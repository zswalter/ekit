package com.oakland.ekit.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.oakland.ekit.AdminHomePageActivity;
import com.oakland.ekit.Constants;
import com.oakland.ekit.CreateUserActivity;
import com.oakland.ekit.R;
import com.oakland.ekit.ServerManager;
import com.oakland.ekit.SettingsManager;
import com.oakland.ekit.SurveyActivity;
import com.oakland.ekit.UserHomepageActivity;
import com.oakland.ekit.data.Result;
import com.oakland.ekit.data.model.LoggedInUser;
import com.oakland.ekit.ui.login.LoginViewModel;
import com.oakland.ekit.ui.login.LoginViewModelFactory;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel mViewModel;

    private Context mContext;


    private EditText mUsernameEditText, mPasswordEditText;
    private Button mLoginButton, mBtnCreateUser;
    private ProgressBar mLoadingProgressBar;

    private LoginFormState mLoginFormState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_login);

        //update settings manager first
        SettingsManager.sharedInstance.updateSettings(mContext);

        //make a thread for the server call
        Thread thread = new Thread(() -> {
            try  {

                //Pass to authenticate the admin user to get the admin token
                JSONObject credentials = ServerManager.sharedInstance.userLogin("admin", "admin", true);

                //if the token went through fine, then we will get the right stuff
                if(credentials == null){

                    //TODO: does this actually work??
                    throw new Exception();



                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();



        //get view model
        mViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        //create the login result observer
        Observer<Result<LoggedInUser>> resultObserver = new Observer<Result<LoggedInUser>>() {
            @Override
            public void onChanged(Result<LoggedInUser> loggedInUserResult) {

                //perform null check
                if(loggedInUserResult == null){
                    return;
                }

                //see if its a success result or a failed
                if (loggedInUserResult instanceof Result.Success) {
                    LoggedInUser data = ((Result.Success<LoggedInUser>) loggedInUserResult).getData();

                    //call to set the logged in user
                    mViewModel.loginRepository.setLogedInUser(loggedInUserResult);

                    //The result is a success so now return the users data
                    mViewModel.loginResult.setValue(new LoginResult(new LoggedInUser(data.getUserId(), data.getDisplayName(), data.getIsSpecial(), data.getServerData(), data.getmUserName(), data.getmPassword())));
                } else {
                    //If the result was a fail
                    mViewModel.loginResult.setValue(new LoginResult(R.string.login_failed));
                }


            }
        };

        //add the observer
        this.mViewModel.loginRepository.mLoginResult.observe(this, resultObserver);


        //see if we are already logged in and if so, lets continue
        if(mViewModel.loginRepository.isLoggedIn()){
            updateUiWithUser(mViewModel.getLoggedInUser());
        }

        this.mUsernameEditText = findViewById(R.id.username);
        this.mPasswordEditText = findViewById(R.id.password);
        this.mLoginButton = findViewById(R.id.login);
        this.mLoadingProgressBar = findViewById(R.id.loading);
        this.mBtnCreateUser = findViewById(R.id.btnCreateAccount);

        //Call to init all the observers
        initObservers();

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                mViewModel.loginDataChanged(mUsernameEditText.getText().toString(),
                        mPasswordEditText.getText().toString());
            }
        };
        mUsernameEditText.addTextChangedListener(afterTextChangedListener);
        mPasswordEditText.addTextChangedListener(afterTextChangedListener);
        mPasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                //When user clicks done button on the keyboard
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    //check if text forms are valid first before continuing
                    if(mLoginFormState.isDataValid()){

                        //Attempt the login
                        mViewModel.login(mUsernameEditText.getText().toString(),
                                mPasswordEditText.getText().toString());

                    }

                }
                return false;
            }
        });

        //On click listener for the actual on screen login btn
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Attempt the login
                mLoadingProgressBar.setVisibility(View.VISIBLE);
                mViewModel.login(mUsernameEditText.getText().toString(),
                        mPasswordEditText.getText().toString());

            }
        });

        //On click listener for create account btn
        mBtnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //goto the create user activity
                Intent i = new Intent(mContext, CreateUserActivity.class);
                mContext.startActivity(i);

            }
        });
    }




    //Used to init the observers for the view
    private void initObservers(){

        mViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }

                //set the login form state for the rest of the view
                mLoginFormState = loginFormState;

                mLoginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    mUsernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    mPasswordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        //When a result of a login is received
        mViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {

                if (loginResult == null) {
                    return;
                }

                mLoadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }

                //Check if we had a success login
                if (loginResult.getSuccess() != null) {

                    //TODO: handle this in a way
                    boolean test = loginResult.getSuccess().getIsSpecial();

                    //Login was success so lets call to update the ui
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);



                //Complete and destroy login activity once successful
                //finish();
            }
        });


    }

    //Used to update ui with logged in user
    private void updateUiWithUser(LoggedInUser user) {

        //String welcome = getString(R.string.welcome) + user.getDisplayName();

        //Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();

        // TODO : initiate successful logged in experience

        //Call to finish since we don't want the login page any more
        finish();

        //Check if they are regular user or special user
        if(!user.getIsSpecial()){

            Intent i = new Intent(this, UserHomepageActivity.class);
            mContext.startActivity(i);


        }else{

            //They are a special user so goto admin portal
            Intent i = new Intent(this, AdminHomePageActivity.class);
            mContext.startActivity(i);

        }


    }


    //Used to show a login fail
    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();

        //TODO: make more of a popup box maybe and dismiss the keyboard!
    }
}
