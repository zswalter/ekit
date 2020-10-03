package com.oakland.ekit;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserHomepageActivity extends AppCompatActivity {


    private final static String TAG = UserHomepageActivity.class.getSimpleName();
    private Context mContext = null;

    private RecyclerView recyclerView;
    private MainListAdapter mainListAdapter;
    private List<MainListData> mainListDataList = new ArrayList<>();

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


        recyclerView = findViewById(R.id.recyclerViewMainOptions);
        mainListAdapter = new MainListAdapter(mainListDataList);
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mainListAdapter);


        dataPrepare();

    }

    private void dataPrepare() {
        MainListData data = new MainListData("Profile", 25);
        mainListDataList.add(data);
        data = new MainListData("Survey", 20);
        mainListDataList.add(data);

//        Collections.sort(mainListDataList, new Comparator() {
//            @Override
//            public int compare(MainListData o1, MainListData o2) {
//                return o1.name.compareTo(o2.name);
//            }
//        });
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
