package ingsw.group1.findmyphone.cryptography;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Class used to store a password.
 *
 * @author Pardeep Kumar
 */
public class PasswordManager {
    private static final String PASSWORD_FILE_NAME = "ingsw.group1.findmyphone.password";
    private static final String PASSWORD = "password";
    private static final String DEFAULT_PASSWORD = "ASkj26%&/chfd";
    private SharedPreferences sharedPreferences;

    /**
     * Constructor of the passwordManager object, it requires a context to create a sharedPreference object.
     *
     * @param context The context used to create the sharedPreference.
     */
    public PasswordManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PASSWORD_FILE_NAME, MODE_PRIVATE);
    }

    /**
     * Changes the saved password in the sharedPreferences with a newPassword.
     *
     * @param newPassword The new password.
     */
    public void storePassword(String newPassword) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASSWORD, newPassword);
        editor.apply();
    }

    /**
     * Returns a string containing the password saved in the sharedPreferences.
     *
     * @return The string containing the password.
     */
    public String retrievePassword() {
        return sharedPreferences.getString(PASSWORD, DEFAULT_PASSWORD);
    }

}
