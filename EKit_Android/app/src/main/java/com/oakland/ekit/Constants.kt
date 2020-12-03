package com.oakland.ekit

import java.io.Serializable


class Constants {


    companion object{

        //The api value http request url
        val API_URL = "http://192.168.4.44:8080/api/"

        //User data ref values
        enum class UserData{ //TODO: update these options

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


        class QuestionList{

            val questions: ArrayList<Question> = ArrayList()

            class Question{


                val questionID: Int
                val multipleChoice: Boolean
                val answerStringArray: Array<String>
                val questionString: String

                constructor(questionID: Int, multipleChoise: Boolean, questionString: String, answerStringArray: Array<String>){

                    this.questionID = questionID
                    this.multipleChoice = multipleChoise
                    this.questionString = questionString
                    this.answerStringArray = answerStringArray

                }

            }

        }


        class SurveyQuestionAnswer{

            val questionID: Int
            var questionAnswerIndex: Int? = null

            constructor(questionID: Int){
                this.questionID = questionID
            }

        }


        //survey question lists
        class SurveyQuestionLists{


            //test question list
            fun generateQuestionList1(): QuestionList{

                val list = QuestionList()

                list.questions!!.add(QuestionList.Question(0, false, "This is test question 1", arrayOf("question1", "question2", "question3")))
                list.questions!!.add(QuestionList.Question(1, false, "This is test question 2", arrayOf("question1", "question2", "question3", "question4")))
                list.questions!!.add(QuestionList.Question(2, false, "This is test question 3", arrayOf("question1", "question2", "question3")))
                list.questions!!.add(QuestionList.Question(3, false, "This is test question 4", arrayOf("question1", "question2", "question3", "question4", "question5")))


                return list

            }



        }


        //Ticket item
        class Ticket: Serializable{


            val id: Int

            constructor(id: Int){

                this.id = id


            }



        }



    }


}