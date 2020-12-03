package com.oakland.ekit;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.oakland.ekit.ui.login.LoginViewModelFactory;
import com.oakland.ekit.viewModels.AdminHomePageViewModel;

import java.util.ArrayList;
import java.util.List;

public class AdminHomePageActivity extends AppCompatActivity implements View.OnClickListener {

    private AdminHomePageViewModel mViewModel = null;

    private final static String TAG = UserHomepageActivity.class.getSimpleName();
    private Context mContext = null;

    private RecyclerView recyclerView;
    private AdminListAdapter mainListAdapter;
    private List<MainListData> mainListDataList = new ArrayList<>();
    private Button btnSideView = null;

    private TextView lblTempTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        //Remove notification bar and set the context view
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin_homepage);

        //set the view model
        this.mViewModel = ViewModelProviders.of(this, new LoginViewModelFactory()).get(AdminHomePageViewModel.class);

        //Call to init the ui
        initUI();

        //prepare the data for the table view
        dataPrepare();


//
//
////        //Test code of login requests
//        Thread thread = new Thread(() -> {
//            try  {
//
//                //request the authentication token TODO: only need to do this the one time and then save token until we log out of app if token exists, login again
//                JsonObject testRequestBody = new JsonObject();
//
//                testRequestBody.addProperty("username", "admin");
//                testRequestBody.addProperty("password", "admin");
//                testRequestBody.addProperty("rememberMe", "true");
//
//                JSONObject postReturnJson = ServerManager.post("authenticate", testRequestBody);
//
//                String tokenKey = postReturnJson.getString("id_token");
//
//
//
//
//                //request to get the user data info
//                String response = ServerManager.sendGet("account", tokenKey);
//
//                System.out.println(response);
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//
//        thread.start();




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
        mainListAdapter = new AdminListAdapter(mainListDataList);
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mainListAdapter);


    }

    private void dataPrepare() {

        //TODO: remove the age param
        MainListData data = new MainListData("Open Tickets", 25);
        mainListDataList.add(data);
//        data = new MainListData("Survey", 20);
//        mainListDataList.add(data);
//        data = new MainListData("Product Guide", 20);
//        mainListDataList.add(data);
//        data = new MainListData("Diagnosis Tool", 20);
//        mainListDataList.add(data);
//        data = new MainListData("Assembly Guide", 20);
//        mainListDataList.add(data);


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
        }else if(id == R.id.action_change_pass){

            //Call to prompt user to change their password
            showChangePasswordDialog();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        //determine which view is clicked
        switch (view.getId()){

            case R.id.btnSideMenu:

                //add popup menu to the button
                PopupMenu po=new PopupMenu(AdminHomePageActivity.this, view);
                po.getMenuInflater().inflate(R.menu.main_menu, po.getMenu());
                po.setOnMenuItemClickListener(this::onOptionsItemSelected);

                //show the menu
                po.show();


                break;


        }


    }


    //Used to present a change password dialog box
    private void showChangePasswordDialog() {

        LayoutInflater li = LayoutInflater.from(this.mContext);
        View promptsView = li.inflate(R.layout.dialog_change_password, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.mContext);

        alertDialogBuilder.setView(promptsView);

        final EditText txtCurrentPass = (EditText) promptsView.findViewById(R.id.txtCurrentPass);
        final EditText txtNewPass = (EditText) promptsView.findViewById(R.id.txtNewPass);
        final EditText txtNewPassVerify = (EditText) promptsView.findViewById(R.id.txtNewPassVerify);


        // set dialog message
        alertDialogBuilder
                .setCancelable(true)
                .setTitle("Change Password")
                .setPositiveButton("Change", (dialog, id) -> {

                    String currentPass = txtCurrentPass.getText().toString();
                    String newPass = txtNewPass.getText().toString();
                    String newPassVerify = txtNewPassVerify.getText().toString();

                    //if they do not match, lets dismiss and prompt error
                    if(!newPass.equals(newPassVerify)){ //TODO: also make sure answer in each and password is at least 4 char

                        //not the same
                        Toast.makeText(mContext, "Values don't match!", Toast.LENGTH_LONG).show();

                    }else{

                        //TODO: finish
                        //its all good to try and send
                        Toast.makeText(mContext, "Feature coming soon!", Toast.LENGTH_LONG).show();

                    }

                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel()).show();




    }


}
