package ingsw.group1.findmyphone.fragment;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.dialog.PasswordDialog;

/**
 * Fragment dedicated to showing the Preferences for the app.
 *
 * @author Riccardo De Zen
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    private static final String PASS_DIALOG_TAG = "password-input";

    /**
     * Method called to inflate the Preferences hierarchy.
     *
     * @param savedInstanceState The saved instance state.
     * @param rootKey            The root key, for the hierarchy, it is provided automatically.
     */
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.app_preferences, rootKey);
        Preference passwordPreference =
                findPreference(getString(R.string.pref_password_key));
        if (passwordPreference != null) {
            passwordPreference.setOnPreferenceClickListener(preference -> {
                showPasswordDialog();
                return true;
            });
        }
    }

    /**
     * Method showing a Dialog to set the user's password.
     */
    private void showPasswordDialog() {
        PasswordDialog passwordDialog = new PasswordDialog();
        passwordDialog.setCancelable(false);
        passwordDialog.show(getParentFragmentManager(), PASS_DIALOG_TAG);
    }

}
