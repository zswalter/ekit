package com.oakland.ekit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.oakland.ekit.ui.login.LoginViewModelFactory;
import com.oakland.ekit.viewModels.UserHomePageViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserHomepageActivity extends AppCompatActivity implements View.OnClickListener {

    private UserHomePageViewModel mViewModel = null;

    private final static String TAG = UserHomepageActivity.class.getSimpleName();
    private Context mContext = null;

    private RecyclerView recyclerView;
    private MainListAdapter mainListAdapter;
    private List<MainListData> mainListDataList = new ArrayList<>();
    private Button btnSideView = null;

    private TextView lblTempTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        //Remove notification bar and set the context view
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_homepage);

        //set the view model
        this.mViewModel = ViewModelProviders.of(this, new LoginViewModelFactory()).get(UserHomePageViewModel.class);

        //Call to init the ui
        initUI();

        //prepare the data for the table view
        dataPrepare();



    }



    //Used to init the UI
    private void initUI(){


        this.btnSideView = findViewById(R.id.btnSideMenu);
        this.btnSideView.setOnClickListener(this);

        //temp stuff
        lblTempTextView = findViewById(R.id.lblTempTextLabel);
        String welcomeString = getString(R.string.welcome) + mViewModel.getLoggedInUser().getDisplayName();
        lblTempTextView.setText(welcomeString);


        recyclerView = findViewById(R.id.recyclerViewMainOptions);
        //add adapters and on touch listener and grid layout
        mainListAdapter = new MainListAdapter(mainListDataList);
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mainListAdapter);


    }

    private void dataPrepare() {

        //TODO: remove the age param
        MainListData data = new MainListData("Account Information", 25);
        mainListDataList.add(data);
        data = new MainListData("Survey", 20);
        mainListDataList.add(data);
        data = new MainListData("Product Guide", 20);
        mainListDataList.add(data);
        data = new MainListData("Diagnosis Tool", 20);
        mainListDataList.add(data);
        data = new MainListData("Assembly Guide", 20);
        mainListDataList.add(data);


//        Collections.sort(mainListDataList, new Comparator() {
//            @Override
//            public int compare(MainListData o1, MainListData o2) {
//                return o1.name.compareTo(o2.name);
//            }
//        });
    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        //add the menu to the view
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return true;
//    }

    @Override
    public void onBackPressed(){

        //Build a warning popup box
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setTitle(R.string.title_are_you_sure).setMessage(R.string.message_back_pressed_exit_app)
                .setNeutralButton("Cancel", null)
                .setNegativeButton("Continue", (dialogInterface, i) -> {

                    super.onBackPressed();

                }).create().show();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //check which menu item was pressed
        if(id == android.R.id.home){

            //TODO: if user presses the home button maybe do something?? what is this button???

        }
        else if(id == R.id.action_logout){
            //Call to logout the user
            mViewModel.logOutUser();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {

        //determine which view is clicked
        switch (view.getId()){

            case R.id.btnSideMenu:


                //add popup menu to the button
                PopupMenu po=new PopupMenu(UserHomepageActivity.this, view);
                po.getMenuInflater().inflate(R.menu.main_menu, po.getMenu());
                po.setOnMenuItemClickListener(this::onOptionsItemSelected);

                //show the menu
                po.show();


                break;


        }


    }
}
