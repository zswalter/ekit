package com.oakland.ekit;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //add the menu to the view
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //check which menu item was pressed
        if(id == android.R.id.home){

            //TODO: if user presses the home button maybe do something??

        }
        else if(id == R.id.action_logout){
            //TODO: call to logout user
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
