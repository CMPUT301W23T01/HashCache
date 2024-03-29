/*
 * MyProfile
 *
 * User's profile page.
 * Displays scrollable list of QR monsters scanned by the user.
 * Top logo button allows navigation to user settings page.
 * Selecting a monster navigates to that monster's info page.
 * Additional buttons permit navigation to other pages.
 */

package com.example.hashcache.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.PopupMenu;

import com.example.hashcache.R;
import com.example.hashcache.appContext.AppContext;
import com.example.hashcache.models.Player;
import com.example.hashcache.models.ScannableCode;
import com.example.hashcache.models.database.Database;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * MyProfile
 *
 * The MyProfile class is an activity that displays the user's profile page.
 *
 * It displays a scrollable list of QR monsters scanned by the user, along with
 * their username and score. The top logo
 * button allows navigation to the user settings page. Selecting a monster
 * navigates to that monster's info page. Additional
 * buttons permit navigation to other pages.
 *
 * @see AppCompatActivity
 * @see AppCompatButton
 * @see ImageButton
 * @see ListView
 * @see TextView
 * @see PopupMenu
 * @see MenuItem
 * @see Player
 * @see AppContext
 * @see SettingsActivity
 * @see QRStats
 * @see Community
 */
public class MyProfile extends AppCompatActivity implements Observer {
    private View mPurpleRect;
    private ImageButton mLogoButton;
    private ImageButton mMenuButton;
    private ListView mScannableCodesList;
    private AppCompatButton mQRStatsButton;
    private ScannableCodesArrayAdapter scannableCodesArrayAdapter;
    private ArrayList<ScannableCode> scannableCodes;

    @Override
    /**
     * Called when the activity is created.
     *
     * It sets up the functionality for the logo button, the QR STATS button, and
     * the menu button. It also retrieves the
     * current user's information and sets the username and score on the profile
     * page.
     *
     * @param savedInstanceState the saved state of the activity, if it was
     *                           previously closed
     * @see AppContext
     * @see Player
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
        initView();
        // add functionality to logo button
        ImageButton logoButton = findViewById(R.id.logo_button);

        logoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // go to user settings page
                startActivity(new Intent(MyProfile.this, SettingsActivity.class));
            }
        });

        // add functionality to QR STATS button
        AppCompatButton statsButton = findViewById(R.id.qr_stats_button);

        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // go to page displaying QR Stats
                startActivity(new Intent(MyProfile.this, QRStats.class));
            }
        });

        // add functionality to menu button
        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomMenuFragment bottomMenu = new BottomMenuFragment();
                bottomMenu.show(getSupportFragmentManager(), bottomMenu.getTag());
            }

        });

        mScannableCodesList = findViewById(R.id.scannable_codes_list);

        mScannableCodesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onScannableCodeItemClicked(i);
            }
        });

        AppContext.get().addObserver(this);
        setUIParams();
    }

    private void onScannableCodeItemClicked(int i) {
        ScannableCode clickedScannableCode = scannableCodes.get(i);

        Intent intent = new Intent(getApplicationContext(), DisplayMonsterActivity.class);
        intent.putExtra("belongsToCurrentUser", true);

        AppContext.get().setCurrentScannableCode(clickedScannableCode);
        startActivity(intent);
    }

    /**
     * Initializes the view.
     */
    private void initView() {
        mPurpleRect = findViewById(R.id.purple_rect);
        mLogoButton = findViewById(R.id.logo_button);
        mMenuButton = findViewById(R.id.menu_button);
        mQRStatsButton = findViewById(R.id.qr_stats_button);
    }

    /**
     * Sets the username displayed on the profile page.
     *
     * @param username the username to be set
     * @see TextView
     */
    public void setUsername(String username) {
        TextView tv = findViewById(R.id.username_textview);
        tv.setText(username);
    }

    /**
     * Sets the score displayed on the profile page.
     *
     * @param score the score to be set
     * @see TextView
     */
    public void setScore(long score) {
        TextView scoreTv = findViewById(R.id.score_textview);
        scoreTv.setText("Score: " + score);
    }

    /**
     * Called when the observable for this observer updates
     * @param observable     the observable object.
     * @param o   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable observable, Object o) {
        setUIParams();
    }

    private void setUIParams() {
        Player currentPlayer = AppContext.get().getCurrentPlayer();
        setUsername(currentPlayer.getUsername());
        setScore(currentPlayer.getPlayerWallet().getTotalScore());
        Database.getInstance()
                .getScannableCodesByIdInList(currentPlayer.getPlayerWallet().getScannedCodeIds())
                .thenAccept(scannableCodes -> {
                    this.scannableCodes = scannableCodes;
                    scannableCodesArrayAdapter = new ScannableCodesArrayAdapter(this,
                            scannableCodes);
                    mScannableCodesList.setAdapter(scannableCodesArrayAdapter);
                });

    }

}
