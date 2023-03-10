/*
 * MyProfile
 *
 * User's profile page.
 * Displays scrollable list of QR monsters scanned by the user.
 * Top logo button allows navigation to user settings page.
 * Selecting a monster navigates to that monster's info page.
 * Additional buttons permit navigation to other pages.
 */

package com.example.hashcache;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.PopupMenu;

import com.example.hashcache.models.ImageGenerator;

public class MyProfile extends AppCompatActivity {
    private ImageView imageView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
        imageView = findViewById(R.id.imageView);
        Bitmap bitmap = ImageGenerator.generateImage(getApplicationContext());
        imageView.setImageBitmap(bitmap);
        // add functionality to logo button
        ImageButton logoButton = findViewById(R.id.logo_button);
        logoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // go to user settings page
                startActivity(new Intent(MyProfile.this, Settings.class));
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
            public void onClick(View v) {

                // create menu
                PopupMenu menu = new PopupMenu(MyProfile.this, menuButton);
                menu.getMenuInflater()
                        .inflate(R.menu.fragment_popup_menu, menu.getMenu());

                // navigate to different activities based on menu item selected
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();

                        if (id == R.id.menu_home) {                 // go to AppHome page
                            startActivity(new Intent(MyProfile.this, AppHome.class));
                            return true;

                        } else if (id == R.id.menu_stats) {         // go to QRStats page
                            startActivity(new Intent(MyProfile.this, QRStats.class));
                            return true;

                        } else if (id == R.id.menu_profile) {       // remain on MyProfile
                            return true;

                        } else if (id == R.id.menu_community) {     // go to Community
                            startActivity(new Intent(MyProfile.this, Community.class));
                            return true;
                        }
                        return MyProfile.super.onOptionsItemSelected(item);
                    }
                });
                menu.show();
            }
        });
    }
}
