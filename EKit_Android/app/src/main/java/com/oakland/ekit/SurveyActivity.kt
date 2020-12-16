package com.oakland.ekit

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.oakland.ekit.Constants.Companion.Product
import com.oakland.ekit.ui.login.LoginViewModelFactory
import com.oakland.ekit.viewModels.SurveyViewModel
import org.json.JSONObject


class SurveyActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private var mViewModel: SurveyViewModel? = null

    private var mContext: Context? = null

    private var rgAnswers: RadioGroup? = null
    private var btnNext: Button? = null
    private var btnBack: Button? = null
    private var txtQuestionText: TextView? = null
    private var txtAnswerInput: EditText? = null
    private var isDoneNext = false


    private var mSurveyManager: SurveyManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set the context
        this.mContext  = this

        //set the view model
        this.mViewModel = ViewModelProviders.of(this, LoginViewModelFactory()).get<SurveyViewModel>(SurveyViewModel::class.java)

        setContentView(R.layout.activity_survey)
        setSupportActionBar(findViewById(R.id.toolbar))


        val thread = Thread(Runnable {

            try {

                //make a server request for the survey
                val survey = ServerManager.getSurvey()

                if (survey != null) {

                    Handler(Looper.getMainLooper()).post { //Perform on main thread

                        //init the survey manager with the survey
                        this.mSurveyManager = SurveyManager(survey)

                        //call to init the ui
                        initUi()

                        //display success
                        //Toast.makeText(this, "Survey Loaded Successfully", Toast.LENGTH_LONG).show()

                    }


                } else {
                    throw Exception()
                }
            } catch (e: Exception) {
                e.printStackTrace()

            }
        })

        thread.start()



    }

    private fun initUi(){

        this.rgAnswers = findViewById(R.id.rgAnswers)
        this.rgAnswers!!.setOnCheckedChangeListener(this)
        this.btnNext = findViewById(R.id.btnNext)
        this.btnNext!!.setOnClickListener(this)
        this.btnBack = findViewById(R.id.btnBack)
        this.btnBack!!.setOnClickListener(this)
        this.txtQuestionText = findViewById(R.id.txtSurveyQuestion)
        this.txtAnswerInput = findViewById(R.id.txtAnswerInput)

        this.btnNext!!.visibility = View.VISIBLE

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

        //clear all radio buttons first and the text box
        this.rgAnswers!!.removeAllViews()
        this.txtAnswerInput!!.text.clear()

        //check if this needs radio buttons or text box
        if(question.questionType != Constants.Companion.QuestionType.INPUT){ //needs radio

            this.rgAnswers!!.visibility = View.VISIBLE
            this.txtAnswerInput!!.visibility = View.GONE


            //for each answer in the question, lets make a radio button
            for (i in 0 until question.answerStringArray!!.count()) {

                //create the radio button
                button = RadioButton(this)
                button.text = question.answerStringArray!![i]

                //add the button to the radio group
                this.rgAnswers!!.addView(button)

                //if the question has previously been answered
                if (this.mSurveyManager!!.getQuestionAnswer().questionAnswerIndex != null) {

                    if(this.mSurveyManager!!.getQuestionAnswer().questionAnswerIndex!! == i){
                        this.rgAnswers!!.check(button.id)
                    }

                }

            }

        }else{ //we have input text box answer

            this.rgAnswers!!.visibility = View.GONE
            this.txtAnswerInput!!.visibility = View.VISIBLE

            if(this.mSurveyManager!!.getQuestionAnswer().answerString != null){
                this.txtAnswerInput!!.setText(this.mSurveyManager!!.getQuestionAnswer().answerString)
            }

        }

        //see if we have a next question
        if(this.mSurveyManager!!.isNext()){

            //not done
            isDoneNext = false

            this.btnNext!!.text = "NEXT QUESTION"

        }else{

            //done is next
            isDoneNext = true

            this.btnNext!!.text = "SUBMIT SURVEY"

            //TODO: show submit survey button !!!!!!!!!!!!!!!!!!! and add a counter for the question number that they are one
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

                if(isDoneNext){ //last question so prompt the survey to be submitted

                    AlertDialog.Builder(this).setTitle("Submit Survey").setMessage("Are you sure you wish to submit the survey?").setNegativeButton("Dismiss") { dialog, which ->

                        //Dismiss
                        dialog.dismiss()

                    }.setPositiveButton("Submit") { _, _ ->

                        //check if this needs radio buttons or text box
                        if(this.mSurveyManager!!.getCurrentQuestion().questionType != Constants.Companion.QuestionType.INPUT) { //needs radio

                            if (radioIndex != -1) { //if a index was selected

                                //submit the answer for the question to the manager
                                this.mSurveyManager!!.submitAnswer(radioIndex, null)

                                //call to submit the survey
                                this.submitSurveyToServer()

                            }else{
                                //nothing selected
                                Toast.makeText(this, "You must first select something!", Toast.LENGTH_SHORT).show()
                            }

                        }else{//need textbox answer

                            if(this.txtAnswerInput!!.text.toString() != null){

                                //submit the answer for the question to the manager
                                this.mSurveyManager!!.submitAnswer(null, this.txtAnswerInput!!.text.toString())

                                //call to submit the survey
                                this.submitSurveyToServer()

                            }

                        }

                    }.setCancelable(true).show()


                }else{

                    //check if this needs radio buttons or text box
                    if(this.mSurveyManager!!.getCurrentQuestion().questionType != Constants.Companion.QuestionType.INPUT) { //needs radio


                        if (radioIndex != -1){ //if a index was selected

                            //submit the answer for the question to the manager
                            this.mSurveyManager!!.submitAnswer(radioIndex, null)

                            //move on
                            this.generateQuestionsUI(this.mSurveyManager!!.moveNext())

                        }else{
                            //nothing selected
                            Toast.makeText(this, "You must first select something!", Toast.LENGTH_SHORT).show()

                        }



                    }else{//text box answer

                        if(this.txtAnswerInput!!.text.toString() != null){

                            //submit the answer for the question to the manager
                            this.mSurveyManager!!.submitAnswer(null, this.txtAnswerInput!!.text.toString())

                        }

                        //move on
                        this.generateQuestionsUI(this.mSurveyManager!!.moveNext())


                    }


                }


            }

            btnBack ->{

                //move on
                this.generateQuestionsUI(this.mSurveyManager!!.moveBack())

            }

        }

    }

    //Used to submit the survey to the server
    private fun submitSurveyToServer(){

        //TODO: finish


        val thread = Thread(Runnable {

            try {

                //request the information that needs to be passed back to the server
                val usersSurveyAnswers = this.mSurveyManager!!.getAnswersForSubmission()
                val userId = mViewModel!!.getLoggedInUser().userId

                //Now create the map/json object to send as a request body
                val surveySubmissionJson = Utilities.generateSurveySubmissionRequestBody(userId.toInt(), usersSurveyAnswers)

                //make a server request to submit the completed survey answers
                val submissionResponse = ServerManager.submitSurvey(surveySubmissionJson)

                if (submissionResponse != null) {

                    var product: Product? = null

                    //make sure we got a product returned
                    if(!submissionResponse.isNull("product")){
                        product = Product(submissionResponse.getJSONObject("product"))
                    }else{
                        throw Exception()
                    }

                    Handler(Looper.getMainLooper()).post { //Perform on main thread

                        //make sure not null first
                        if(product != null){
                            //prompt the user with results
                            this.showResultsDialog(product, submissionResponse)
                        }else{
                            throw Exception()
                        }


                    }


                } else {
                    throw Exception()
                }
            } catch (e: Exception) {
                e.printStackTrace()


                Handler(Looper.getMainLooper()).post { //Perform on main thread

                    //Display error message
                    AlertDialog.Builder(this).setTitle("Error...").setMessage("A error occurred while processing your survey, please try again.").setPositiveButton("Continue") { dialog, which ->

                        //go back to the home page
                        super.onBackPressed()

                    }.setCancelable(false).show()

                }

            }
        })

        thread.start()


    }


    //Used to present a survey results dialog box
    private fun showResultsDialog(product: Product, ticketJson: JSONObject) {

        var submitWithComment = false

        val li = LayoutInflater.from(mContext)
        val promptsView = li.inflate(R.layout.dialog_survey_results, null)
        val alertDialogBuilder = AlertDialog.Builder(mContext)
        alertDialogBuilder.setView(promptsView)

        val imgProductImage = promptsView.findViewById<View>(R.id.imgSuggestedProduct) as ImageView //TODO: would update with product image
        val txtUserCommentBox = promptsView.findViewById<View>(R.id.txtUsersComments) as EditText
        val btnFinish = promptsView.findViewById<View>(R.id.btnFinishResults) as Button
        val lblSuggestedProduct = promptsView.findViewById<View>(R.id.lblSuggestedProduct) as TextView

        //set the product suggestion lbl
        lblSuggestedProduct.text = "Product Suggestion: ${product.productName}"

        //make a text editor watcher for on text change
        val mTextEditorWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                //if there is text in the comment box, change to send mode
                if(s.isNotEmpty()){
                    submitWithComment = true
                    btnFinish.text = "Send Comment"
                }else{
                    submitWithComment = false
                    btnFinish.text = "Continue"
                }

            }

            override fun afterTextChanged(s: Editable) {}
        }

        //make the on click listener
        btnFinish.setOnClickListener {
            if(submitWithComment){
                //open ticket and add comment
                this.submitTicketWithComment(product, ticketJson, txtUserCommentBox!!.text.toString())
            }else{
                //all good to go with closed ticket so lets finish the survey view
                super.onBackPressed()
            }
        }

        //add the text listener
        txtUserCommentBox.addTextChangedListener(mTextEditorWatcher)

        // setup dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setTitle("Survey Results").show()

    }

    //Used to submit a ticket (update) with comment
    private fun submitTicketWithComment(product: Product, ticketJson: JSONObject, content: String){


        val thread = Thread(Runnable {

            try {

                //TODO: update the ticket to open (close: null) and then add the ticket comment to the server

                var newTicket = ticketJson

                //"open" the ticket
                newTicket.put("closed", null)

                //Call the server manager to update ticket to open
                val updatedTicket = ServerManager.updateTicket(JsonParser().parse(ticketJson.toString()).asJsonObject)

                //make sure we have a updated ticket result now
                if (updatedTicket != null) {

                    val newTicketCommentJson = Utilities.generateTicketCommentRequestBody(updatedTicket, content, mViewModel!!.getLoggedInUser().serverData)

                    if (ServerManager.addTicketComment(newTicketCommentJson) != null){

                        //TODO: then we must prompt the success after success
                        showAlertMessageBox("Comment Request Submitted!", "One of our representatives will review and get back to you as soon as possible. Thank you!")

                    }else{
                        throw Exception()
                    }

                } else {
                    throw Exception()
                }

            } catch (e: Exception) {
                e.printStackTrace()

                Handler(Looper.getMainLooper()).post { //Perform on main thread

                    //Display error message
                    AlertDialog.Builder(this).setTitle("Error...").setMessage("A error occurred while processing your survey, please try again.").setPositiveButton("Continue") { dialog, which ->

                        //go back to the home page
                        super.onBackPressed()

                    }.setCancelable(false).show()

                }

            }
        })

        thread.start()

    }


    //Used to show a alert message box
    private fun showAlertMessageBox(title: String, msg: String){


        Handler(Looper.getMainLooper()).post { //Perform on main thread

            //Display message
            AlertDialog.Builder(this).setTitle(title).setMessage(msg).setPositiveButton("Continue") { dialog, which ->

                //go back to the home page
                super.onBackPressed()

            }.setCancelable(false).show()

        }

    }



}