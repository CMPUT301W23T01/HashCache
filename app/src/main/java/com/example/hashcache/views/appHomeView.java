package com.example.hashcache.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.example.hashcache.R;

public class appHomeView extends RelativeLayout {
    private ImageButton mLogoButton;
    private TextView mUsernameTextView;
    private TextView mScoreTextView;
    private ImageButton mMenuButton;
    private ImageButton mMapButton;
    private ImageButton mCommunityButton;
    private View mTempMap;
    private AppCompatButton mScanQrButton;


    public appHomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void initView() {
        inflate(getContext(), R.layout.app_home, this);
        mLogoButton = findViewById(R.id.logo_button);
        mUsernameTextView = findViewById(R.id.username_textview);
        mScoreTextView = findViewById(R.id.score_textview);
        mMenuButton = findViewById(R.id.menu_button);
        mMapButton = findViewById(R.id.map_button);
        mCommunityButton = findViewById(R.id.community_button);
        mTempMap = findViewById(R.id.temp_map);
        mScanQrButton = findViewById(R.id.scan_qr_button);
    }

    public void setLogoButtonListener(OnClickListener listener) {
        mLogoButton.setOnClickListener(listener);
    }

    public void setMenuButtonListener(OnClickListener listener) {
        mMenuButton.setOnClickListener(listener);
    }

    public void setMapButtonListener(OnClickListener listener) {
        mMapButton.setOnClickListener(listener);
    }

    public void setCommunityButtonListener(OnClickListener listener) {
        mCommunityButton.setOnClickListener(listener);
    }

    public void setScanQrButtonListener(OnClickListener listener) {
        mScanQrButton.setOnClickListener(listener);
    }

    public String getUsername() {
        return mUsernameTextView.getText().toString();
    }

    public void setUsername(String username) {
        mUsernameTextView.setText(username);
    }

    public int getScore() {
        String scoreStr = mScoreTextView.getText().toString().replace("Score: ", "");
        return Integer.parseInt(scoreStr);
    }

    public void setScore(int score) {
        mScoreTextView.setText("Score: " + score);
    }

    public void setMapTempViewClickListener(OnClickListener listener) {
        mTempMap.setOnClickListener(listener);
    }
}
