package com.oakland.ekit

import android.os.Build
import com.google.gson.*
import com.oakland.ekit.Constants.Companion.UserData
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import com.oakland.ekit.Constants.Companion.SurveyQuestionAnswer
import java.time.ZonedDateTime
import kotlin.jvm.Throws


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
        fun parseTicketUserInfo(jo: JSONObject): TicketUserInfo{

            var mId: Int? = null
            var mCreatedBy: UserInfo? = null
            var mClosed: String? = null
            var mCreated: String? = null
            //var mClosedBy: UserInfo? = null

            //check each json key //TODO: add "product"
            if (jo.has("id")){
                mId = jo.getInt("id")
            }
            if (jo.has("closed")){
                mClosed = jo.getString("closed")
            }
            if (jo.has("created")){
                mCreated = jo.getString("created")
            }
//            if (jo.has("closedBy")){
//
//                //TODO: temp for now since null
//                mClosedBy = parseUserInfo(JSONObject(""))
////                if(parseUserInfo(jo.getJSONObject("closedBy")) != null){
////                    mClosedBy = parseUserInfo(jo.getJSONObject("closedBy"))
////                }else{
////                    mClosedBy = parseUserInfo(JSONObject(""))
////                }
//
//            }
            if (jo.has("createdBy")){
                mCreatedBy = parseUserInfo(jo.getJSONObject("createdBy"))
            }

            //return the final result
            return TicketUserInfo(mId, mClosed, mCreated, mCreatedBy) //TODO: add closedBy

        }


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
        fun parseQuestionJsonAsSurveyQuestion(questionAnswerObject: JSONObject?, questionObject: JSONObject, questionType: Constants.Companion.QuestionType): Constants.Companion.QuestionList.Question{


            try { //TODO: json keys to constants and perform checks before setting a variable to make sure its in the json object


                val questionId = questionObject.getInt("id")
                val questionText = questionObject.getString("questionText")
                val isActive = questionObject.getBoolean("isActive")
                //val questionType = questionObject.getString("questionType")

                var answersList: Array<String>? = null

                //switch on the question type to determine if answers need to be parsed out
                when(questionType){

                    Constants.Companion.QuestionType.MULTI -> { //TODO: add any more that needs answer parsed out
                        //Null check
                        if(questionAnswerObject != null){

                            //get all the possible answers
                            val answers = questionAnswerObject!!.getJSONArray("answerTextList")

                            //convert the answers list json to a string array to pass
                            answersList = Array(answers.length()) {
                                answers.getString(it)
                            }

                        }



                    }

                }

                //return the generated question object
                return Constants.Companion.QuestionList.Question(questionId, questionText, answersList, questionType)


            }catch (e: Exception){

                throw e

            }




        }

        //Used to parse the survey submission data into the request json for the api call
        @Throws(Exception::class)
        fun generateSurveySubmissionRequestBody(userID: Int, submissionAnswers: Array<SurveyQuestionAnswer>): JsonObject{

            try {

                var jsonBody = JsonObject()
                var resultMapObject = JsonObject()

                //add the user id
                jsonBody.addProperty("userId", userID)

                //go through and add each of the selected answers
                for(questionAnswer in submissionAnswers){

                    //add the question id and the selected answer text to the map object
                    resultMapObject.addProperty("${questionAnswer.questionID}", "${questionAnswer.answerString}")

                }

                //add the result map to the body
                jsonBody.add("surveyResultsMap", resultMapObject)

                //return the created json
                return jsonBody


            }catch (e: JsonIOException){

                throw e

            }

        }


        //Used to make a ticket request body json
        @Throws(Exception::class)
        fun generateTicketCommentRequestBody(ticketBody: JSONObject, content: String, userObject: JSONObject ): JsonObject{

            try {

                var jsonUser = JsonParser().parse(userObject.toString()).asJsonObject
                var jsonTicketBody = JsonParser().parse(ticketBody.toString()).asJsonObject

                var jsonBody = JsonObject()

                var userJson = JsonObject()
                userJson.add("id", jsonUser.get("id"))

                //because the date time zone conventions require sdk check, only add if it fits the check
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    jsonBody.addProperty("created", ZonedDateTime.now().toString())
                }

                jsonBody.add("user", userJson)
                jsonBody.add("ticket", jsonTicketBody)
                jsonBody.addProperty("content", content)
                //jsonBody.addProperty()

                //return the created json
                return jsonBody


            }catch (e: JsonIOException){

                throw e

            }

        }


    }



}