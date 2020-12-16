package com.oakland.ekit

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oakland.ekit.Constants.Companion.MessageModel
import com.oakland.ekit.Constants.Companion.Ticket
import com.oakland.ekit.ui.login.LoginViewModelFactory
import com.oakland.ekit.viewModels.OpenTicketsViewModel
import com.oakland.ekit.viewModels.TicketItemViewModel
import kotlinx.android.synthetic.main.activity_create_user.*
import org.w3c.dom.Text
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class TicketItemActivity : AppCompatActivity(), View.OnClickListener {

    private var mViewModel: TicketItemViewModel? = null

    var lblTicketTitle: TextView? = null
    var lblUserName: TextView? = null
    var lblOpenDate: TextView? = null
    var lblProduct: TextView? = null
    var btnCloseTicket: Button? = null

    var ticketCommentView: RecyclerView? = null

    var mTicket: Ticket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_item)
        setSupportActionBar(toolbar)

        //set the view model
        mViewModel = ViewModelProviders.of(this, LoginViewModelFactory()).get<TicketItemViewModel>(TicketItemViewModel::class.java)

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
        this.lblUserName = findViewById(R.id.lblUsersName)
        this.lblOpenDate = findViewById(R.id.lblOpenDate)
        this.btnCloseTicket = findViewById(R.id.btnClose)
        this.ticketCommentView = findViewById(R.id.rvTicketComments)
        this.lblProduct = findViewById(R.id.lblProduct)

        this.btnCloseTicket!!.setOnClickListener(this)

        //call to populate the views
        this.populateTicketInfo()

    }

    //Used to populate the ticket view with the data
    private fun populateTicketInfo(){

        //populate the views with the data
        this.lblTicketTitle!!.text = "Ticket ID #${this.mTicket!!.id}"
//        this.lblContent!!.text = "Content: ${this.mTicket!!.content}"
        this.lblUserName!!.text = "Created By: ${this.mTicket!!.firstName + " " + this.mTicket!!.lastName}"
        this.lblOpenDate!!.text = "Open Date: ${this.mTicket!!.createdDate}"

        //make sure our ticket has a product assigned
        if(this.mTicket!!.ticketProduct != null){
            this.lblProduct!!.text = "Suggested Product: ${this.mTicket!!.ticketProduct!!.productName}"
        }




        //call to make and populate the ticket comments view
        this.makeTicketComments()


    }


    //Used to make the ticket comments view
    private fun makeTicketComments(){

        //make a list for all the ticket comment messages
        val messagesList: ArrayList<MessageModel> = ArrayList()


        //get the current logged in users id to use for comparison in the message sender/recipient
        val loggedInUserId = this.mViewModel!!.getLoggedInUser().userId.toInt()



        //go through each of the comments and extract the message
        for(comment in this.mTicket!!.comments){

            //perform required sdk check for date
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                //if the message list size is not zero, then we need to check if this new message is a different day
                if(messagesList.size != 0){

                    //see if this current message is a new day or not from the previous one
                    if (
                            messagesList[messagesList.size - 1].messageTime.dayOfMonth != ZonedDateTime.parse(comment.commentCreatedDate).dayOfMonth ||
                            messagesList[messagesList.size - 1].messageTime.month != ZonedDateTime.parse(comment.commentCreatedDate).month
                    ) {

                        //new day so lets add the date to our message viewer
                        messagesList.add(MessageModel("", CommentsListAdapter.MESSAGE_DATE, ZonedDateTime.parse(comment.commentCreatedDate)))

                    }

                }else{ //this is the first message

                    //new day so lets add the date to our message viewer
                    messagesList.add(MessageModel("", CommentsListAdapter.MESSAGE_DATE, ZonedDateTime.parse(comment.commentCreatedDate)))

                }


            }


            var messageType = CommentsListAdapter.MESSAGE_TYPE_IN

            //figure out which message time (aka who send it)
            if(comment.user.mId == loggedInUserId){

                //this is a comment from the current user so lets mark it as an "out" message
                messageType = CommentsListAdapter.MESSAGE_TYPE_OUT

            }

            //because the date time zone conventions require sdk check, only add if it fits the check
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                //create the message and add it to our list of messages
                messagesList.add(MessageModel(comment.content, messageType, ZonedDateTime.parse(comment.commentCreatedDate)))

            }


        }


        //make our list adapter
        val adapter = CommentsListAdapter(this, messagesList)


        //add all the comments to our view and adapter
        this.ticketCommentView!!.layoutManager = LinearLayoutManager(this)
        this.ticketCommentView!!.adapter = adapter


    }



    override fun onClick(v: View?) {

        //switch on the view item that was clicked
        when(v){

            btnCloseTicket ->{

                //TODO: try and update the ticket with a closed date this would effectively remove the tickets from the view

                //this.mTicket!!.

            }


        }
    }


}
