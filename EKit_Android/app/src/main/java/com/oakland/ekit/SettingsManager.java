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

    public void updateSettings(Context context){

        this.mContext = context;

        //get system pref
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);


        try {
            Gson gson = new Gson();

            //get the saved user pref
            String json = sharedPref.getString(mContext.getString(R.string.preference_logged_in_user), null);
            if(json != null){

                LoggedInUser user = gson.fromJson(json, LoggedInUser.class);
                if(user != null){
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







    public LoggedInUser getSavedLoggedInUser() {
        //TODO: update from saved pref first
        return mLoggedInUser;
    }

    public void setSavedLoggedInUser(LoggedInUser mSavedLoggedInUser) {

        this.mLoggedInUser = mSavedLoggedInUser;

        //get system pref
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPref.edit();

        //convert to json and save
        Gson gson = new Gson();
        String json = gson.toJson(this.mLoggedInUser);
        editor.putString(mContext.getString(R.string.preference_logged_in_user), json);

        editor.apply();
        editor.commit();


        //Call to update settings again
        updateSettings(mContext);


    }

    public void perfromLogout() {
        //TODO: clear the save user from saved pref (set to null)

        //get system pref
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.remove(mContext.getString(R.string.preference_logged_in_user));
        editor.apply();
        editor.commit();

    }








}
