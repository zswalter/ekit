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




    }


}