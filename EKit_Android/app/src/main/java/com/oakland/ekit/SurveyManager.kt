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

    //Used to submit a answer to the question
    fun submitAnswer(answerIndex: Int){

        this.answers[this.currentQuestionIndex].questionAnswerIndex = answerIndex

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

    //Used to get the users selcted survey answers to submit
    fun getAnswersForSubmittion() {




        //TODO: finish




    }





}