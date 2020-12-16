package com.oakland.ekit

import com.google.gson.JsonObject
import org.json.JSONObject
import java.io.Serializable
import java.time.ZonedDateTime


class Constants {


    companion object{

        //The api value http request url
        val API_URL = "http://192.168.4.44:8080/api/" //TODO: this is were you update for server IP!!!!!!!!!

        //User data ref values
        enum class UserData{

            FirstName, LastName, UserId, Email, ImageURL, Activated, LangKey, CreatedBy, CreatedDate, LastModifiedBy, LastModifiedDate, Authorities, UserName;


            //Used to get json key for the value
            fun key(): String{

                return when(this){

                    UserId -> "id"
                    UserName -> "login"
                    FirstName -> "firstName"
                    LastName -> "lastName"
                    Email -> "email"
                    ImageURL -> "imageUrl"
                    Activated -> "activated"
                    LangKey -> "langKey"
                    CreatedBy -> "createdBy"
                    CreatedDate -> "createdDate"
                    LastModifiedBy -> "lastModifiedBy"
                    LastModifiedDate -> "lastModifiedDate"
                    Authorities -> "authorities"



                }

            }

        }


        //question type enum
        enum class QuestionType{

            MULTI, INPUT;

            //Used to get json key for the value
            fun key(): String{

                return when(this){

                    MULTI -> "MULTI"
                    INPUT -> "INPUT"

                }

            }

        }


        //survey question list class
        class QuestionList{

            val questions: ArrayList<Question> = ArrayList()

            //question list member (question)
            class Question{


                val questionID: Int
                val answerStringArray: Array<String>?
                val questionString: String
                val questionType: QuestionType

                constructor(questionID: Int, questionString: String, answerStringArray: Array<String>?, questionType: QuestionType){

                    this.questionID = questionID
                    this.questionString = questionString
                    this.answerStringArray = answerStringArray
                    this.questionType = questionType

                }

            }

        }


        //survey question answer
        class SurveyQuestionAnswer{

            val questionID: Int
            var questionAnswerIndex: Int? = null
            var answerString: String? = null

            constructor(questionID: Int){
                this.questionID = questionID
            }

        }


        //Ticket Comment item
        class TicketComment: Serializable{

            val id: Int
            val content: String
            val commentCreatedDate: String
            val ticketValues: TicketUserInfo
            val user: TicketUserInfo

            constructor(id: Int, content: String, commentCreatedDate: String, ticketValues: TicketUserInfo, user: TicketUserInfo){

                this.id = id
                this.content = content
                this.commentCreatedDate = commentCreatedDate
                this.ticketValues = ticketValues
                this.user = user


            }



        }


        //Ticket item
        class Ticket: Serializable{

            val id: Int
            val createdDate: String
            val comments: ArrayList<TicketComment>
            val firstName: String
            val lastName: String
            val ticketProduct: Product?
            val ticketDataString: String

            constructor(id: Int, createdDate: String, comments: ArrayList<TicketComment>, firstName: String, lastName: String, product: Product?, ticketDataString: String){

                this.id = id
                this.createdDate = createdDate
                this.comments = comments
                this.firstName = firstName
                this.lastName = lastName
                this.ticketProduct = product
                this.ticketDataString = ticketDataString


            }



        }


        //Product object
        class Product: Serializable{

            var id: Int
            var productDescription: String
            var productImage: String
            var productName: String

            constructor(productObject: JSONObject){

                this.id = productObject.getInt("id")
                this.productDescription = productObject.getString("productDescription")
                this.productImage = productObject.getString("productImage")
                this.productName = productObject.getString("productName")



            }


        }






        class MessageModel // Constructor
        (var message: String, var messageType: Int, var messageTime: ZonedDateTime)


    }


}