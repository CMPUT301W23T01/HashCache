package com.example.hashcache.views;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hashcache.R;

public class SettingsView extends RelativeLayout {

    private ImageButton menuButton;
    private ImageButton logoIcon;
    private TextView usernameTextView;
    private TextView phoneTextView;
    private TextView emailTextView;
    private View background;
    private CheckBox geolocationCheckbox;

    public SettingsView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Inflate the layout
        LayoutInflater.from(context).inflate(R.layout.settings, this);

        // Get references to the UI elements
        menuButton = findViewById(R.id.menu_button);
        logoIcon = findViewById(R.id.logo_icon);
        usernameTextView = findViewById(R.id.username_textview);
        phoneTextView = findViewById(R.id.phone_textview);
        emailTextView = findViewById(R.id.email_textview);
        background = findViewById(R.id.background);
        geolocationCheckbox = findViewById(R.id.geolocation_checkbox);
    }

    public ImageButton getMenuButton() {
        return menuButton;
    }

    public ImageButton getLogoIcon() {
        return logoIcon;
    }

    public TextView getUsernameTextView() {
        return usernameTextView;
    }

    public TextView getPhoneTextView() {
        return phoneTextView;
    }

    public TextView getEmailTextView() {
        return emailTextView;
    }

    public View getBackgroundView() {
        return background;
    }

    public CheckBox getGeolocationCheckbox() {
        return geolocationCheckbox;
    }
}
