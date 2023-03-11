package com.example.hashcache.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.example.hashcache.R;

public class QrStatsView extends RelativeLayout {

    private ImageButton menuButton;
    private ImageButton scoreIcon;
    private TextView totalScoreTextView;
    private TextView myCodesTextView;
    private TextView topScoreTextView;
    private TextView lowScoreTextView;
    private AppCompatButton myProfileButton;

    public QrStatsView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Inflate the layout
        LayoutInflater.from(context).inflate(R.layout.qr_stats, this);

        // Get references to the UI elements
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
}
