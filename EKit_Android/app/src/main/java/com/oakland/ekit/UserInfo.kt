package com.oakland.ekit

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.oakland.ekit.Constants.Companion.UserData
import org.json.JSONArray


//User info object
class UserInfo(fName: String?, lName: String?, userID: String?, isSpecialUser: Boolean?, email: String?, imageURL: String?, active: Boolean?, langKey: String?, createdBy: String?, createdDate: String?, lastModifiedBy: String?, lastedModifiedDate: String?, userName: String?, authorities: JsonArray?){


    val mUserID = userID
    val mUserName = userName
    var mFirstName = fName //currently can edit
    var mLastName = lName //currently can edit
    var mEmail = email //currently can edit
    val mImageURL = imageURL
    val mActivated = active
    val mLangKey = langKey
    val mCreatedBy = createdBy
    val mCreatedDate = createdDate
    val mLastModifiedBy = lastModifiedBy
    val mLastModifiedDate = lastedModifiedDate
    val mAuthorities = authorities

    val mIsSpecialUser = isSpecialUser



    fun getJsonObject(): JsonObject{

        var buildJson = JsonObject()

        //add each value with its assigned keys
        buildJson.addProperty(UserData.UserId.key(), mUserID)
        buildJson.addProperty(UserData.UserName.key(), mUserName)
        buildJson.addProperty(UserData.FirstName.key(), mFirstName)
        buildJson.addProperty(UserData.LastName.key(), mLastName)
        buildJson.addProperty(UserData.Email.key(), mEmail)
        buildJson.addProperty(UserData.ImageURL.key(), mImageURL)
        buildJson.addProperty(UserData.Activated.key(), mActivated)
        buildJson.addProperty(UserData.LangKey.key(), mLangKey)
        buildJson.addProperty(UserData.CreatedBy.key(), mCreatedBy)
        buildJson.addProperty(UserData.CreatedDate.key(), mCreatedDate)
        buildJson.addProperty(UserData.LastModifiedBy.key(), mLastModifiedBy)
        buildJson.addProperty(UserData.LastModifiedDate.key(), mLastModifiedDate)
        buildJson.add(UserData.Authorities.key(), mAuthorities)

        return buildJson


    }

}