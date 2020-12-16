package com.oakland.ekit.data;

import android.util.Log;

import com.google.gson.JsonObject;
import com.oakland.ekit.Constants;
import com.oakland.ekit.ServerManager;
import com.oakland.ekit.data.model.LoggedInUser;
import com.oakland.ekit.Constants.Companion.UserData;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;

import javax.sql.DataSource;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    //todo: maybe pass the call back to be used here???
    public void login(String username, String password, LoginRepository repo) {

        try {
            Thread thread = new Thread(() -> {
                try  {

                    //Pass to authenticate the user via server backend
                    JSONObject credentials = ServerManager.sharedInstance.userLogin(username, password, false);


                    if(credentials != null){

                        //check if the user is activated and should be able to login
                        if(credentials.getBoolean(UserData.Activated.key())){ //good to go

                            //create the display name using the first and last name of the user
                            String displayName = String.format("%s %s", credentials.getString(UserData.FirstName.key()),  credentials.getString(UserData.LastName.key()));

                            //break down the authorities array into an array of all the authority stings
                            JSONArray arrJson = credentials.getJSONArray(UserData.Authorities.key());
                            String[] arr = new String[arrJson.length()];
                            for(int i = 0; i < arrJson.length(); i++)
                                arr[i] = arrJson.getString(i);


                            Boolean isSpecial = false;

                            //determine if special user
                            for (String authority: arr) {

                                if (authority.equals("ROLE_ADMIN")){
                                    //this is a special user
                                    isSpecial = true;
                                }

                                //"authorities":["ROLE_USER","ROLE_ADMIN"]}
                            }

//                        String randomStringID = java.util.UUID.randomUUID().toString();
//
//                        JSONObject fakeObject = new JSONObject();

//                        fakeObject.put(UserData.FirstName.key(), "Zachary");
//                        fakeObject.put(UserData.LastName.key(), "Denny");
//                        fakeObject.put(UserData.DOB.key(), "01-27-1997");
//                        fakeObject.put(UserData.UserId.key(), randomStringID);
//                        fakeObject.put(UserData.IsSpecialUser.key(), false);
//                        fakeObject.put(UserData.Email.key(), "zdenny61@outlook.com");


                            LoggedInUser loginUser = new LoggedInUser(credentials.getString(UserData.UserId.key()), displayName, isSpecial, credentials, username, password);

                            //call to post the success results
                            repo.mLoginResult.postValue(new Result.Success<>(loginUser));


                        }else{ //not good to go

                            //TODO: does this actually work??
                            throw new Exception();

                        }



                        //return the success user
                        //return new Result.Success<>(fakeUser);

                    }else{

                        //TODO: does this actually work??
                        throw new Exception();

                    }

                } catch (Exception e) {
                    e.printStackTrace();

                    //post that a error happen
                    repo.mLoginResult.postValue(new Result.Error(e));

                }
            });

            thread.start();


            // TODO: handle loggedInUser authentication for now just making fake account
           // LoggedInUser fakeUser = new LoggedInUser(java.util.UUID.randomUUID().toString(),"Jane Doe",false, new JSONObject());

            //return new Result.Error(new IOException("Error logging in"));

        } catch (Exception e) {

            //return new Result.Error(new IOException("Error logging in", e));

        }
    }


    public void logout() {
        // TODO: revoke authentication
    }
}
