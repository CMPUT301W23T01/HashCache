/*
 * QRStats
 *
 * Shows statistics for current user.
 * Additional buttons permit navigation to other pages.
 */

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
import com.example.hashcache.models.PlayerWallet;
import com.example.hashcache.store.AppStore;
/**
 The QRStats activity displays statistics for the current user, including total score, number of codes scanned,
 highest score achieved, and lowest score achieved. It also provides buttons for navigating to other pages.
 */
public class QRStats extends AppCompatActivity {
    private ImageButton menuButton;
    private ImageButton scoreIcon;
    private TextView totalScoreTextView;
    private TextView myCodesTextView;
    private TextView topScoreTextView;
    private TextView lowScoreTextView;
    private TextView totalScoreValueTextView;
    private TextView myCodesValueTextView;
    private TextView topScoreValueTextView;
    private TextView lowScoreValueTextView;
    private AppCompatButton myProfileButton;
    /**
     * Initializes the activity and sets the layout. Also adds functionality to the menu button and my profile button.
     *
     * @param savedInstanceState a Bundle object containing the activity's previously saved state, if any
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_stats);

        // add functionality to menu button
        ImageButton menuButton = findViewById(R.id.menu_button);
        totalScoreValueTextView = findViewById(R.id.total_score_value);
        myCodesValueTextView = findViewById(R.id.my_codes_value);
        topScoreValueTextView = findViewById(R.id.top_score_value);
        lowScoreValueTextView = findViewById(R.id.low_score_value);

        PlayerWallet playerWallet = AppStore.get().getCurrentPlayer().getPlayerWallet();
        setLowScoreValueTextView(playerWallet.getLowScore());
        setTopScoreValueTextView(playerWallet.getHighScore());
        setMyCodesValueTextView(playerWallet.getSize());
        setTotalScoreValueTextView(playerWallet.getTotalScore());

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // create main menu
                PopupMenu menu = new PopupMenu(QRStats.this, menuButton);
                menu.getMenuInflater()
                        .inflate(R.menu.fragment_popup_menu, menu.getMenu());

                // navigate to different activities based on menu item selected
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();

                        if (id == R.id.menu_home) {                 // go to AppHome page
                            startActivity(new Intent(QRStats.this, AppHome.class));
                            return true;

                        } else if (id == R.id.menu_stats) {         // remain on QRStats page
                            return true;

                        } else if (id == R.id.menu_profile) {       // go to MyProfile
                            startActivity(new Intent(QRStats.this, MyProfile.class));
                            return true;

                        } else if (id == R.id.menu_community) {     // go to Community
                            startActivity(new Intent(QRStats.this, Community.class));
                            return true;
                        }
                        return QRStats.super.onOptionsItemSelected(item);
                    }
                });
                menu.show();
            }
        });

        // add functionality to profile button
        AppCompatButton loginButton = findViewById(R.id.my_profile_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            /**

             This method handles the click event of the My Profile button in the QRStats activity and starts the MyProfile activity.
             @param v The view that was clicked
             */
            @Override
            public void onClick(View v) {

                // go to MyProfile page
                Intent goHome = new Intent(QRStats.this, MyProfile.class);
                startActivity(goHome);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        PlayerWallet playerWallet = AppStore.get().getCurrentPlayer().getPlayerWallet();
        setLowScoreValueTextView(playerWallet.getLowScore());
        setTopScoreValueTextView(playerWallet.getHighScore());
        setMyCodesValueTextView(playerWallet.getSize());
        setTotalScoreValueTextView(playerWallet.getTotalScore());
    }

    private void init() {

        menuButton = findViewById(R.id.menu_button);
        scoreIcon = findViewById(R.id.score_icon);
        totalScoreTextView = findViewById(R.id.total_score_textview);
        myCodesTextView = findViewById(R.id.my_codes_textview);
        topScoreTextView = findViewById(R.id.top_score_textview);
        lowScoreTextView = findViewById(R.id.low_score_textview);
        myProfileButton = findViewById(R.id.my_profile_button);
    }
    public ImageButton getMenuButton() {
        return menuButton;
    }

    public ImageButton getScoreIcon() {
        return scoreIcon;
    }

    public TextView getTotalScoreTextView() {
        return totalScoreTextView;
    }

    public TextView getMyCodesTextView() {
        return myCodesTextView;
    }

    public TextView getTopScoreTextView() {
        return topScoreTextView;
    }

    public TextView getLowScoreTextView() {
        return lowScoreTextView;
    }

    public AppCompatButton getMyProfileButton() {
        return myProfileButton;
    }

    private void setTotalScoreValueTextView(int totalScore){
        if(totalScore>0){
            this.totalScoreValueTextView.setText(Integer.toString(totalScore));
        }else{
            this.totalScoreValueTextView.setText(R.string.no_codes_scanned);
        }
    }

    private void setMyCodesValueTextView(int numCodes){
        if(numCodes>0){
            this.myCodesValueTextView.setText(Integer.toString(numCodes));
        }else{
            this.myCodesValueTextView.setText(R.string.no_codes_scanned);
        }
    }

    private void setTopScoreValueTextView(int topScore){
        if(topScore>0){
            this.topScoreValueTextView.setText(Integer.toString(topScore));
        }else{
            this.topScoreValueTextView.setText(R.string.no_codes_scanned);
        }
    }

    private void setLowScoreValueTextView(int lowScore){
        if(lowScore>0){
            this.lowScoreValueTextView.setText(Integer.toString(lowScore));
        }else{
            this.lowScoreValueTextView.setText(R.string.no_codes_scanned);
        }
    }
}