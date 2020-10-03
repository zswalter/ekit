package com.oakland.ekit;

import android.nfc.Tag;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerManager {

    public static ServerManager sharedInstance = new ServerManager();



    private String TAG = ServerManager.class.getSimpleName();


    //Constructor
    ServerManager(){




    }



    //Used to login a user and return a json object with the users authentication credentials
    public JSONObject userLogin(String username, String pass) throws Exception{

        //TODO: call server function

        try{

            //Make the get request and get the json object in return
            //return this.StringToJson(this.sendGet(""));

        }catch (Exception e){

            throw e;

        }

        return new JSONObject(); //TEMP

    }



    //Used to create a new user on the server
    public boolean createNewUser(String fName, String lName, String username, String pass){

        //TODO: finish by calling to server and get response?

        try{

            //Make the call to the server to create the user
            return true;

        }catch (Exception e){

            Log.d(TAG, e.toString());

            return false;


        }


    }




    //Used to make a server GET request to the my sql backend
    public static String sendGet(String url) throws Exception{

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");



        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();


        return response.toString(); //here is your response which is in string type, but remember that the format is json.

    }


    //Used to create a json object variable from a json string
    private JSONObject StringToJson(String jsonString) throws Exception{

        //Try and make our json object from the string
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            return jsonObject;
        }catch (JSONException err){
            throw err;
        }

    }




}
