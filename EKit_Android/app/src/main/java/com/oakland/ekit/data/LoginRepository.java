package com.oakland.ekit.data;

import com.oakland.ekit.SettingsManager;
import com.oakland.ekit.data.model.LoggedInUser;

import java.util.Set;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted //TODO:??
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {

        //set the data source
        this.dataSource = dataSource;


        //see if we have a user to login auto
        if(SettingsManager.sharedInstance.getSavedLoggedInUser() != null){

            LoggedInUser user = SettingsManager.sharedInstance.getSavedLoggedInUser();

            //login
            this.login(user.getmUserName(), user.getmPassword());

        }

    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {


        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }


        return instance;
    }



    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {

        //tell the settings manager to forget
        SettingsManager.sharedInstance.perfromLogout();

        //clear the user data from repo
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;

        //Save the logged in user
        SettingsManager.sharedInstance.setSavedLoggedInUser(user);
        // If user credentials will be cached in local storage, it is recommended it be encrypted //TODO:??
        // @see https://developer.android.com/training/articles/keystore
    }

    //Used to
    public Result<LoggedInUser> login(String username, String password) {
        // handle login
        Result<LoggedInUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }

    //Used to get the logged in user
    public LoggedInUser getUser(){
        return this.user;
    }

}
