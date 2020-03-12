package ingsw.group1.findmyphone.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.cryptography.PasswordManager;

/**
 * DialogFragment class displaying a Dialog used when setting the password.
 *
 * @author Riccardo De Zen.
 */
public class PasswordDialog extends DialogFragment {

    private PasswordManager passwordManager;
    private CharSequence previousPassword;
    private AlertDialog.Builder builder;
    private EditText passwordInput;
    private Button dialogButton;

    /**
     * Method called when the DialogFragment is being attached to an Activity.
     * Caches a {@link PasswordManager} to set the password and an {@link AlertDialog.Builder} to
     * later build the Dialog.
     *
     * @param context The Context for this DialogFragment.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.passwordManager = new PasswordManager(context);
        this.builder = new AlertDialog.Builder(context);
        this.previousPassword = passwordManager.retrievePassword();
    }

    /**
     * This is the main method to instantiate a fragment. The returned fragment uses the default
     * system style and displays information about why the permissions are required.
     * If the current context is not an Activity then an Exception will be thrown by
     * {@link DialogFragment#requireActivity()}.
     *
     * @param savedInstanceState The saved state of the Dialog, if any.
     * @return A Dialog built with the proper message and buttons.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Inflating and setting the View.
        View dialogRoot = requireActivity()
                .getLayoutInflater()
                .inflate(R.layout.password_dialog, null);
        builder.setView(dialogRoot);

        // Get a reference to the EditText field.
        passwordInput = dialogRoot.findViewById(R.id.password_dialog_input);
        passwordInput.setText((previousPassword == null) ? "" : previousPassword);
        passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No action
            }

            /**
             * Whenever the text in the input field is changed, we check to see whether it is an
             * empty text or not, because we don't want to have an empty password.
             */
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty())
                    dialogButton.setEnabled(false);
                else
                    dialogButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No action
            }
        });

        // When the button is pressed the new password is stored.
        dialogButton = dialogRoot.findViewById(R.id.password_dialog_button);
        dialogButton.setOnClickListener((view) -> {
            String newPassword = passwordInput.getText().toString();
            if (!newPassword.isEmpty()) {
                passwordManager.storePassword(newPassword);
                if (getDialog() != null) dismiss();
            } else
                Toast.makeText(getContext(), R.string.empty_password_msg, Toast.LENGTH_SHORT).show();
        });

        return builder.create();
    }

}
