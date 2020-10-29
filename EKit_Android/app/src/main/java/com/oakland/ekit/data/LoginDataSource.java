package com.oakland.ekit.data;

import com.oakland.ekit.ServerManager;
import com.oakland.ekit.data.model.LoggedInUser;
import com.oakland.ekit.Constants.Companion.UserData;
import org.json.JSONObject;
import java.io.IOException;

import javax.sql.DataSource;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {

            //Pass to authenticate the user via server backend
            JSONObject credentials = ServerManager.sharedInstance.userLogin(username, password);

            if(credentials != null){

                String randomStringID = java.util.UUID.randomUUID().toString();

                JSONObject fakeObject = new JSONObject();

                fakeObject.put(UserData.FirstName.key(), "Zachary");
                fakeObject.put(UserData.LastName.key(), "Denny");
                fakeObject.put(UserData.DOB.key(), "01-27-1997");
                fakeObject.put(UserData.UserId.key(), randomStringID);
                fakeObject.put(UserData.IsSpecialUser.key(), false);
                fakeObject.put(UserData.Email.key(), "zdenny61@outlook.com");


                //TODO: temp (need to parse out)
                LoggedInUser fakeUser = new LoggedInUser(randomStringID,"Zachary Denny",false, fakeObject, username, password);

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
