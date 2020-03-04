package ingsw.group1.findmyphone.fragment;

import android.os.Bundle;
import android.text.InputType;

import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;

import ingsw.group1.findmyphone.R;

/**
 * Fragment dedicated to showing the Preferences for the app.
 *
 * @author Riccardo De Zen
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    /**
     * Method called to inflate the Preferences hierarchy.
     *
     * @param savedInstanceState The saved instance state.
     * @param rootKey            The root key, for the hierarchy, it is provided automatically.
     */
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.app_preferences, rootKey);
        EditTextPreference passwordPreference =
                findPreference(getString(R.string.pref_password_key));
        if (passwordPreference != null) {
            passwordPreference.setOnBindEditTextListener(editText ->
                    editText.setInputType(
                            InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    )
            );
        }
    }

}
