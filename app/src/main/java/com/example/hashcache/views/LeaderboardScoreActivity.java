package com.example.hashcache.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.PopupMenu;

import com.example.hashcache.R;
import com.example.hashcache.appContext.AppContext;
import com.example.hashcache.models.PlayerWallet;
import com.example.hashcache.models.database.Database;
import com.example.hashcache.models.database.DatabasePort;
import com.example.hashcache.models.database.values.FieldNames;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**

 The LeaderboardScoreActivity class is an activity that displays the leaderboard of scores.

 It has a menu button that displays a popup menu of different activities to navigate to.

 The leaderboard is divided into three categories - region, number of QR codes scanned, and top QR codes scanned.

 The user can navigate to each leaderboard by clicking on the respective buttons.
 */
public class LeaderboardScoreActivity extends AppCompatActivity {
    /**

     This method sets up the activity, inflating the layout and adding functionality to the menu button,

     region button, number of QR codes scanned button, and top QR codes scanned button.

     @param savedInstanceState Bundle containing the data it most recently supplied in onSaveInstanceState(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_score);

        // add functionality to menu button
        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This method is called when the menu button is clicked.
             * It displays a popup menu of different activities to navigate to.
             *
             * @param v The view that was clicked
             */
            @Override
            public void onClick(View v) {

                // create menu
                PopupMenu menu = new PopupMenu(LeaderboardScoreActivity.this, menuButton);
                menu.getMenuInflater()
                        .inflate(R.menu.fragment_popup_menu, menu.getMenu());

                // navigate to different activities based on menu item selected
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    /**
                     * This method is called when a menu item is clicked.
                     *
                     * @param item The menu item that was clicked
                     * @return true if the event was handled, false otherwise.
                     */
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();

                        if (id == R.id.menu_home) {                 // go to AppHome page
                            startActivity(new Intent(LeaderboardScoreActivity.this, AppHome.class));
                            return true;

                        } else if (id == R.id.menu_stats) {         // go to QRStats page
                            startActivity(new Intent(LeaderboardScoreActivity.this, QRStats.class));
                            return true;

                        } else if (id == R.id.menu_profile) {       // go to MyProfile
                            startActivity(new Intent(LeaderboardScoreActivity.this, MyProfile.class));
                            return true;

                        } else if (id == R.id.menu_community) {     // go to Community
                            startActivity(new Intent(LeaderboardScoreActivity.this, Community.class));
                            return true;
                        }
                        return LeaderboardScoreActivity.super.onOptionsItemSelected(item);
                    }
                });
                menu.show();
            }
        });

        // add functionality to region button
        AppCompatButton regionButton = findViewById(R.id.region_tab_button);
        regionButton.setOnClickListener(new View.OnClickListener() {
            /**

             This method is called when the region button is clicked.

             It starts the LeaderboardRegionActivity, displaying the leaderboard of scores based on regions.

             @param v The view that was clicked
             */
            @Override
            public void onClick(View v) {
                // go to region leaderboard page
                startActivity(new Intent(LeaderboardScoreActivity.this, LeaderboardRegionActivity.class));
            }
        });
        // add functionality to numQR button
        AppCompatButton numQRButton = findViewById(R.id.numQR_tab_button);
        numQRButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This method is called when the number of QR codes scanned button is clicked.
             * It starts the LeaderboardNumQRActivity, displaying the leaderboard of scores based on the number of QR codes scanned.
             *
             * @param v The view that was clicked
             */
            @Override
            public void onClick(View v) {
                // go to numQR leaderboard page
                startActivity(new Intent(LeaderboardScoreActivity.this, LeaderboardNumQRActivity.class));
            }
        });
        // add functionality to topQR button
        AppCompatButton topButton = findViewById(R.id.topQR_tab_button);
        topButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This method is called when the top QR codes scanned button is clicked.
             * It starts the LeaderboardTopQRActivity, displaying the leaderboard of scores based on the top QR codes scanned.
             *
             * @param v The view that was clicked
             */
            @Override
            public void onClick(View v) {
                // go to topQR leaderboard page
                startActivity(new Intent(LeaderboardScoreActivity.this, LeaderboardTopQRActivity.class));
            }
        });
        setLeaderboard();
    }

    /**
     * Sets the leaderboard scores
     */
    private void setLeaderboard() {
        // Update the my QR code scores
        AppContext appContext = AppContext.get();
        PlayerWallet playerWallet = appContext.getCurrentPlayer().getPlayerWallet();

        TextView playersNumQrCodes = findViewById(R.id.score_value_textview);
        playersNumQrCodes.setText(String.valueOf(playerWallet.getTotalScore()));

        // Get access to the database
        DatabasePort databaseAdapter = Database.getInstance();


        // Get the text views needed to set the leaderboard
        ArrayList<TextView> userNames = new ArrayList<>();
        userNames.add(findViewById(R.id.user_one));
        userNames.add(findViewById(R.id.user_two));
        userNames.add(findViewById(R.id.user_three));

        ArrayList<TextView> qrCounts = new ArrayList<>();
        qrCounts.add(findViewById(R.id.score_one));
        qrCounts.add(findViewById(R.id.score_two));
        qrCounts.add(findViewById(R.id.score_three));

        // Fetch the values from the database needed for the leaderboards
        databaseAdapter.getTopUsers(FieldNames.TOTAL_SCORE.fieldName).thenAccept(score -> {
            if (score.size() != 0) {
                int count = 0;
                for (TextView view : userNames) {
                    if (count < score.size()) {
                        view.setText(score.get(count++).getFirst());
                    } else {
                        view.setText("NA");
                    }
                }
                count = 0;
                for (TextView view : qrCounts) {
                    if(count < score.size()) {
                        view.setText(String.valueOf(score.get(count++).getSecond()));
                    } else {
                        view.setText("0");
                    }
                }
                // Find the player rating
                String userName = appContext.getCurrentPlayer().getUsername();

                // Update the player rating
                for(int i = 0; i < score.size(); i++) {
                    if(Objects.equals(score.get(i).getFirst(), userName)) {
                        TextView rankingView = findViewById(R.id.region_value_textview);
                        rankingView.setText(String.valueOf(i + 1));

                        // No need to keep looking
                        break;
                    }
                }

            } else {
                Log.e("DATABASE", "Error in getting the top k users");
            }
        });
    }
}

