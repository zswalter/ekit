package com.oakland.ekit.viewModels

import androidx.lifecycle.ViewModel
import com.oakland.ekit.Constants
import com.oakland.ekit.data.LoginRepository
import com.oakland.ekit.data.model.LoggedInUser
import com.oakland.ekit.Constants.Companion.UserData
import com.oakland.ekit.ServerManager
import com.oakland.ekit.UserInfo
import com.oakland.ekit.Utilities
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception
import kotlin.jvm.Throws

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

    @Throws(Exception::class)
    fun updateUser(newUserInfo: UserInfo): UserInfo {


        try {



            //call to update the user info with the apps backend and the server backend
            val updatedInfoValues = ServerManager.updateUserInfo(newUserInfo)

            //pass to the repository to handle the changed info now
            //loginRepository.
            //TODO: pass to the repo to update values throughout the app

            return updatedInfoValues

        }catch (e: Exception){


            throw e

        }


    }





}