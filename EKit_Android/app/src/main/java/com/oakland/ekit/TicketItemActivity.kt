package com.oakland.ekit

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_user.*
import com.oakland.ekit.Constants.Companion.Ticket

class TicketItemActivity : AppCompatActivity() {

    var lblTicketTitle: TextView? = null

    var mTicket: Ticket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_item)
        setSupportActionBar(toolbar)

        // gets intent and passed data
        val myIntent = intent
        val ticketData = myIntent.getSerializableExtra("ticketData") as Ticket

        //save the ticket locally
        if(ticketData != null){
            this.mTicket = ticketData
        }else{
            //error happened so exit ticket view
            Toast.makeText(this, "Error Loading Ticket", Toast.LENGTH_LONG).show()
            super.onBackPressed()
        }

        //Call to init the ui
        this.initUI()

    }


    //Used to init the UI
    private fun initUI(){


        //init the ui
        this.lblTicketTitle = findViewById(R.id.lblTicketTitle)


        //call to populate the views
        this.populateTicketInfo()


    }

    //Used to populate the ticket view with the data
    private fun populateTicketInfo(){

        //populate the views with the data
        this.lblTicketTitle!!.text = "Ticket #${this.mTicket!!.id}"

    }





}
