package com.example.hashcache.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.example.hashcache.R;
import com.example.hashcache.context.Context;
import com.example.hashcache.controllers.UpdateContactInfoCommand;
import com.example.hashcache.models.ContactInfo;
import com.example.hashcache.models.database.Database;

/**

 Popup fragment for editing player info.

 Appears on-screen when user taps the edit button on SettingsActivity page.
 */

public class EditPlayerInfoFragment extends DialogFragment {
    private ImageButton xButton;
    private EditText editEmail;
    private EditText editPhoneNumber;
    private AppCompatButton confirmButton;
    private ContactInfo currentContactInfo;

    public EditPlayerInfoFragment() {
    }

    /**
     * Initializes EditPlayerInfoFragment
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Sets the EditText objects, close button, and confirmation button and
     * sets click listeners for the buttons.
     * Initializes the values of the email and phone number EditText objects.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_player_info, viewGroup, false);

        xButton = view.findViewById(R.id.x_button);
        editEmail = view.findViewById(R.id.email_edit_text);
        editPhoneNumber = view.findViewById(R.id.edit_phone_number);
        confirmButton = view.findViewById(R.id.confirm_player_info_button);

        // add functionality to x button
        xButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // close fragment
                dismiss();
            }
        });

        // add functionality to confirm button
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            // save new contact information
            public void onClick(View v) {
                onConfirmClicked();
            }
        });

        initValues();

        return view;
    }

    /**
     * Initializes the values of the email and phone number EditText objects.
     * Gets the current contact information of the user and sets the text of the EditText objects
     * with the current values of the user's email and phone number.
     */
    private void initValues(){
        currentContactInfo = Context.get().getCurrentPlayer().getContactInfo();
        System.out.println(currentContactInfo.getEmail());
        editEmail.setText(currentContactInfo.getEmail());
        editPhoneNumber.setText(currentContactInfo.getPhoneNumber());
    }

    /**
     * Saves the new contact information of the user to the database.
     * Creates a new ContactInfo object with the values in the EditText objects.
     * Calls the UpdateContactInfoCommand to update the contact information in the database.
     * Upon successful completion, starts the Settings activity.
     */
    private void onConfirmClicked(){
        String emailText = editEmail.getText().toString();
        String phoneNumberText = editPhoneNumber.getText().toString();
        ContactInfo newContactInfo = new ContactInfo();

        //TODO: FE input validation
        newContactInfo.setPhoneNumber(phoneNumberText);
        newContactInfo.setEmail(emailText);

        UpdateContactInfoCommand.updateContactInfoCommand(Context.get().getCurrentPlayer().getUserId(),
                        newContactInfo, Database.getInstance(), Context.get())
                .thenAccept(isComplete->{
                    if(isComplete){
                        dismiss();
                    }
                });
    }
}
