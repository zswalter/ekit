package com.oakland.ekit.data;

import com.oakland.ekit.ServerManager;
import com.oakland.ekit.data.model.LoggedInUser;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {

            //Pass to authenticate the user via server backend
            JSONObject credentials = ServerManager.sharedInstance.userLogin(username, password);

            if(credentials != null){

                //TODO: temp (need to parse out)
                LoggedInUser fakeUser = new LoggedInUser(java.util.UUID.randomUUID().toString(),"Zachary Denny",false, new JSONObject());

                //return the success user
                return new Result.Success<>(fakeUser);

            }


            // TODO: handle loggedInUser authentication for now just making fake account
           // LoggedInUser fakeUser = new LoggedInUser(java.util.UUID.randomUUID().toString(),"Jane Doe",false, new JSONObject());

            return new Result.Error(new IOException("Error logging in"));

        } catch (Exception e) {

            return new Result.Error(new IOException("Error logging in", e));

        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
