/*
 * QRStats
 *
 * Shows statistics for current user.
 * Additional buttons permit navigation to other pages.
 */

package com.example.hashcache.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hashcache.R;
import com.example.hashcache.appContext.AppContext;
import com.example.hashcache.models.PlayerWallet;
import com.example.hashcache.models.ScannableCode;

import java.util.Observable;
import java.util.Observer;

/**
 * The QRStats activity displays statistics for the current user, including
 * total score, number of codes scanned,
 * highest score achieved, and lowest score achieved. It also provides buttons
 * for navigating to other pages.
 */
public class QRStats extends AppCompatActivity implements Observer {
    private ImageButton menuButton;
    private ImageButton scoreIcon;
    private TextView totalScoreTextView;
    private TextView myCodesTextView;
    private TextView topScoreValueTextView;
    private TextView lowScoreValueTextView;

    /**
     * Initializes the activity and sets the layout. Also adds functionality to the
     * menu button.
     *
     * @param savedInstanceState a Bundle object containing the activity's
     *                           previously saved state, if any
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_stats);

        menuButton = findViewById(R.id.menu_button);
        totalScoreTextView = findViewById(R.id.total_score_value);
        myCodesTextView = findViewById(R.id.total_score_value);
        topScoreValueTextView = findViewById(R.id.top_score_value);
        lowScoreValueTextView = findViewById(R.id.low_score_value);

        topScoreValueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highestScoringCodeClicked();
            }
        });

        lowScoreValueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lowestScoringCodeClicked();
            }
        });

        // add functionality to menu button
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                BottomMenuFragment bottomMenu = new BottomMenuFragment();
                bottomMenu.show(getSupportFragmentManager(), bottomMenu.getTag());
            }
        });

        init();
        AppContext.get().addObserver(this);
    }

    /**
     * Called whenever the activity resumes
     */
    @Override
    protected void onResume() {
        super.onResume();
        updateValues();
    }

    private void updateValues() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PlayerWallet curWallet = AppContext.get().getCurrentPlayer().getPlayerWallet();
                ScannableCode lowestScan = AppContext.get().getLowestScannableCode();
                ScannableCode highestScan = AppContext.get().getHighestScannableCode();
                long totalScore = AppContext.get().getCurrentPlayer().getPlayerWallet().getTotalScore();
                setMyCodesValue((int)curWallet.getQrCount());
                setLowScoreValue(lowestScan);
                setHighScoreValue(highestScan);
                setTotalScoreValue(totalScore);
            }
        });
    }

    private void highestScoringCodeClicked() {
        ScannableCode highestScan = AppContext.get().getHighestScannableCode();
        AppContext.get().setCurrentScannableCode(highestScan);
        Intent intent = new Intent(getApplicationContext(), DisplayMonsterActivity.class);
        intent.putExtra("belongsToCurrentUser", true);
        startActivity(intent);
    }

    private void lowestScoringCodeClicked() {
        ScannableCode lowestScan = AppContext.get().getLowestScannableCode();
        AppContext.get().setCurrentScannableCode(lowestScan);
        Intent intent = new Intent(getApplicationContext(), DisplayMonsterActivity.class);
        intent.putExtra("belongsToCurrentUser", true);
        startActivity(intent);
    }

    private void init() {
        menuButton = findViewById(R.id.menu_button);
        scoreIcon = findViewById(R.id.score_icon);
        totalScoreTextView = findViewById(R.id.total_score_value);
        myCodesTextView = findViewById(R.id.my_codes_value);
    }

    private void setHighScoreValue(ScannableCode highestScoring) {
        if(highestScoring!=null){
            topScoreValueTextView.setText(String.valueOf(highestScoring.getHashInfo().getGeneratedScore()));
            topScoreValueTextView.setClickable(true);
        }else{
            topScoreValueTextView.setText(R.string.no_codes_scanned);
            topScoreValueTextView.setClickable(false);
        }
    }

    private void setLowScoreValue(ScannableCode lowestScoring) {
        if(lowestScoring!=null){
            lowScoreValueTextView.setText(Long.toString(lowestScoring.getHashInfo().getGeneratedScore()));
            lowScoreValueTextView.setClickable(true);
        }else{
            lowScoreValueTextView.setText(R.string.no_codes_scanned);
            lowScoreValueTextView.setClickable(false);
        }

    }

    private void setTotalScoreValue(long score) {
        totalScoreTextView.setText(String.valueOf(score));
    }

    private void setMyCodesValue(int value) {
        myCodesTextView.setText(String.valueOf(value));
    }

    private ImageButton getMenuButton() {
        return menuButton;
    }

    /**
     * Called when the observer's observable object updates
     * @param observable     the observable object.
     * @param o   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable observable, Object o) {
        updateValues();
    }
}
