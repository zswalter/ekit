package com.oakland.ekit

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.lifecycle.ViewModelProviders
import com.oakland.ekit.ui.login.LoginViewModelFactory
import com.oakland.ekit.viewModels.AccountInformationViewModel
import kotlinx.android.synthetic.main.activity_user_information.*
import java.time.LocalDate
import java.time.Period
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class UserInformationActivity : AppCompatActivity() {

    private var mViewModel : AccountInformationViewModel? = null
    private var mContext: Context? = null

    private var mIsEdit = false

    //Define UI views
    private var mBtnEdit: Button? = null
    private var mBtnSave: Button? = null
    private var mTxtFirstName: EditText? = null
    private var mTxtLastName: EditText? = null
    private var mTxtEmailAddress: EditText? = null
    private var mLblMemberSince: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set the context
        this.mContext  = this

        //set the view model
        this.mViewModel = ViewModelProviders.of(this, LoginViewModelFactory()).get<AccountInformationViewModel>(AccountInformationViewModel::class.java)

        setContentView(R.layout.activity_user_information)
        setSupportActionBar(findViewById(R.id.toolbar))

        //Call to init the UI
        initUI(mViewModel!!.getUserInfo())


    }

    override fun onBackPressed() { //Build a warning popup box
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle(R.string.title_are_you_sure).setMessage("Exiting when editing will forget all changes. Continue?")
                .setNeutralButton("Cancel", null)
                .setNegativeButton("Continue") { dialogInterface: DialogInterface?, i: Int -> super.onBackPressed() }.create().show()
    }

    //Used to init/build the ui with the users info
    private fun initUI(info: UserInfo){

        this.mTxtFirstName = findViewById(R.id.txtFirstName)
        this.mTxtLastName = findViewById(R.id.txtLastName)
        this.mTxtEmailAddress = findViewById(R.id.txtEmail)
        this.mLblMemberSince = findViewById(R.id.lblMemberStartDate)

        this.mBtnEdit = findViewById(R.id.btnEdit)
        this.mBtnSave = findViewById(R.id.btnSave)
        this.mBtnSave!!.setOnClickListener {

            //call to save the users info
            saveEditUserInfo()

        }
        this.mBtnEdit!!.setOnClickListener {

            if(!mIsEdit){ //not currently editing

                //switch to editing
                mIsEdit = true
                btnEdit!!.text = "Cancel"

                //enable editing by showing hints
                showHints()

            }else{ //currently editing now

                //switch to not editing
                mIsEdit = false
                btnEdit!!.text = "Edit"
                setTextBoxs(mViewModel!!.getUserInfo())

            }

        }

        //set the text box's info
        this.setTextBoxs(info)

    }



    //Used to set the text boxs
    private fun setTextBoxs(info: UserInfo){

        mBtnSave!!.visibility = View.GONE

        //first name
        this.mTxtFirstName!!.isEnabled = false
        if(info.mFirstName != null){
            this.txtFirstName.setText(info.mFirstName)
        }

        //last name
        this.mTxtLastName!!.isEnabled = false
        if(info.mLastName != null){
            this.mTxtLastName!!.setText(info.mLastName)
        }

        //Email Address
        this.mTxtEmailAddress!!.isEnabled = false
        if(info.mEmail != null){
            this.mTxtEmailAddress!!.setText(info.mEmail)
        }

        //because the date time zone conventions require sdk check, only add if it fits the check
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val date = ZonedDateTime.parse(info.mCreatedDate)
            this.mLblMemberSince!!.text = ("Member Since: ${DateTimeFormatter.ofPattern("MM/dd/yyyy").format(date)} ")
            //DateTimeFormatter.ofPattern("E, MMM dd, hh:mm a").format(messageModel.messageTime)
        } else {
            //member since box
            this.mLblMemberSince!!.text = "Member Since: ${info.mCreatedDate}"

        }

    }

    //Used to show hints and hide the text making text box's editable
    private fun showHints(){

        mBtnSave!!.visibility = View.VISIBLE

        //first name
        this.mTxtFirstName!!.hint = this.mTxtFirstName!!.text
        this.mTxtFirstName!!.setText("")
        this.mTxtFirstName!!.isEnabled = true

        //last name
        this.mTxtLastName!!.hint = this.mTxtLastName!!.text
        this.mTxtLastName!!.setText("")
        this.mTxtLastName!!.isEnabled = true

        //Email Address
        this.mTxtEmailAddress!!.hint = this.mTxtEmailAddress!!.text
        this.mTxtEmailAddress!!.setText("")
        this.mTxtEmailAddress!!.isEnabled = true


    }

    //Used to save new user info
    private fun saveEditUserInfo(){

        //get a version of the user info to check against
        val currentUserData = this.mViewModel!!.getUserInfo()

        //get previous info
        val previousUserInfo = this.mViewModel!!.getUserInfo()
        var newInfo = previousUserInfo


        //TODO: perform validation logic to make sure valid values
        //Update the info values if changed
        if(this.mTxtFirstName!!.text.toString() != ""){
            newInfo.mFirstName = this.mTxtFirstName!!.text.toString()
        }
        if(this.mTxtLastName!!.text.toString() != ""){
            newInfo.mLastName = this.mTxtLastName!!.text.toString()
        }
        if(this.mTxtEmailAddress!!.text.toString() != ""){
            newInfo.mEmail = this.mTxtEmailAddress!!.text.toString()
        }

        //check for any changes
        if(
                newInfo.mFirstName != currentUserData.mFirstName ||
                newInfo.mLastName != currentUserData.mLastName ||
                newInfo.mEmail != currentUserData.mEmail
        ){//User info changed. Lets process and submit to server

            val thread = Thread(Runnable {
                try {

                    //Update the user info on the server and the app
                    val updatedValues = this.mViewModel!!.updateUser(newInfo)

                    //make sure the update value is not null
                    if (updatedValues != null) { //check if the user is activated and should be able to login

                        Handler(Looper.getMainLooper()).post { //Perform on main thread

                            //now that changes are saved, lets switch to not editing and update views with new values
                            mIsEdit = false
                            btnEdit!!.text = "Edit"
                            setTextBoxs(newInfo) //TODO: for now setting to newInfo but after final changes, make back to mViewModel!!.getUserInfo() so its the final changed data that the server for sure has

                            //display success
                            Toast.makeText(this, "Information Updated Successfully", Toast.LENGTH_LONG).show()

                        }


                    } else {
                        throw Exception()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()

                }
            })

            thread.start()


        }else{

            //No change
            Toast.makeText(this, "No Changes have been made!", Toast.LENGTH_LONG).show()

            //switch back to not editing
            mIsEdit = false
            btnEdit!!.text = "Edit"
            setTextBoxs(mViewModel!!.getUserInfo())


        }

    }


}