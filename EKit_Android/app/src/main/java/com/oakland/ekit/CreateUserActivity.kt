package com.oakland.ekit

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_create_user.*
import kotlinx.android.synthetic.main.activity_create_user.toolbar
import kotlinx.android.synthetic.main.activity_user_information.*


class CreateUserActivity : AppCompatActivity() {

    var textFirstName: TextInputEditText? = null
    var textLastName: TextInputEditText? = null
    var textEmail: TextInputEditText? = null
    var textUsername: TextInputEditText? = null
    var textPassword: EditText? = null
    var btnRegister: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        setSupportActionBar(toolbar)

        //Call to init the ui
        this.initUI()

    }


    //Used to init the UI
    private fun initUI(){

        //listener for textboxs
        val textViewEditorListener = OnEditorActionListener { v, actionId, event ->
            //When user clicks done button on the keyboard
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //call to process the new user
                processNewUser()
            }
            false
        }


        //init the ui
        this.textFirstName = findViewById(R.id.textFirstName)
        this.textLastName = findViewById(R.id.textLastName)
        this.textEmail = findViewById(R.id.textEmail)
        this.textUsername = findViewById(R.id.textUsername)
        this.textPassword = findViewById(R.id.textPassword)
        this.textFirstName!!.setOnEditorActionListener(textViewEditorListener)
        this.textLastName!!.setOnEditorActionListener(textViewEditorListener)
        this.textEmail!!.setOnEditorActionListener(textViewEditorListener)
        this.textPassword!!.setOnEditorActionListener(textViewEditorListener)
        this.textUsername!!.setOnEditorActionListener(textViewEditorListener)


        this.btnRegister = findViewById(R.id.btnRegister)

        this.btnRegister!!.setOnClickListener {

            //call to process the new user info
            this.processNewUser()

        }


    }


    //Used to process any new user
    private fun processNewUser(){

        //Call to validate text
        if(validateText()){


            val thread = Thread(Runnable {
                try {


                    //after validation, call to create the new user
                    if (ServerManager.createNewUser(this.textFirstName!!.text.toString()!!, this.textLastName!!.text.toString()!!, this.textUsername!!.text.toString()!!, this.textPassword!!.text.toString()!!, this.textEmail!!.text.toString()!!)){
                        //success!
                        Handler(Looper.getMainLooper()).post { //Perform on main thread

                            //Display to user and return to login screen
                            AlertDialog.Builder(this)
                                    .setTitle("Account Setup Complete!")
                                    .setMessage("Your account has been created and you can now login to E-Kit!")
                                    .setPositiveButton("Continue") { dialog, which ->

                                        //go back to login
                                        super.onBackPressed()

                                    }
                                    .setCancelable(false)
                                    .show()


                        }

                    }

                } catch (e: Exception) {


                    //success! //TODO: if we fix this activate issue, we should get a actual return so we can remove this and display the error message instead.
                    Handler(Looper.getMainLooper()).post { //Perform on main thread

                        //Display to user and return to login screen
                        AlertDialog.Builder(this)
                                .setTitle("Account Setup Complete!")
                                .setMessage("Your account has been created and you can now login to E-Kit!")
                                .setPositiveButton("Continue") { dialog, which ->

                                    //go back to login
                                    super.onBackPressed()

                                }
                                .setCancelable(false)
                                .show()


                    }

                    //TODO: if we have time/the ability to, lets make it so that the user can get error. This will require us to set the active property of user

//                    //error!!!
//                    Handler(Looper.getMainLooper()).post { //Perform on main thread
//
//                        //Display to user and dismiss
//                        AlertDialog.Builder(this)
//                                .setTitle("Error Creating Account!")
//                                .setMessage("A error came up creating your account. Please try again or contain customer support.\n$e")
//                                .setPositiveButton("Okay") { dialog, which ->
//
//                                    //Dismiss
//                                    dialog.dismiss()
//
//                                }
//                                .setCancelable(false)
//                                .show()
//
//
//                    }

                }
            })

            thread.start()

        }


    }


    //Used to validate all textBox
    private fun validateText(): Boolean{

        var isValid = true

        //check if empty
        if(isBlank(this.textFirstName!!)){
            //error
            isValid = false

        }
        if(isBlank(this.textLastName!!)){
            //error
            isValid = false

        }
        if(isBlank(this.textEmail!!)){
            //error
            isValid = false

        }
        if(isBlank(this.textUsername!!)){
            //error
            isValid = false

        }
        if(isBlank(this.textPassword!!)){
            //error
            isValid = false

        }

        //make sure more then 8 char
        if (this.textPassword!!.text.toString()!!.count() < 4){
            //error
            this.textPassword!!.error = "Password must be 4 or more characters"
            isValid = false
        }

        //make sure email is a valid email
        if(!this.textEmail!!.text.toString()!!.contains("@") || !this.textEmail!!.text.toString()!!.contains(".") || this.textEmail!!.text.toString().isEmpty()){
            //error
            this.textEmail!!.error = "This is not a valid email"
            isValid = false
        }

        //return if we can validate all the inputs
        return isValid



    }


    //Used to add a error if there is a blank for TextInputEditText
    private fun isBlank(textBox: TextInputEditText): Boolean{

        //check if empty
        if (textBox.text.toString()!! == ""){

            textBox.error = "Cant be left blank!"
            return true

        }

        return false

    }

    //Used to add a error if there is a blank for EditText
    private fun isBlank(textBox: EditText): Boolean{

        //check if empty
        if (textBox.text.toString()!! == ""){

            textBox.error = "Cant be left blank!"
            return true

        }

        return false


    }









}
