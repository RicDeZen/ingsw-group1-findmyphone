package ingsw.group1.findmyphone;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

/**
 * Class describing a dialog that shows information related to the permissions required by
 * the app.
 *
 * @author Riccardo De Zen.
 */
public class PermissionInfoDialog extends DialogFragment {

    public static final int LOCATION = 0;
    public static final int MESSAGES = 1;

    private static final String DEFAULT_INFO = "";
    private static final int DEFAULT_ID = 0;
    private static final int LOCATION_INFO_STRING_ID = R.string.location_permissions_info;
    private static final int MESSAGES_INFO_STRING_ID = R.string.message_permissions_info;
    private static final int POSITIVE_BUTTON_ID = R.string.permissions_positve_button;
    private static final int NEGATIVE_BUTTON_ID = R.string.permissions_negative_button;
    private static final int TITLE_ID = R.string.permissions_info_title;

    private PermissionsDialogListener listener;
    private int infoStringId;

    /**
     * Default constructor. Takes the type of Dialog. Only accepted values are
     * {@link PermissionInfoDialog#LOCATION} and {@link PermissionInfoDialog#MESSAGES}. Other
     * values
     * will result in incorrect or empty dialogs.
     *
     * @param type The type of dialog. See public constants of this class.
     */
    public PermissionInfoDialog(int type) {
        super();
        switch (type) {
            case LOCATION:
                infoStringId = LOCATION_INFO_STRING_ID;
                break;
            case MESSAGES:
                infoStringId = MESSAGES_INFO_STRING_ID;
                break;
            default:
                infoStringId = DEFAULT_ID;
        }
    }

    /**
     * Method called when the DialogFragment is being attached to an Activity.
     * A Context cannot instantiate this dialog class if it does not implement
     * {@link PermissionsDialogListener}.
     *
     * @param context The Context for this DialogFragment, must implement
     *                {@link PermissionsDialogListener}.
     * @throws ClassCastException If {@code context} does not implement
     *                            {@link PermissionsDialogListener}.
     */
    @Override
    public void onAttach(@NonNull Context context) throws ClassCastException {
        super.onAttach(context);
        try {
            listener = (PermissionsDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.getClass().getName() + " must implement " +
                            PermissionsDialogListener.class.getSimpleName()
            );
        }
    }

    /**
     * This is the main method to instantiate a fragment. The returned fragment uses the default
     * system style and displays information about why the permissions are required.
     *
     * @param savedInstanceState The saved state of the Dialog, if any.
     * @return A Dialog built with the proper message and buttons.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if (infoStringId == DEFAULT_ID)
            builder.setMessage(DEFAULT_INFO);
        else builder.setMessage(infoStringId);

        builder.setTitle(TITLE_ID);

        builder.setPositiveButton(POSITIVE_BUTTON_ID, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (listener != null) listener.onDialogPositiveClick(PermissionInfoDialog.this);
            }
        });

        builder.setNegativeButton(NEGATIVE_BUTTON_ID, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (listener != null) listener.onDialogNegativeClick(PermissionInfoDialog.this);
            }
        });
        return builder.create();
    }

    /**
     * The activity that creates an instance of this dialog fragment must implement this
     * interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it.
     */
    public interface PermissionsDialogListener {
        /**
         * @param dialog The dialog on which the positive button was clicked.
         */
        void onDialogPositiveClick(DialogFragment dialog);

        /**
         * @param dialog The dialog on which the negative button was clicked.
         */
        void onDialogNegativeClick(DialogFragment dialog);
    }


}
