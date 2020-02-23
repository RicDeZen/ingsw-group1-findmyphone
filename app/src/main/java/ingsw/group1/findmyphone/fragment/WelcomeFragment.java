package ingsw.group1.findmyphone.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ingsw.group1.findmyphone.R;

/**
 * Fragment displayed on app startup.
 *
 * @author Riccardo De Zen.
 */
public class WelcomeFragment extends Fragment {

    /**
     * Method to inflate the view used by the Fragment.
     *
     * @param inflater           The inflater to use.
     * @param container          The container view.
     * @param savedInstanceState The saved instance state for this fragment.
     * @return The inflated View.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.welcome_fragment, container, false);
    }
}
