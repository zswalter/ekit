package com.oakland.ekit.viewModels

import androidx.lifecycle.ViewModel
import com.oakland.ekit.Constants
import com.oakland.ekit.data.LoginRepository
import com.oakland.ekit.data.model.LoggedInUser
import com.oakland.ekit.Constants.Companion.Ticket
import com.oakland.ekit.ServerManager
import java.lang.Exception
import kotlin.jvm.Throws

class TicketItemViewModel internal constructor(var loginRepository: LoginRepository) : ViewModel() {

    //var mOenTickets: ArrayList<Ticket>? = null


    //Used to get the logged in user
    fun getLoggedInUser(): LoggedInUser {
        return loginRepository.user
    }

//    //Used to request the open tickets from the server
//    @Throws(Exception::class)
//    fun getOpenTickets(): ArrayList<Ticket> {
//
//        try {
//
//            //if we have already recived it from the server once, we don't need to call it again so lets return the previous values
//            if (this.mOenTickets != null) {
//                return this.mOenTickets!!
//            }
//
//            //since we don't have the data already, lets make the call to get it and then load the data in
//
//            //request the open ticket items from the server
//            val openTickets = ServerManager.getOpenTickets()
//
//            //save the data for later potential use
//            this.mOenTickets = openTickets
//
//            //return the open tickets
//            return openTickets
//
//        } catch (e: Exception) {
//            throw e
//        }
//
//    }


}

