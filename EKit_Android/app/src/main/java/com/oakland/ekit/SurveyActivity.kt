package com.oakland.ekit

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.oakland.ekit.ui.login.LoginViewModelFactory
import com.oakland.ekit.viewModels.SurveyViewModel

class SurveyActivity : AppCompatActivity() {

    private var mViewModel: SurveyViewModel? = null

    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set the context
        this.mContext  = this

        //set the view model
        this.mViewModel = ViewModelProviders.of(this, LoginViewModelFactory()).get<SurveyViewModel>(SurveyViewModel::class.java)

        setContentView(R.layout.activity_survey)
        setSupportActionBar(findViewById(R.id.toolbar))


    }



    override fun onBackPressed() {

        //Build a warning popup box
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle(R.string.title_are_you_sure).setMessage(R.string.message_back_pressed_survey)
                .setNeutralButton("Cancel", null)
                .setNegativeButton("Continue") { _: DialogInterface?, i: Int ->

                    super.onBackPressed()

                }.create().show()
    }



}