package com.example.hashcache.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hashcache.R;
import com.example.hashcache.context.Context;
import com.example.hashcache.models.Comment;
import com.example.hashcache.models.Player;

import java.util.ArrayList;

public class DisplayCommentsActivity extends AppCompatActivity {
    private ListView commentsList;
    private CommentsArrayAdapter commentsArrayAdapter;
    private ArrayList<Comment> comments;
    private boolean belongToCurrentUser;


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
     * @see Context
     * @see Player
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_comments);

        Intent intent = getIntent();
        belongToCurrentUser = intent.getBooleanExtra("belongsToCurrentUser", false);

        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomMenuFragment bottomMenu = new BottomMenuFragment();
                bottomMenu.show(getSupportFragmentManager(), bottomMenu.getTag());
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        comments = Context.get().getCurrentScannableCode().getComments();
        commentsList = findViewById(R.id.comment_listview_content);
        commentsArrayAdapter = new CommentsArrayAdapter(this,
                comments);
        commentsList.setAdapter(commentsArrayAdapter);
    }
}
