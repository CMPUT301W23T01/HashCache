package com.example.hashcache.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.widget.AppCompatButton;

import com.example.hashcache.R;

public class CommunityView extends RelativeLayout {
    private ImageButton mMenuButton;
    private ImageButton mSearchButton;
    private EditText mSearchEditText;
    private ListView mUserListView;
    private AppCompatButton mLeaderboardButton;

    public CommunityView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.community, this);
        mMenuButton = findViewById(R.id.menu_button);
        mSearchButton = findViewById(R.id.search_button);
        mSearchEditText = findViewById(R.id.search_bar_edittext);
        mUserListView = findViewById(R.id.user_listview);
        mLeaderboardButton = findViewById(R.id.leaderboard_button);
    }

    public void setMenuButtonListener(OnClickListener listener) {
        mMenuButton.setOnClickListener(listener);
    }

    public void setSearchButtonListener(OnClickListener listener) {
        mSearchButton.setOnClickListener(listener);
    }

    public String getSearchQuery() {
        return mSearchEditText.getText().toString();
    }

    public void setSearchQuery(String query) {
        mSearchEditText.setText(query);
    }

    public void setUserListViewAdapter(ListAdapterView adapter) {
        mUserListView.setAdapter(adapter);
    }

    public void setUserListViewEmptyView(View view) {
        mUserListView.setEmptyView(view);
    }

    public void setLeaderboardButtonListener(OnClickListener listener) {
        mLeaderboardButton.setOnClickListener(listener);
    }
}

