package com.oakland.ekit

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.get
import androidx.lifecycle.ViewModelProviders
import com.oakland.ekit.ui.login.LoginViewModelFactory
import com.oakland.ekit.viewModels.SurveyViewModel


class SurveyActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private var mViewModel: SurveyViewModel? = null

    private var mContext: Context? = null

    private var rgAnswers: RadioGroup? = null
    private var btnNext: Button? = null
    private var btnBack: Button? = null
    private var txtQuestionText: TextView? = null


    private var mSurveyManager: SurveyManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set the context
        this.mContext  = this

        //set the view model
        this.mViewModel = ViewModelProviders.of(this, LoginViewModelFactory()).get<SurveyViewModel>(SurveyViewModel::class.java)

        setContentView(R.layout.activity_survey)
        setSupportActionBar(findViewById(R.id.toolbar))

        //init the survey manager
        this.mSurveyManager = SurveyManager(Constants.Companion.SurveyQuestionLists().generateQuestionList1())

        //call to init the ui
        initUi()



    }

    private fun initUi(){

        this.rgAnswers = findViewById(R.id.rgAnswers)
        this.rgAnswers!!.setOnCheckedChangeListener(this)
        this.btnNext = findViewById(R.id.btnNext)
        this.btnNext!!.setOnClickListener(this)
        this.btnBack = findViewById(R.id.btnBack)
        this.btnBack!!.setOnClickListener(this)
        this.txtQuestionText = findViewById(R.id.txtSurveyQuestion)

        //get the first question from the survey manager
        val firstQuestion = mSurveyManager!!.startSurvey()

        //call to generate the first questions ui
        generateQuestionsUI(firstQuestion)

    }

    override fun onBackPressed() {

        //Build a warning popup box
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle(R.string.title_are_you_sure).setMessage(R.string.message_back_pressed_survey)
                .setNeutralButton("Cancel", null)
                .setNegativeButton("Continue") { _: DialogInterface?, i: Int ->

                    super.onBackPressed()

                }.create().show()
    }




    //Used to generate the ui of the question
    private fun generateQuestionsUI(question: Constants.Companion.QuestionList.Question) {

        this.txtQuestionText!!.text = question.questionString

        var button: RadioButton

        //clear all radio buttons first
        this.rgAnswers!!.removeAllViews()

        //for each answer in the question, lets make a radio button
        for (i in 0 until question.answerStringArray.count()) {

            //create the radio button
            button = RadioButton(this)
            button.text = question.answerStringArray[i]

            //add the button to the radio group
            this.rgAnswers!!.addView(button)

            //if the question has previously been answered
            if (this.mSurveyManager!!.getQuestionAnswer().questionAnswerIndex != null) {

                if(this.mSurveyManager!!.getQuestionAnswer().questionAnswerIndex!! == i){
                    this.rgAnswers!!.check(button.id)
                }

            }

        }

        //see if we have a next question
        if(this.mSurveyManager!!.isNext()){
            this.btnNext!!.visibility = View.VISIBLE
        }else{
            this.btnNext!!.visibility = View.GONE
            //TODO: show submit survey button
        }

        //see if we have a previous question
        if(this.mSurveyManager!!.isBack()){
            this.btnBack!!.visibility = View.VISIBLE
        }else{
            this.btnBack!!.visibility = View.GONE
        }

    }




    override fun onCheckedChanged(rg: RadioGroup?, radioButtonID: Int) {

        when(radioButtonID){



        }

    }

    override fun onClick(view: View?) {

        val radioButtonID: Int = this.rgAnswers!!.checkedRadioButtonId
        var radioIndex: Int = -1

        if(radioButtonID != -1){
            radioIndex = this.rgAnswers!!.indexOfChild(this.rgAnswers!!.findViewById(radioButtonID))
        }

        when(view){

            btnNext ->{

                if (radioIndex != -1){ //if a index was selected

                    //submit the answer for the question to the manager
                    this.mSurveyManager!!.submitAnswer(radioIndex)

                    //move on
                    this.generateQuestionsUI(this.mSurveyManager!!.moveNext())

                }else{
                    //nothing selected
                    Toast.makeText(this, "You must first select something!", Toast.LENGTH_SHORT).show()

                }

            }

            btnBack ->{

                //move on
                this.generateQuestionsUI(this.mSurveyManager!!.moveBack())

            }

        }

    }


}