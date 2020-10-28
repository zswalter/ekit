package com.oakland.ekit.viewModels

import androidx.lifecycle.ViewModel
import com.oakland.ekit.data.LoginRepository
import com.oakland.ekit.data.model.LoggedInUser

class AccountInformationViewModel internal constructor(var loginRepository: LoginRepository) : ViewModel() {

    //Used to get the logged in user
    fun getLoggedInUser(): LoggedInUser{
        return loginRepository.user
    }



}