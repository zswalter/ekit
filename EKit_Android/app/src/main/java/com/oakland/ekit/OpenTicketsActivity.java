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
import com.oakland.ekit.viewModels.OpenTicketsViewModel;
import com.oakland.ekit.Constants.Companion.Ticket;

import java.util.ArrayList;
import java.util.List;

public class OpenTicketsActivity extends AppCompatActivity implements View.OnClickListener {

    private OpenTicketsViewModel mViewModel = null;

    private final static String TAG = UserHomepageActivity.class.getSimpleName();
    private Context mContext = null;

    private RecyclerView recyclerView;
    private OpenTicketsListAdapter mainListAdapter;
    private List<OpenTicketListData> mainListDataList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        //Remove notification bar and set the context view
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_open_tickets);

        //set the view model
        this.mViewModel = ViewModelProviders.of(this, new LoginViewModelFactory()).get(OpenTicketsViewModel.class);


        //Call to init the ui
        initUI();

        //prepare the data for the table view
        dataPrepare();


    }



    //Used to init the UI
    private void initUI(){

        recyclerView = findViewById(R.id.recyclerViewOpenTickets);
        //add adapters and on touch listener and grid layout
        mainListAdapter = new OpenTicketsListAdapter(mainListDataList);
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mainListAdapter);


    }

    private void dataPrepare() {

        Thread thread = new Thread(() -> {
            try  {

                //call to get the open tickets from the server
                ArrayList<Ticket> tickets = this.mViewModel.getOpenTickets();

                //call to run on main thread
                this.runOnUiThread(() -> {

                    //Once the tickets have been received from the backend, lets create the ticket items to view
                    for(Ticket ticket : tickets){

                            //create the card holder for the ticket and add it to the view list
                            OpenTicketListData data = new OpenTicketListData("Ticket #" + ticket.getId(), ticket);
                            mainListDataList.add(data);

                    }

                    //now that we have added all the data lets tell the adapter to update
                    this.mainListAdapter.notifyDataSetChanged();

                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        thread.start();


    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        //add the menu to the view
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return true;
//    }

    @Override
    public void onClick(View view) {

        //determine which view is clicked
        switch (view.getId()){

            case R.id.btnSideMenu:

                //add popup menu to the button
                PopupMenu po=new PopupMenu(OpenTicketsActivity.this, view);
                po.getMenuInflater().inflate(R.menu.main_menu, po.getMenu());
                po.setOnMenuItemClickListener(this::onOptionsItemSelected);

                //show the menu
                po.show();


                break;


        }


    }


}
