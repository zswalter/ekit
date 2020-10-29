package com.oakland.ekit;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.oakland.ekit.data.LoginDataSource;
import com.oakland.ekit.data.model.LoggedInUser;

import javax.sql.DataSource;

public class SettingsManager{

    public static SettingsManager sharedInstance = new SettingsManager();


    Context mContext = null;

    private LoggedInUser mLoggedInUser = null;
    private String mPrivateEncryptionKeySeed = null;

    public void updateSettings(Context context){

        this.mContext = context;

        //get system pref
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPref.edit();


        try {

            //call to get saved encryption key
            this.retrieveSavedPrivateEncryption(sharedPref, editor);

            //get saved user
            Gson gson = new Gson();

            //get the saved user pref
            String json = sharedPref.getString(mContext.getString(R.string.preference_logged_in_user), null);
            if(json != null){

                LoggedInUser user = gson.fromJson(json, LoggedInUser.class);
                if(user != null){

                    //decrypt the value first
                    user.setmUserName(this.decryptWithPrivateKey(user.getmUserName()));
                    user.setmPassword(this.decryptWithPrivateKey(user.getmPassword()));

                    this.mLoggedInUser = user;
                }

            }else{
                //no save before
                this.mLoggedInUser = null;
            }

        } catch (Exception e) {
            Log.e("SettingManager", "Settings Load Error");
            e.printStackTrace();
        }





    }


    //Used to retrieve private encrypt seed
    private void retrieveSavedPrivateEncryption(SharedPreferences sharedPref, SharedPreferences.Editor editor){

        this.mPrivateEncryptionKeySeed = sharedPref.getString(mContext.getString(R.string.preference_private_encryption_key), null);

        //if its not set yet, lets create a new one
        if(mPrivateEncryptionKeySeed == null){

            //get a new key now
            String newKey = this.generateEncryptionKey();

            this.mPrivateEncryptionKeySeed = newKey;

            //add to sys pref
            editor.putString(mContext.getString(R.string.preference_private_encryption_key), newKey);

            //save
            editor.apply();
            editor.commit();

        }

        
    }






    public LoggedInUser getSavedLoggedInUser() {
        //TODO: update from saved pref first
        return mLoggedInUser;
    }

    public void setSavedLoggedInUser(LoggedInUser savedLoggedInUser) {

        this.mLoggedInUser = savedLoggedInUser;


        //perform encryption on username and password
        LoggedInUser saveInfo = savedLoggedInUser;
        saveInfo.setmUserName(this.encryptWithPrivateKey(saveInfo.getmUserName()));
        saveInfo.setmPassword(this.encryptWithPrivateKey(saveInfo.getmPassword()));

        //get system pref
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPref.edit();

        //convert to json and save the instance with encryption
        Gson gson = new Gson();
        String json = gson.toJson(saveInfo);
        editor.putString(mContext.getString(R.string.preference_logged_in_user), json);

        editor.apply();
        editor.commit();


        //Call to update settings again
        updateSettings(mContext);


    }

    //Used to perform the logout functions for the saved user
    public void perfromLogout() {

        //get system pref
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.remove(mContext.getString(R.string.preference_logged_in_user));
        editor.apply();
        editor.commit();

    }



    //Used to generate a random key to use to encrypt with
    private String generateEncryptionKey(){

        String randomString = java.util.UUID.randomUUID().toString();

        return randomString;

    }


    //Used to retrieve the private encryption key
    public String getPrivateEncryptionKey(){
        return this.mPrivateEncryptionKeySeed;
    }


    //used to encrypt with the private key
    private String encryptWithPrivateKey(String plainStringValue){

        //test some encryption
        return Utilities.Companion.encrypt(this.mPrivateEncryptionKeySeed, plainStringValue);
    }

    //used to decrypt with the private key
    private String decryptWithPrivateKey(String encryptedValue){

        return Utilities.Companion.decrypt(this.mPrivateEncryptionKeySeed, encryptedValue);

    }





}
