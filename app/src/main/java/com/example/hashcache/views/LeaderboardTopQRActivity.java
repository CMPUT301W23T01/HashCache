package com.example.hashcache.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.PopupMenu;

import com.example.hashcache.R;
import com.example.hashcache.models.Player;
import com.example.hashcache.models.ScannableCode;
import com.example.hashcache.models.database.Database;
import com.example.hashcache.store.AppStore;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**

 The LeaderboardTopQRActivity class is an activity that displays the leaderboard of scores based on the top QR codes scanned.

 It has a menu button that displays a popup menu of different activities to navigate to.

 The user can navigate to different leaderboards - region, number of QR codes scanned, and scores - by clicking on the respective buttons.
 */
public class LeaderboardTopQRActivity extends AppCompatActivity {
    /**

     This method sets up the activity, inflating the layout and adding functionality to the menu button,

     region button, number of QR codes scanned button, and score button.

     @param savedInstanceState Bundle containing the data it most recently supplied in onSaveInstanceState(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // setup the activity and inflate the layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_topqr);


        // Sets the players numb qr codes
        TextView playersTopQrCode = findViewById(R.id.score_value_textview);
        AtomicLong playerTopQrScore = new AtomicLong();
        Database.getInstance()
                .getTotalScore(AppStore.get().getCurrentPlayer().getUserId())
                .thenAccept( score -> {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            playerTopQrScore.set(score);
                        }
                    });
                });


        long valueOfUser= AppStore.get().getHighestScannableCode().getHashInfo().getGeneratedScore();
        playersTopQrCode.setText(String.valueOf(valueOfUser));
        //playersTopQrCode.setText(String.valueOf(playerTopQrScore));

        // Gets the text view for the user names
        ArrayList<TextView> userNames = new ArrayList<>();
        userNames.add(findViewById(R.id.user_one));
        userNames.add(findViewById(R.id.user_two));
        userNames.add(findViewById(R.id.user_three));

        for(TextView name: userNames) {
            name.setText("Ryan");
        }

        // Gets the text view for the total scores
        ArrayList<TextView> totalScore = new ArrayList<>();
        totalScore.add(findViewById(R.id.score_one));
        totalScore.add(findViewById(R.id.score_two));
        totalScore.add(findViewById(R.id.score_three));

        for(TextView name: totalScore) {
            name.setText("5");
        }

        // Gets the textview for the monster names
        ArrayList<TextView> monsterNames = new ArrayList<>();
        monsterNames.add(findViewById(R.id.monster_name_one));
        monsterNames.add(findViewById(R.id.monster_name_two));
        monsterNames.add(findViewById(R.id.monster_name_three));

        for(TextView name: monsterNames) {
            name.setText("Zorg");
        }

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
                PopupMenu menu = new PopupMenu(LeaderboardTopQRActivity.this, menuButton);
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
                            startActivity(new Intent(LeaderboardTopQRActivity.this, AppHome.class));
                            return true;

                        } else if (id == R.id.menu_stats) {         // go to QRStats page
                            startActivity(new Intent(LeaderboardTopQRActivity.this, QRStats.class));
                            return true;

                        } else if (id == R.id.menu_profile) {       // go to MyProfile
                            startActivity(new Intent(LeaderboardTopQRActivity.this, MyProfile.class));
                            return true;

                        } else if (id == R.id.menu_community) {     // go to Community
                            startActivity(new Intent(LeaderboardTopQRActivity.this, Community.class));
                            return true;
                        }
                        return LeaderboardTopQRActivity.super.onOptionsItemSelected(item);
                    }
                });
                menu.show();
            }
        });

        // add functionality to region button
        AppCompatButton regionButton = findViewById(R.id.region_tab_button);
        regionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to numQR leaderboard page
                startActivity(new Intent(LeaderboardTopQRActivity.this, LeaderboardRegionActivity.class));
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
                startActivity(new Intent(LeaderboardTopQRActivity.this, LeaderboardNumQRActivity.class));
            }
        });
        // add functionality to score button
        AppCompatButton topButton = findViewById(R.id.score_tab_button);
        topButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This method is called when the score button is clicked.
             * It starts the LeaderboardScoreActivity, displaying the leaderboard of scores.
             *
             * @param v The view that was clicked
             */
            @Override
            public void onClick(View v) {
                // go to score leaderboard page
                startActivity(new Intent(LeaderboardTopQRActivity.this, LeaderboardScoreActivity.class));
            }
        });
    }
}

