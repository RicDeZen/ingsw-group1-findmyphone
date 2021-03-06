package ingsw.group1.findmyphone.cryptography;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import ingsw.group1.findmyphone.R;

/**
 * Class used to store a password.
 *
 * @author Pardeep Kumar
 */
public class PasswordManager {
    private static final String DEFAULT_PASSWORD = null;
    private SharedPreferences sharedPreferences;
    private String passwordKey;

    /**
     * Constructor of the passwordManager object, it requires a context to create a
     * sharedPreference object.
     *
     * @param context The context used to create the sharedPreference.
     */
    public PasswordManager(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        passwordKey = context.getResources().getString(R.string.pref_password_key);
    }

    /**
     * Changes the saved password in the sharedPreferences with a newPassword.
     *
     * @param newPassword The new password.
     */
    public void storePassword(String newPassword) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(passwordKey, newPassword);
        editor.apply();
    }

    /**
     * Returns a string containing the password saved in the sharedPreferences.
     *
     * @return The string containing the password.
     */
    @Nullable
    public String retrievePassword() {
        return sharedPreferences.getString(passwordKey, DEFAULT_PASSWORD);
    }

}
