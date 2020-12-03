package com.oakland.ekit.ui.login;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.oakland.ekit.data.LoginRepository;
import com.oakland.ekit.data.Result;
import com.oakland.ekit.data.model.LoggedInUser;
import com.oakland.ekit.R;

public class LoginViewModel extends ViewModel {

    public MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    public MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    public LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;


        //TODO: add observer here to watch for the login to happen

//        //create the login result observer
//        Observer<Result<LoggedInUser>> resultObserver = new Observer<Result<LoggedInUser>>() {
//            @Override
//            public void onChanged(Result<LoggedInUser> loggedInUserResult) {
//
//                //see if its a success result or a failed
//                if (loggedInUserResult instanceof Result.Success) {
//                    LoggedInUser data = ((Result.Success<LoggedInUser>) loggedInUserResult).getData();
//
//                    //The result is a success so now return the users data
//                    loginResult.setValue(new LoginResult(new LoggedInUser(data.getUserId(), data.getDisplayName(), data.getIsSpecial(), data.getServerData(), data.getmUserName(), data.getmPassword())));
//                } else {
//                    //If the result was a fail
//                    loginResult.setValue(new LoginResult(R.string.login_failed));
//                }
//
//
//            }
//        };
//
//        //add the observer
//        this.loginRepository.mLoginResult.observe(this, resultObserver);


    }



    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    //Used to attempt a login
    public void login(String username, String password) {
        // can be launched in a separate asynchronous job

        //TODO: add to async job here and then when the server responds, we can return
        //Result<LoggedInUser> result = loginRepository.login(username, password);
        loginRepository.login(username, password); //todo: pass the callback here

        //todo: all this will have to be in a call back that we pass to .login. this will allow the login observer to be called once the call back gets a hit
        //see if its a success result or a failed
//        if (result instanceof Result.Success) {
//            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
//
//            //The result is a success so now return the users data
//            loginResult.setValue(new LoginResult(new LoggedInUser(data.getUserId(), data.getDisplayName(), data.getIsSpecial(), data.getServerData(), username, password)));
//        } else {
//            //If the result was a fail
//            loginResult.setValue(new LoginResult(R.string.login_failed));
//        }


    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null || username.length() <= 1) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() >= 4;
    }

    //Used to determine if logged in or not
    public boolean isLoggedIn(){
        return loginRepository.isLoggedIn();
    }

    //Used to get the logged in user
    public LoggedInUser getLoggedInUser(){
        return loginRepository.getUser();
    }


}
