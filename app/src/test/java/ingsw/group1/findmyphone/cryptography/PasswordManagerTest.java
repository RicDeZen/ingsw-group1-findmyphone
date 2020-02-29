package ingsw.group1.findmyphone.cryptography;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@Config(sdk = 28)
@RunWith(RobolectricTestRunner.class)

/**
 * Tests the store and retrieve password methods of PasswordManager class.
 *
 * @author Pardeep Kumar
 */
public class PasswordManagerTest {

    private static final String EXPECTED_PASSWORD = "hardToGuessPassword";
    private static final String DIFFERENCE_BETWEEN_PASSWORD = "12345";
    private static final String WRONG_PASSWORD = EXPECTED_PASSWORD + DIFFERENCE_BETWEEN_PASSWORD;
    private Context context = ApplicationProvider.getApplicationContext();
    private PasswordManager passwordManager = new PasswordManager(context);


    /**
     * Tests the method storePassword, retrievePassword and assert that given a newPassword the
     * retrievePassword returns the newPassword.
     */
    @Test
    public void testStorePassword() {
        passwordManager.storePassword(EXPECTED_PASSWORD);
        assertEquals(EXPECTED_PASSWORD, passwordManager.retrievePassword());
    }


    /**
     * Tests the method storePassword, retrievePassword and assert that the retrieved password is
     * not equal to a wrong password .
     */
    @Test
    public void testStorePasswordNotEquals() {
        passwordManager.storePassword(EXPECTED_PASSWORD);
        assertNotEquals(WRONG_PASSWORD, passwordManager.retrievePassword());
    }

}
