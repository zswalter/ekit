package com.oakland.ekit;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.oakland.ekit.Constants.Companion.Ticket;

public class ServerManager {

    public static ServerManager sharedInstance = new ServerManager();


    private static String authenticationTokenKey = null;

    private static String adminToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTYwOTUzNzkxM30.wfIqMk--0eKozjI6QLlwkYttLtX7vccWzHyGMZk_KbGyfk-sceyVgBgRuXnZMhOEcKDDrzQGZiWkLyPkPx94Tw";



    private static String TAG = ServerManager.class.getSimpleName();

    //Constructor
    ServerManager(){




    }


    //Used to login a user and return a json object with the users authentication credentials
    public static JSONObject userLogin(String username, String pass) throws Exception{

        try{

            //request the authentication token TODO: only need to do this the one time and then save token until we log out of app if token exists, login again
            JsonObject testRequestBody = new JsonObject();

            //create the request body
            testRequestBody.addProperty("username", username);
            testRequestBody.addProperty("password", pass);
            testRequestBody.addProperty("rememberMe", "true");

            //call to make a post request for the authentication
            JSONObject postReturnJson = POST("authenticate", testRequestBody, null);

            //get the token key and save it in server manager for later
            String tokenKey = postReturnJson.getString("id_token");
            authenticationTokenKey = tokenKey;

            //request to get the user data info
            String response = GET("account", tokenKey);

            System.out.println(response);

            //return the json object
            return new JSONObject(response);

        }catch (Exception e){

            throw e;

        }

    }

    //Used to request that a users info is updated //TODO:
    public static UserInfo updateUserInfo(UserInfo newUserInfo) throws Exception{


        //TODO: finsih this!!!!! (currently seems like user does not have access to change their own info unless admin???)

        try{

            //make sure we have a authentication token to talk with first
            if(authenticationTokenKey == null){
                throw new Exception();
            }

            //make the PUT request to the server to update the users account values //TODO: for now sending as admin key for elevated permissions
            String response = PUT("users", newUserInfo.getJsonObject() , adminToken); //TODO: Would like to use this one

            //create a new server user object to represent the new user data
            UserInfo updatedServerUser = Utilities.Companion.parseUserInfo(new JSONObject(response));

            //return the user info that was saved successfully
            return updatedServerUser;


        }catch (Exception e){
            throw e;
        }

    }

    //Used to create a new user on the server //TODO:
    public static boolean createNewUser(String fName, String lName, String username, String pass){

        //TODO: finish by calling to server and get response?

        try{

            //Make the call to the server to create the user
            return true;

        }catch (Exception e){

            Log.d(TAG, e.toString());

            return false;


        }


    }


    //Used to get the survey from the server
    public static Constants.Companion.QuestionList getSurvey() throws Exception{

        try{

            //make sure we have a authentication token to talk with first
            if(authenticationTokenKey == null){
                throw new Exception();
            }

            //Make a get request to get all the survey questions
            String responseString = GET("survey-questions", authenticationTokenKey);

            //Put all the received questions into a json array
            JSONArray questionsJsonArray = new JSONArray(responseString);

            //create a empty survey list to add the questions to
            Constants.Companion.QuestionList surveyList = new Constants.Companion.QuestionList();

            //now go through each of the questions and request their full answers
            for(int i = 0; i<questionsJsonArray.length(); i++){

                JSONObject surveyQuestionJson = questionsJsonArray.getJSONObject(i);

                //we only care about processing the active questions
                if(surveyQuestionJson.getBoolean("isActive")){

                    //make a post request to get the survey question now with its answers
                    JSONObject surveyQuestionWithAnswersJson = POST("survey-controller/get-question-with-answers", new JsonParser().parse(surveyQuestionJson.toString()).getAsJsonObject(), authenticationTokenKey);

                    //pass to get the survey question with answers converted into a survey question object
                    Constants.Companion.QuestionList.Question surveyQuestion = Utilities.Companion.parseQuestionJsonAsSurveyQuestion(surveyQuestionWithAnswersJson);

                    //Add the survey question to the list
                    surveyList.getQuestions().add(surveyQuestion);

                }

            }

            //return the created list
            return surveyList;


        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }


    }



    //Used to get all the open ticket items from the server
    public static ArrayList<Ticket> getOpenTickets() throws Exception{

        try{

            //make sure we have a authentication token first
            if(authenticationTokenKey == null){
                throw new Exception();
            }

            ArrayList<Ticket> openTickets = new ArrayList<>();

            //make the request to get all the tickets
            String responseString = GET("ticket-comments", authenticationTokenKey);




            //return all the open ticket items
            return openTickets;


        }catch (Exception e){
            throw e;
        }

    }



    //Used to make a server GET request to the my sql backend
    private static String GET(String request, String token) throws Exception{


        try{

            //establish the connection
            URL obj = new URL(Constants.Companion.getAPI_URL() + request);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add the token to authenticate the get request
            con.setRequestProperty("Authorization","Bearer "+ token);

            // optional default is GET
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();

            //create the buffer reader to read the incoming response data
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            //read the response
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //JSONObject test = new JSONObject(response.toString());

            //return the response from the GET request
            return response.toString(); //return as string but in json formate

        }catch (Exception e){

            throw e;

        }

    }

    //Used to perform a http post request with json body for the request
    private static JSONObject POST(String request, JsonObject requestBody, String tokenKey) throws Exception{ //TODO: might have to pass token and then in that case only add token if its not null

        try {

            //establish the connection
            URL url = new URL(Constants.Companion.getAPI_URL() + request);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            if(tokenKey != null){
                //add the token to authenticate the get request
                con.setRequestProperty("Authorization","Bearer "+ tokenKey);
            }

            //set the POST options
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            //make the request body json object into a string value
            String jsonInputString = requestBody.toString();

            //create a output stream to input the request body into the server
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            //create a buffer reader to read all response from the server
            try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {

                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                //return the response as a json object
                return new JSONObject(response.toString());

            }

        }catch (Exception err){
            System.out.println(err);

            throw err;

        }

    }

    //Used to send a http PUT request
    private static String PUT(String request, JsonObject requestBody, String token) throws Exception{

        try{

            //establish the connection
            URL url = new URL(Constants.Companion.getAPI_URL() + request);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            //Add the token to authenticate the get request
            con.setRequestProperty("Authorization","Bearer "+ token);

            //Set the PUT options
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");

            con.setDoOutput(true);
            con.setDoInput(true);

            //make the request body json object into a string value
            String jsonInputString = requestBody.toString();

//
//            if( jsonInputString != null){
//
//                DataOutputStream out = new DataOutputStream(con.getOutputStream());
//                out.writeBytes(jsonInputString);
//                out.flush();
//                out.close();
//            }
//

//            //con.connect();
//            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            String temp = null;
//            StringBuilder sb = new StringBuilder();
//            while((temp = in.readLine()) != null){
//                sb.append(temp).append(" ");
//            }
//            String result = sb.toString();
//            in.close();

            //create a output stream to input the request body into the server
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

//            InputStream _is;
//            if (con.getResponseCode() / 100 == 2) { // 2xx code means success
//                _is = con.getInputStream();
//            } else {
//
//                _is = con.getErrorStream();
//
//                //String result = getStringFromInputStream(_is);
//                Log.i("Error != 2xx", "result");
//            }
//
//            return "";

            //create a buffer reader to read all response from the server
            try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {

                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                //return the response as a json object
                return response.toString();

            }

        }catch (Exception err){

            System.out.println(err);

            throw err;

        }


    }

    //Used to create a json object variable from a json string
    private static JSONObject StringToJson(String jsonString) throws Exception{

        //Try and make our json object from the string
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            return jsonObject;
        }catch (JSONException err){
            throw err;
        }

    }

}
