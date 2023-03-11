package com.example.hashcache.views;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import com.example.hashcache.R;

public class ProfileView extends RelativeLayout {
    private View mPurpleRect;
    private ImageButton mLogoButton;
    private TextView mUsernameTextView;
    private TextView mScoreTextView;
    private ImageButton mMenuButton;
    private ListView mTempList;
    private AppCompatButton mQRStatsButton;

    public ProfileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.my_profile, this);
        mPurpleRect = findViewById(R.id.purple_rect);
        mLogoButton = findViewById(R.id.logo_button);
        mUsernameTextView = findViewById(R.id.username_textview);
        mScoreTextView = findViewById(R.id.score_textview);
        mMenuButton = findViewById(R.id.menu_button);
        mTempList = findViewById(R.id.temp_list);
        mQRStatsButton = findViewById(R.id.qr_stats_button);
    }

    public void setLogoButtonListener(OnClickListener listener) {
        mLogoButton.setOnClickListener(listener);
    }

    public void setMenuButtonListener(OnClickListener listener) {
        mMenuButton.setOnClickListener(listener);
    }

    public void setUsername(String username) {
        mUsernameTextView.setText(username);
    }

    public void setScore(int score) {
        mScoreTextView.setText("Score: " + score);
    }

    public void setListAdapter(ProfileListAdapter adapter) {
        mTempList.setAdapter(adapter);
    }

    public void setEmptyView(View view) {
        mTempList.setEmptyView(view);
    }

    public void setQRStatsButtonListener(OnClickListener listener) {
        mQRStatsButton.setOnClickListener(listener);
    }
}
