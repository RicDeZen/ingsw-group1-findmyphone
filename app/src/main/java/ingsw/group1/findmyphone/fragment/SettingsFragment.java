package ingsw.group1.findmyphone.fragment;

import android.os.Bundle;

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
    }

}
