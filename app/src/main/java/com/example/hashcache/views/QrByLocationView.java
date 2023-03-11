package com.example.hashcache.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hashcache.R;

public class QrByLocationView extends RelativeLayout {
    private ListView locationListView;
    private TextView qrNearTextView;
    private RadioGroup rangeChoices;
    private RadioButton oneKmRadioButton;
    private RadioButton fiveKmRadioButton;
    private RadioButton tenKmRadioButton;

    public QrByLocationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.qr_by_location, this);

        locationListView = findViewById(R.id.location_listview);
        qrNearTextView = findViewById(R.id.qr_near_textview);
        rangeChoices = findViewById(R.id.range_choices);
        oneKmRadioButton = findViewById(R.id.onekm_radiobutton);
        fiveKmRadioButton = findViewById(R.id.fivekm_radiobutton);
        tenKmRadioButton = findViewById(R.id.tenkm_radiobutton);
    }

    public ListView getLocationListView() {
        return locationListView;
    }

    public TextView getQrNearTextView() {
        return qrNearTextView;
    }

    public RadioGroup getRangeChoices() {
        return rangeChoices;
    }

    public RadioButton getOneKmRadioButton() {
        return oneKmRadioButton;
    }

    public RadioButton getFiveKmRadioButton() {
        return fiveKmRadioButton;
    }

    public RadioButton getTenKmRadioButton() {
        return tenKmRadioButton;
    }

    public void setOneKmRadioButtonChecked(boolean checked) {
        oneKmRadioButton.setChecked(checked);
    }

    public void setFiveKmRadioButtonChecked(boolean checked) {
        fiveKmRadioButton.setChecked(checked);
    }

    public void setTenKmRadioButtonChecked(boolean checked) {
        tenKmRadioButton.setChecked(checked);
    }
}
