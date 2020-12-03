package com.oakland.ekit

import com.google.gson.JsonArray
import com.oakland.ekit.Constants.Companion.UserData
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception


class Utilities{

    //Used to parse user info data from json object
    fun parseUserInfo(jo: JSONObject): UserInfo{

        var mFirstName: String? = null
        var mLastName: String? = null
        var mUserID: String? = null
        var mEmail: String? = null
        var mUserName: String? = null
        var mImageURL: String? = null
        var mActivated: Boolean? = null
        var mLangKey: String? = null
        var mCreatedBy: String? = null
        var mCreatedDate: String? = null
        var mLastModifiedBy: String? = null
        var mLastModifiedDate: String? = null
        var mAuthorities: JsonArray? = null
        var mIsSpecialUser: Boolean? = null


        //check each json key

        if (jo.has(UserData.UserId.key())){
            mUserID = jo.getString(UserData.UserId.key())
        }
        if (jo.has(UserData.UserName.key())){
            mUserName = jo.getString(UserData.UserName.key())
        }
        if (jo.has(UserData.FirstName.key())){
            mFirstName = jo.getString(UserData.FirstName.key())
        }
        if (jo.has(UserData.LastName.key())){
            mLastName = jo.getString(UserData.LastName.key())
        }
        if (jo.has(UserData.Email.key())){
            mEmail = jo.getString(UserData.Email.key())
        }
        if (jo.has(UserData.ImageURL.key())){
            mImageURL = jo.getString(UserData.ImageURL.key())
        }
        if (jo.has(UserData.Activated.key())){
            mActivated = jo.getBoolean(UserData.Activated.key())
        }
        if (jo.has(UserData.LangKey.key())){
            mLangKey = jo.getString(UserData.LangKey.key())
        }
        if (jo.has(UserData.CreatedBy.key())){
            mCreatedBy = jo.getString(UserData.CreatedBy.key())
        }
        if (jo.has(UserData.CreatedDate.key())){
            mCreatedDate = jo.getString(UserData.CreatedDate.key())
        }
        if (jo.has(UserData.LastModifiedBy.key())){
            mLastModifiedBy = jo.getString(UserData.LastModifiedBy.key())
        }
        if (jo.has(UserData.LastModifiedDate.key())){
            mLastModifiedDate = jo.getString(UserData.LastModifiedDate.key())
        }
        if (jo.has(UserData.Authorities.key())){

            var isSpecial = false //default to false

            //get the authorises array json
            val arrJson: JSONArray = jo.getJSONArray(UserData.Authorities.key())

            //breakdown into json array to save the authorities array
            val authoritiesJsonArray = JsonArray(arrJson.length())
            for (i in 0 until arrJson.length()) authoritiesJsonArray.add(arrJson.get(i).toString())

            mAuthorities = authoritiesJsonArray

            //break down the authorities array into an array of all the authority stings
            val arr = arrayOfNulls<String>(arrJson.length())
            for (i in 0 until arrJson.length()) arr[i] = arrJson.getString(i)

            //determine if special user
            for (authority in arr) {
                if (authority == "ROLE_ADMIN") { //this is a special user
                    isSpecial = true
                }
                //"authorities":["ROLE_USER","ROLE_ADMIN"]}
            }

            //set the is special value
            mIsSpecialUser = isSpecial
        }

        //return the final result
        return UserInfo(mFirstName, mLastName, mUserID, mIsSpecialUser, mEmail, mImageURL, mActivated, mLangKey, mCreatedBy, mCreatedDate, mLastModifiedBy, mLastModifiedDate, mUserName, mAuthorities)


    }

    companion object{

        //Used to parse user info data from json object
        fun parseUserInfo(jo: JSONObject): UserInfo{

            var mFirstName: String? = null
            var mLastName: String? = null
            var mUserID: String? = null
            var mEmail: String? = null
            var mUserName: String? = null
            var mImageURL: String? = null
            var mActivated: Boolean? = null
            var mLangKey: String? = null
            var mCreatedBy: String? = null
            var mCreatedDate: String? = null
            var mLastModifiedBy: String? = null
            var mLastModifiedDate: String? = null
            var mAuthorities: JsonArray? = null
            var mIsSpecialUser: Boolean? = null


            //check each json key

            if (jo.has(UserData.UserId.key())){
                mUserID = jo.getString(UserData.UserId.key())
            }
            if (jo.has(UserData.UserName.key())){
                mUserName = jo.getString(UserData.UserName.key())
            }
            if (jo.has(UserData.FirstName.key())){
                mFirstName = jo.getString(UserData.FirstName.key())
            }
            if (jo.has(UserData.LastName.key())){
                mLastName = jo.getString(UserData.LastName.key())
            }
            if (jo.has(UserData.Email.key())){
                mEmail = jo.getString(UserData.Email.key())
            }
            if (jo.has(UserData.ImageURL.key())){
                mImageURL = jo.getString(UserData.ImageURL.key())
            }
            if (jo.has(UserData.Activated.key())){
                mActivated = jo.getBoolean(UserData.Activated.key())
            }
            if (jo.has(UserData.LangKey.key())){
                mLangKey = jo.getString(UserData.LangKey.key())
            }
            if (jo.has(UserData.CreatedBy.key())){
                mCreatedBy = jo.getString(UserData.CreatedBy.key())
            }
            if (jo.has(UserData.CreatedDate.key())){
                mCreatedDate = jo.getString(UserData.CreatedDate.key())
            }
            if (jo.has(UserData.LastModifiedBy.key())){
                mLastModifiedBy = jo.getString(UserData.LastModifiedBy.key())
            }
            if (jo.has(UserData.LastModifiedDate.key())){
                mLastModifiedDate = jo.getString(UserData.LastModifiedDate.key())
            }
            if (jo.has(UserData.Authorities.key())){

                var isSpecial = false //default to false

                //get the authorises array json
                val arrJson: JSONArray = jo.getJSONArray(UserData.Authorities.key())

                //breakdown into json array to save the authorities array
                val authoritiesJsonArray = JsonArray(arrJson.length())
                for (i in 0 until arrJson.length()) authoritiesJsonArray.add(arrJson.get(i).toString())

                mAuthorities = authoritiesJsonArray

                //break down the authorities array into an array of all the authority stings
                val arr = arrayOfNulls<String>(arrJson.length())
                for (i in 0 until arrJson.length()) arr[i] = arrJson.getString(i)

                //determine if special user
                for (authority in arr) {
                    if (authority == "ROLE_ADMIN") { //this is a special user
                        isSpecial = true
                    }
                    //"authorities":["ROLE_USER","ROLE_ADMIN"]}
                }

                //set the is special value
                mIsSpecialUser = isSpecial
            }

            //return the final result
            return UserInfo(mFirstName, mLastName, mUserID, mIsSpecialUser, mEmail, mImageURL, mActivated, mLangKey, mCreatedBy, mCreatedDate, mLastModifiedBy, mLastModifiedDate, mUserName, mAuthorities)


        }

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


        //Used to parse as json survey question into a Survey Question object
        fun parseQuestionJsonAsSurveyQuestion(questionAnswerObject: JSONObject): Constants.Companion.QuestionList.Question{


            try { //TODO: json keys to constants and perform checks before setting a variable to make sure its in the json object

                //get all the possible answers
                val answers = questionAnswerObject.getJSONArray("answerTextList")
                val questionObject = questionAnswerObject.getJSONObject("surveyQuestion")

                val questionId = questionObject.getInt("id")
                val questionText = questionObject.getString("questionText")
                val isActive = questionObject.getBoolean("isActive")
                val questionType = questionObject.getString("questionType")

                //convert the answers list json to a string array to pass
                val answersList = Array(answers.length()) {
                    answers.getString(it)
                }

                //TODO: handle question types ("MULTI", ect...)

                //return the generated question object
                return Constants.Companion.QuestionList.Question(questionId, false, questionText, answersList)


            }catch (e: Exception){

                throw e

            }




        }



    }



}