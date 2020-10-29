package com.oakland.ekit

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.oakland.ekit.ui.login.LoginViewModelFactory
import com.oakland.ekit.viewModels.AccountInformationViewModel
import kotlinx.android.synthetic.main.activity_user_information.*

class UserInformationActivity : AppCompatActivity() {

    private var mViewModel : AccountInformationViewModel? = null

    private var mContext: Context? = null

    private var mIsEdit = false

    private var mBtnEdit: Button? = null
    private var mBtnSave: Button? = null
    private var mTxtFirstName: EditText? = null
    private var mTxtLastName: EditText? = null


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

        this.mBtnEdit = findViewById(R.id.btnEdit)
        this.mBtnSave = findViewById(R.id.btnSave)
        this.mBtnSave!!.setOnClickListener {

            //call to save the users info
            saveEditUserInfo()

        }
        this.mBtnEdit!!.setOnClickListener {

            if(!mIsEdit){

                //Editing now
                mIsEdit = true
                btnEdit!!.text = "Cancel"

                //enable editing by showing hints
                showHints()

            }else{

                //not editing now
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


    }

    //Used to save new user info
    private fun saveEditUserInfo(){

        //TODO: add the saving of new user info

    }


}