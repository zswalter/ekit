package com.oakland.ekit

import java.io.Serializable

//TODO: rename to some kind of ticket value or something because "user" and "ticket" use this formate
//Ticket User info object
class TicketUserInfo(id: Int?, closed: String?, created: String?, createdBy: UserInfo?): Serializable { //TODO: need to add product json item and closedBy since nulls


    val mId = id
    val mCreatedBy = createdBy
    val mClosed = closed
    val mCreated = created
    //val mClosedBy = closedBy

}
