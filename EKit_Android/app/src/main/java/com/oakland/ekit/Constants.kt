package com.oakland.ekit


class Constants {


    companion object{

        private val test = ""



        //User data ref values
        enum class UserData{

            FirstName, LastName, DOB, UserId, IsSpecialUser, Email;


            //Used to get json key for the value
            fun key(): String{

                return when(this){

                    FirstName -> "first_name"
                    LastName -> "last_name"
                    DOB -> "dob"
                    UserId -> "user_id"
                    IsSpecialUser -> "is_special_user"
                    Email -> "email_address"

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



    }


}