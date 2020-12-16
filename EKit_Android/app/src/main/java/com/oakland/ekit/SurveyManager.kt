package com.oakland.ekit

import com.oakland.ekit.Constants.Companion.QuestionList
import com.oakland.ekit.Constants.Companion.SurveyQuestionAnswer

class SurveyManager(questionList: QuestionList) {

    private val questions = questionList
    private var currentQuestionIndex = 0
    private val answers = Array(questionList.questions!!.size){ i->SurveyQuestionAnswer(questions.questions[i].questionID)}

    //Used to get to the first question and start the survey
    fun startSurvey(): QuestionList.Question{

        this.currentQuestionIndex = 0

        return questions.questions!![currentQuestionIndex]

    }

    //Used to store a users selected answer to the question
    fun submitAnswer(answerIndex: Int?, inputTextAnswer: String?){

        if(questions.questions!![currentQuestionIndex].questionType != Constants.Companion.QuestionType.INPUT){ //these are values that can have answer selctions

            if(answerIndex != null){
                //save the users selected
                this.answers[this.currentQuestionIndex].questionAnswerIndex = answerIndex
                this.answers[this.currentQuestionIndex].answerString = this.questions.questions!![currentQuestionIndex].answerStringArray!![answerIndex]

            }

        }else{ //we need to just take in the string and not the index of selected value

            if(inputTextAnswer != null){

                this.answers[this.currentQuestionIndex].questionAnswerIndex = -1 //indicate that this does not exist
                this.answers[this.currentQuestionIndex].answerString = inputTextAnswer

            }

        }

    }

    //used to check if there is a next question
    fun isNext(): Boolean{

        //check if there is a next question or not
        return currentQuestionIndex + 1 != questions.questions!!.count()

    }

    //used to check if there is a previous question
    fun isBack(): Boolean{

        //check if there is a previous question or not
        return currentQuestionIndex != 0

    }

    //Used to move to the next question
    fun moveNext(): QuestionList.Question{

        this.currentQuestionIndex ++

        return questions.questions!![currentQuestionIndex]

    }

    //Used to move to the previous question
    fun moveBack(): QuestionList.Question{

        this.currentQuestionIndex --

        return questions.questions!![currentQuestionIndex]

    }

    //Used to get a questions answer if previously selected answer
    fun getQuestionAnswer(): SurveyQuestionAnswer{

        return this.answers[this.currentQuestionIndex]

    }

    //Used to get the users selected survey answers to submit
    fun getAnswersForSubmission(): Array<SurveyQuestionAnswer> {

        //return all the users selected answers for the survey
        return this.answers

    }

    //Used to get the current question that we are on
    fun getCurrentQuestion(): QuestionList.Question{

        return questions.questions!![currentQuestionIndex]
    }





}