package com.example.hashcache.unit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.SearchView;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.hashcache.R;
import com.example.hashcache.views.AppHome;
import com.example.hashcache.views.Community;
import com.example.hashcache.views.MainActivity;
import com.example.hashcache.views.MyProfile;
import com.example.hashcache.views.QRScanActivity;
import com.google.android.gms.maps.SupportMapFragment;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Random;


/**
 * Test class for AppHome. All the UI tests are written here. Robotium test framework is
 used
 */
public class AppHomeTest {

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
        final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(15);
        for(int i=0;i<15;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));

        solo.enterText((EditText) solo.getView(R.id.username_edittext), sb.toString());
        solo.clickOnButton("START CACHING");
    }

    private void logout(){
        solo.clickOnView(solo.getView(R.id.menu_button));
        solo.clickOnView(solo.getView(R.id.my_codes_button));
        solo.clickOnView(solo.getView(R.id.logo_button));
        solo.sleep(2000);
        solo.clickOnView(solo.getView(R.id.logout_button));
        solo.sleep(1000);
    }

    @Test
    public void checkScanQRButton(){
        // Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity"
        solo.assertCurrentActivity("Wrong Activity", AppHome.class);
        solo.clickOnButton("SCAN QR");
        solo.sleep(100);
        solo.assertCurrentActivity("Wrong Activity", QRScanActivity.class);
        solo.goBack();
        solo.goBack();
        logout();
    }

    @Test
    public void checkLogoButton(){
        // Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity"
        solo.assertCurrentActivity("Wrong Activity", AppHome.class);
        solo.clickOnView(solo.getView(R.id.logo_button));
        solo.sleep(100);
        solo.assertCurrentActivity("Wrong Activity", MyProfile.class);
        logout();
    }


    @Test
    public void checkMapButton(){
        // Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity"
        solo.assertCurrentActivity("Wrong Activity", AppHome.class);
        solo.sleep(1000);
        solo.clickOnView(solo.getView(R.id.idSearchView));
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", AppHome.class);
        solo.goBack();
        logout();
    }

    @Test
    public void testEyecon(){
        solo.assertCurrentActivity("Wrong Activity", AppHome.class);
        solo.clickOnView(solo.getView(R.id.eye_button));
        solo.assertCurrentActivity("Wrong Activity", AppHome.class);
        logout();
    }

    @Test
    public void testSearch(){
        solo.assertCurrentActivity("Wrong Activity", AppHome.class);
        solo.typeText(1, "Beijing");
        solo.sendKey(Solo.ENTER);
        solo.sleep(3000);
        assertTrue(solo.searchText("Beijing", 1));
        solo.assertCurrentActivity("Wrong Activity", AppHome.class);
        logout();
    }

    @Test
    public void testSearchNotFound(){
        solo.assertCurrentActivity("Wrong Activity", AppHome.class);
        solo.typeText(1, "abababaababababaab");
        solo.sendKey(Solo.ENTER);
        assertFalse(solo.searchText("Beijing", 1));
        solo.assertCurrentActivity("Wrong Activity", AppHome.class);
        logout();
    }

    @Test
    public void testCurrentLocationButton(){
        solo.assertCurrentActivity("Wrong Activity", AppHome.class);
        solo.sleep(3000);
        //View mapView = solo.getCurrentActivity().getFragmentManager().findFragmentById(R.id.map).getView();
        //View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        //solo.typeText(1, "Beijing");
        //solo.sendKey(Solo.ENTER);
        //solo.sleep(3000);
        //assertTrue(solo.searchText("Beijing", 1));
        //solo.clickOnView(locationButton);
        solo.assertCurrentActivity("Wrong Activity", AppHome.class);
        logout();
    }

    @Test
    public void checkMenuButton1(){
        // Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity"
        solo.assertCurrentActivity("Wrong Activity", AppHome.class);
        solo.clickOnView(solo.getView(R.id.menu_button));
        solo.clickOnView(solo.getView(R.id.map_button));
        solo.sleep(100);
        solo.assertCurrentActivity("Wrong Activity", AppHome.class);
        solo.clickOnView(solo.getView(R.id.logo_button));
        solo.clickOnView(solo.getView(R.id.logo_button));
        solo.clickOnView(solo.getView(R.id.logout_button));
    }

    @Test
    public void checkMenuButton2(){
        // Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity"
        solo.assertCurrentActivity("Wrong Activity", AppHome.class);
        solo.clickOnView(solo.getView(R.id.menu_button));
        solo.clickOnView(solo.getView(R.id.my_codes_button));
        solo.sleep(100);
        solo.assertCurrentActivity("Wrong Activity", MyProfile.class);
        solo.clickOnView(solo.getView(R.id.logo_button));
        solo.clickOnView(solo.getView(R.id.logout_button));
    }

    @Test
    public void checkMenuButton3(){
        // Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity"
        solo.assertCurrentActivity("Wrong Activity", AppHome.class);
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