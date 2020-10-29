package com.oakland.ekit.viewModels

import androidx.lifecycle.ViewModel
import com.oakland.ekit.Constants
import com.oakland.ekit.data.LoginRepository
import com.oakland.ekit.data.model.LoggedInUser
import com.oakland.ekit.Constants.Companion.UserData
import com.oakland.ekit.UserInfo
import com.oakland.ekit.Utilities

class AccountInformationViewModel internal constructor(var loginRepository: LoginRepository) : ViewModel() {

    //Used to get the logged in user
    fun getLoggedInUser(): LoggedInUser{
        return loginRepository.user
    }


    //Used to get the user info of the current logged in user
    fun getUserInfo(): UserInfo{

        val userData = loginRepository.user.serverData

        return Utilities().parseUserInfo(userData)

    }


}