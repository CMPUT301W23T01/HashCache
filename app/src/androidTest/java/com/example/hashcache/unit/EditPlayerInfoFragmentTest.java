package com.example.hashcache.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.hashcache.views.MainActivity;
import com.example.hashcache.R;
import com.example.hashcache.views.SettingsActivity;
import com.robotium.solo.Solo;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import java.util.Random;


/**
 * Test class for MainActivity. All the UI tests are written here. Robotium test framework is
 used
 */
public class EditPlayerInfoFragmentTest {

    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
        final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm";
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(15);
        for (int i = 0; i < 15; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));

        solo.enterText((EditText) solo.getView(R.id.username_edittext), sb.toString());
        solo.clickOnButton("START CACHING");
        solo.clickOnView(solo.getView(R.id.logo_button));
        solo.clickOnView(solo.getView(R.id.logo_button));
        solo.clickOnView(solo.getView(R.id.edit_info_image));
    }


    @Test
    public void checkEmptyInput(){
        // Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", SettingsActivity.class);
        solo.waitForView(R.id.email_edit_text);
        solo.clickOnView(solo.getView(R.id.confirm_player_info_button));
        solo.sleep(100);
        solo.assertCurrentActivity("Wrong Activity", SettingsActivity.class);
        solo.clickOnView(solo.getView(R.id.logout_button));
    }


    @Test
    public void checkWrongFormat(){
        solo.enterText((EditText) solo.getView(R.id.email_edit_text), "Wrong Format 12345");
        solo.enterText((EditText) solo.getView(R.id.edit_phone_number), "This is not a number");
        solo.clickOnView(solo.getView(R.id.confirm_player_info_button));
        solo.sleep(100);
        solo.waitForView(solo.getView(R.id.invalid_input_text));
        solo.clickOnView(solo.getView(R.id.x_button));
        solo.assertCurrentActivity("Wrong Activity", SettingsActivity.class);
        solo.clickOnView(solo.getView(R.id.logout_button));
    }


    @Test
    public void checkCorrectFormat(){
        String fakeEmail = "email@notadomain.com";
        String fakePhoneNumber = "123-123-1234";

        solo.enterText((EditText) solo.getView(R.id.email_edit_text), fakeEmail);
        solo.enterText((EditText) solo.getView(R.id.edit_phone_number), fakePhoneNumber);
        solo.clickOnView(solo.getView(R.id.confirm_player_info_button));
        solo.sleep(100);
        solo.assertCurrentActivity("Wrong Activity", SettingsActivity.class);
        solo.waitForText(fakeEmail);
        solo.waitForText(fakePhoneNumber);
        solo.clickOnView(solo.getView(R.id.logout_button));

    }
}































