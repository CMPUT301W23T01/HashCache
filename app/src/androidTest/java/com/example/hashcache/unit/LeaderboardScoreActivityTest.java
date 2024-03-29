package com.example.hashcache.unit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.hashcache.views.AppHome;
import com.example.hashcache.views.Community;
import com.example.hashcache.views.LeaderboardNumQRActivity;
import com.example.hashcache.views.LeaderboardRegionActivity;
import com.example.hashcache.views.LeaderboardScoreActivity;
import com.example.hashcache.views.LeaderboardTopQRActivity;
import com.example.hashcache.views.MainActivity;
import com.example.hashcache.views.MyProfile;
import com.example.hashcache.views.QRStats;
import com.example.hashcache.R;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Random;

/**
 * Test class for LeaderboardNumQRActivity. All the UI tests are written here. Robotium test framework is
 used
 */
public class LeaderboardScoreActivityTest {

    private Solo solo;
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm";
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(15);
        for (int i = 0; i < 15; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));

        solo.enterText((EditText) solo.getView(R.id.username_edittext), sb.toString());
        solo.clickOnButton("START CACHING");
        solo.clickOnView(solo.getView(R.id.menu_button));
        solo.clickOnView(solo.getView(R.id.leaderboard_button));
        solo.clickOnView(solo.getView(R.id.leaderboard_button));
    }

    void logout(){
        solo.clickOnView(solo.getView(R.id.menu_button));
        solo.clickOnView(solo.getView(R.id.my_codes_button));
        solo.clickOnView(solo.getView(R.id.logo_button));
        solo.clickOnView(solo.getView(R.id.logout_button));
    }

    @Test
    public void checkScoreButton(){
        // Asserts that the current activity is the LeaderboardNumQRActivity. Otherwise, show “Wrong Activity"
        solo.assertCurrentActivity("Wrong Activity", LeaderboardScoreActivity.class);
        logout();
    }

    @Test
    public void checkTopQRButton(){
        // Asserts that the current activity is the LeaderboardScoreActivity. Otherwise, show “Wrong Activity"
        solo.assertCurrentActivity("Wrong Activity", LeaderboardScoreActivity.class);
        solo.clickOnButton("TOP QR");
        solo.sleep(100);
        solo.assertCurrentActivity("Wrong Activity", LeaderboardTopQRActivity.class);
        logout();
    }

    @Test
    public void checkRegionButton(){
        // Asserts that the current activity is the LeaderboardNumQRActivity. Otherwise, show “Wrong Activity"
        solo.assertCurrentActivity("Wrong Activity", LeaderboardScoreActivity.class);
        solo.clickOnButton("REGION");
        solo.sleep(100);
        solo.assertCurrentActivity("Wrong Activity", LeaderboardRegionActivity.class);
        logout();
    }

    @Test
    public void checkMenuButtonMap(){
        // Asserts that the current activity is the LeaderboardRegionActivity. Otherwise, show “Wrong Activity"
        solo.assertCurrentActivity("Wrong Activity", LeaderboardScoreActivity.class);
        solo.clickOnView(solo.getView(R.id.menu_button));
        solo.clickOnView(solo.getView(R.id.map_button));
        solo.sleep(100);
        solo.assertCurrentActivity("Wrong Activity", AppHome.class);
        solo.clickOnView(solo.getView(R.id.logo_button));
        solo.clickOnView(solo.getView(R.id.logo_button));
        solo.clickOnView(solo.getView(R.id.logout_button));
    }

    @Test
    public void checkMenuButtonProfile(){
        // Asserts that the current activity is the LeaderboardRegionActivity. Otherwise, show “Wrong Activity"
        solo.assertCurrentActivity("Wrong Activity", LeaderboardScoreActivity.class);
        solo.clickOnView(solo.getView(R.id.menu_button));
        solo.clickOnView(solo.getView(R.id.my_codes_button));
        solo.sleep(100);
        solo.assertCurrentActivity("Wrong Activity", MyProfile.class);
        solo.clickOnView(solo.getView(R.id.logo_button));
        solo.clickOnView(solo.getView(R.id.logout_button));
    }

    @Test
    public void checkMenuButtonLeaderboard(){
        // Asserts that the current activity is the LeaderboardRegionActivity. Otherwise, show “Wrong Activity"
        solo.assertCurrentActivity("Wrong Activity", LeaderboardScoreActivity.class);
        solo.clickOnView(solo.getView(R.id.menu_button));
        solo.clickOnView(solo.getView(R.id.leaderboard_button));
        solo.sleep(100);
        solo.assertCurrentActivity("Wrong Activity", LeaderboardScoreActivity.class);
        solo.clickOnView(solo.getView(R.id.menu_button));
        solo.clickOnView(solo.getView(R.id.my_codes_button));
        solo.clickOnView(solo.getView(R.id.logo_button));
        solo.clickOnView(solo.getView(R.id.logout_button));
    }

    @Test
    public void checkMenuButtonCommunity(){
        // Asserts that the current activity is the LeaderboardRegionActivity. Otherwise, show “Wrong Activity"
        solo.assertCurrentActivity("Wrong Activity", LeaderboardScoreActivity.class);
        solo.clickOnView(solo.getView(R.id.menu_button));
        solo.clickOnView(solo.getView(R.id.menu_community_button));
        solo.sleep(100);
        solo.assertCurrentActivity("Wrong Activity", Community.class);
        solo.clickOnView(solo.getView(R.id.menu_button));
        solo.clickOnView(solo.getView(R.id.my_codes_button));
        solo.clickOnView(solo.getView(R.id.logo_button));
        solo.clickOnView(solo.getView(R.id.logout_button));
    }

}