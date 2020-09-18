package com.oakland.ekit;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserHomepageActivity extends AppCompatActivity {


    private final static String TAG = UserHomepageActivity.class.getSimpleName();
    private Context mContext = null;

    private TextView lblTempTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        setContentView(R.layout.activity_user_homepage);


        //temp stuff
        lblTempTextView = findViewById(R.id.lblTempTextLabel);
        String welcomeString = getString(R.string.welcome) + SettingsManager.sharedInstance.mLoggedInUser.getDisplayName();
        lblTempTextView.setText(welcomeString);




    }


}
