package com.oakland.ekit

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.oakland.ekit.ui.login.LoginViewModelFactory
import com.oakland.ekit.viewModels.AccountInformationViewModel
import com.oakland.ekit.viewModels.UserHomePageViewModel

class UserInformationActivity : AppCompatActivity() {

    private var mViewModel : AccountInformationViewModel? = null

    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set the context
        this.mContext  = this

        //set the view model
        this.mViewModel = ViewModelProviders.of(this, LoginViewModelFactory()).get<AccountInformationViewModel>(AccountInformationViewModel::class.java)

        setContentView(R.layout.activity_user_information)
        setSupportActionBar(findViewById(R.id.toolbar))


    }




}