package com.oakland.ekit.viewModels

import androidx.lifecycle.ViewModel
import com.oakland.ekit.data.LoginRepository
import com.oakland.ekit.data.model.LoggedInUser

class AdminHomePageViewModel internal constructor(var loginRepository: LoginRepository) : ViewModel() {



    //Used to perform logout from current account
    fun logOutUser(){

        //call to logout
        this.loginRepository.logout()

    }

    //Used to get the logged in user
    fun getLoggedInUser(): LoggedInUser {
        return loginRepository.user
    }

}