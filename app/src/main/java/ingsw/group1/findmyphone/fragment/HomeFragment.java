package ingsw.group1.findmyphone.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import com.eis.smslibrary.SMSPeer;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.RequestManager;
import ingsw.group1.findmyphone.activity.NavHolderActivity;
import ingsw.group1.findmyphone.location.GeoPosition;
import ingsw.group1.findmyphone.location.LocationManager;
import ingsw.group1.findmyphone.log.holders.LogViewHolderBuilder;
import ingsw.group1.findmyphone.log.items.LogItem;

/**
 * @author Turcato, Kumar, De Zen
 */
public class HomeFragment extends Fragment implements Observer<LogItem> {

    private static final String INVALID_ADDRESS_MESSAGE = "The provided phone address is invalid";
    private static final String INVALID_PASSWORD_MESSAGE = "The password cannot be empty";

    private RequestManager manager = RequestManager.getInstance();

    private EditText addressInput;
    private EditText passwordInput;
    private SMSPeer smsPeer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * Method called when creating the view for the first time.
     *
     * @param inflater           The passed inflater. Needed to create the view.
     * @param container          The container asking for creation of the fragment.
     * @param savedInstanceState The saved state.
     * @return The root view for this fragment, in this case just an inflation of the layout.
     */
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_fragment, container, false);

        ImageButton viewContacts;

        addressInput = root.findViewById(R.id.home_address_input);
        passwordInput = root.findViewById(R.id.home_password_input);
        ImageButton sendAlarmRequestButton = root.findViewById(R.id.home_alarm_button);
        ImageButton sendLocationRequestButton = root.findViewById(R.id.home_location_button);
        viewContacts = root.findViewById(R.id.home_contacts_button);

        if (getActivity() == null) return root;

        sendLocationRequestButton.setOnClickListener(v -> {
            smsPeer = new SMSPeer(addressInput.getText().toString());
            String password = passwordInput.getText().toString();
            if (password.isEmpty()) {
                showToastIfPossible(INVALID_PASSWORD_MESSAGE);
                return;
            }
            if (smsPeer.getInvalidityReason() == null)
                manager.sendLocationRequest(smsPeer, password);
            else showToastIfPossible(INVALID_ADDRESS_MESSAGE);
        });

        sendAlarmRequestButton.setOnClickListener(v -> {
            smsPeer = new SMSPeer(addressInput.getText().toString());
            String password = passwordInput.getText().toString();
            if (password.isEmpty()) {
                showToastIfPossible(INVALID_PASSWORD_MESSAGE);
                return;
            }
            if (smsPeer.getInvalidityReason() == null)
                manager.sendAlarmRequest(smsPeer, password);
            else showToastIfPossible(INVALID_ADDRESS_MESSAGE);
        });

        viewContacts.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.navigation_home_to_contacts));

        return root;
    }

    /**
     * When resuming the value of the picked address is read.
     */
    @Override
    public void onResume() {
        super.onResume();
        addressInput.setText(NavHolderActivity.sharedData.getHomeAddress().getValue());
        if (getActivity() != null)
            NavHolderActivity.sharedData.getLastEvent().observe(getActivity(), this);
        populateLastItem();
    }

    /**
     * Method to safely show a Toast, fails silently if the Context is not available.
     *
     * @param message The message the Toast should contain.
     */
    private void showToastIfPossible(String message) {
        if (getContext() == null) return;
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * Called when the data is changed.
     *
     * @param item The new data
     */
    @Override
    public void onChanged(LogItem item) {
        if (getActivity() != null)
            getActivity().runOnUiThread(this::populateLastItem);
    }

    /**
     * Populates the last item container with the content of the shared data.
     */
    private void populateLastItem() {
        // Should never happen.
        if (getView() == null) return;
        LinearLayout container = getView().findViewById(R.id.last_event_container);
        container.removeAllViews();

        LogItem lastItem = NavHolderActivity.sharedData.getLastEvent().getValue();
        // Might happen.
        if (lastItem == null) return;
        View itemView = getLayoutInflater().inflate(R.layout.log_item, container, false);

        // We exploit the LogViewHolders to skip some steps in populating the view.
        new LogViewHolderBuilder(getResources()).build(itemView, lastItem.getFlags()).populate(lastItem);

        // Hiding the ImageView, removing onclick listener, showing extra
        itemView.findViewById(R.id.log_imageView_icon).setVisibility(View.GONE);
        itemView.findViewById(R.id.log_extra_layout).setVisibility(View.VISIBLE);
        itemView.findViewById(R.id.log_info_layout).setOnClickListener(null);

        // Maps click listener
        itemView.findViewById(R.id.map_link_Button).setOnClickListener(view -> {
            GeoPosition lastPosition = lastItem.getPosition();
            if (getContext() == null || lastPosition == null) return;
            LocationManager.openMapsUrl(
                    getContext(),
                    lastPosition.getLatitude(),
                    lastPosition.getLongitude()
            );
        });

        container.addView(itemView);
    }
}