package com.oakland.ekit

import com.oakland.ekit.Constants.Companion.UserData
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import org.json.JSONObject


class Utilities{


    //Used to parse user info data from json object
    fun parseUserInfo(jo: JSONObject): UserInfo{

        var mFirstName: String? = null
        var mLastName: String? = null
        var mDateOfBirth: String? = null
        var mUserID: String? = null
        var mIsSpecialUser: Boolean? = null
        var mEmail: String? = null

        //check each json key

        if (jo.has(UserData.FirstName.key())){
            mFirstName = jo.getString(UserData.FirstName.key())
        }
        if (jo.has(UserData.LastName.key())){
            mLastName = jo.getString(UserData.LastName.key())
        }
        if (jo.has(UserData.DOB.key())){
            mDateOfBirth = jo.getString(UserData.DOB.key())
        }
        if (jo.has(UserData.UserId.key())){
            mUserID = jo.getString(UserData.UserId.key())
        }
        if (jo.has(UserData.IsSpecialUser.key())){
            mIsSpecialUser = jo.getBoolean(UserData.IsSpecialUser.key())
        }
        if (jo.has(UserData.Email.key())){
            mEmail = jo.getString(UserData.Email.key())
        }

        //return the final result
        return UserInfo(mFirstName, mLastName, mDateOfBirth, mUserID, mIsSpecialUser, mEmail)

    }

    companion object{

        //Used to decrypt using PBE
        fun decrypt(seed: String, encryptedValue: String): String{

            val encryptor = StandardPBEStringEncryptor()
            encryptor.setPassword(seed)

            //return decrypted value
            return encryptor.decrypt(encryptedValue)

        }

        //Used to encrypt using PBE
        fun encrypt(seed: String, stringValue: String): String {

            val encryptor = StandardPBEStringEncryptor()
            encryptor.setPassword(seed)

            //return encrypted value
            return encryptor.encrypt(stringValue)
        }



    }



}