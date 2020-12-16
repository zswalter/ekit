package com.oakland.ekit;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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


import com.oakland.ekit.Constants.Companion.*;


public class ServerManager {

    public static ServerManager sharedInstance = new ServerManager();


    private static String authenticationTokenKey = null;

    private static String adminToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTYxMDQwNTA1NH0.3PyvL08jE1xskouOyFppVvrUN2ZDyaJaUTvLg0xVgDolzl-usQWn3kIB5PFZ2M_FUM-zJQN1maLdFoLaVqO1KQ";

    private static String TAG = ServerManager.class.getSimpleName();

    //Constructor
    ServerManager(){




    }


    //Used to login a user and return a json object with the users authentication credentials
    public static JSONObject userLogin(String username, String pass) throws Exception{

        try{ //TODO: need to request admin user token before app is ready to go!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

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

    //Used to request that a users info is updated //TODO: work it out to work without admin elevation???? no we will just request them at start
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

    //Used to create a new user on the server //TODO: Finish?
    public static boolean createNewUser(String fName, String lName, String username, String pass, String email) throws Exception{



        //TODO: finish by calling to server and get response?

        try{

            //wrap the user info param
            JsonObject newUserObject = new JsonObject();

            newUserObject.addProperty("activated", true); //activated

            //authorities
            JsonArray permissions = new JsonArray(1);
            permissions.add("ROLE_USER");
            newUserObject.add("authorities", permissions);

            newUserObject.addProperty("email", email); //email
            newUserObject.addProperty("firstName", fName); //firstName
            newUserObject.addProperty("id", 0); //id
            newUserObject.addProperty("imageUrl", ""); //imageUrl
            newUserObject.addProperty("langKey", "en"); //langKey
            newUserObject.addProperty("lastName", lName); //lastName
            newUserObject.addProperty("login", username); //login
            newUserObject.addProperty("password", pass); //password

            //Make the call to the server to create the user (with admin token permissions
            JSONObject postReturnJson = POST("register", newUserObject, adminToken);

            //Success
            return true;

        }catch (Exception e){

            throw e;

        }


    }

    //Used to get the survey from the server
    public static QuestionList getSurvey() throws Exception{

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
            QuestionList surveyList = new QuestionList();

            //now go through each of the questions and request their full answers
            for(int i = 0; i<questionsJsonArray.length(); i++){

                //get each question
                JSONObject surveyQuestionJson = questionsJsonArray.getJSONObject(i);

                //we only care about processing the active questions
                if(surveyQuestionJson.getBoolean("isActive")){

                    //make sure we have a type to base the question type on
                    if (!surveyQuestionJson.isNull("questionType")){

                        switch (surveyQuestionJson.getString("questionType")){

                            case "INPUT":

                                //pass to get the survey question with answers converted into a survey question object and add the survey question to the list
                                surveyList.getQuestions().add(Utilities.Companion.parseQuestionJsonAsSurveyQuestion(null, surveyQuestionJson,  QuestionType.INPUT));

                                break;

                            case "MULTI": //We have multiple answer options to select

                                //make a post request to get the survey question now with its answers
                                JSONObject surveyQuestionWithAnswersJson = POST("survey-controller/get-question-with-answers", new JsonParser().parse(surveyQuestionJson.toString()).getAsJsonObject(), authenticationTokenKey);

                                //pass to get the survey question with answers converted into a survey question object
                                QuestionList.Question surveyQuestion = Utilities.Companion.parseQuestionJsonAsSurveyQuestion(surveyQuestionWithAnswersJson, surveyQuestionJson, QuestionType.MULTI);

                                //Add the survey question to the list
                                surveyList.getQuestions().add(surveyQuestion);

                                break;

                            default:

                                //we don't know this type so lets not do anything with it!

                                break;


                        }

                    }

                }

            }

            //return the created list
            return surveyList;


        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }


    }

    //Used to submit the users survey submission
    public static JSONObject submitSurvey(JsonObject submissionRequestBody) throws Exception {

        try{

            //make sure we have a authentication
            if(authenticationTokenKey == null){
                throw new Exception();
            }

//            JSONObject testing = new JSONObject("{\n" +
//                    "    \"id\": 13,\n" +
//                    "    \"closed\": null,\n" +
//                    "    \"created\": \"2020-12-15T09:48:00-05:00\",\n" +
//                    "    \"closedBy\": null,\n" +
//                    "    \"product\": {\n" +
//                    "      \"id\": 11,\n" +
//                    "      \"productImage\": \"https://cdn.shopify.com/s/files/1/2081/1519/products/1600x1067_US_B_Mint_PROFILE-X2.jpg?v=1590502980.png\",\n" +
//                    "      \"productName\": \"Bike Upgrade Ekit\",\n" +
//                    "      \"productDescription\": \"A basic kit that provides mid level power and duration.\"\n" +
//                    "    },\n" +
//                    "    \"createdBy\": {\n" +
//                    "      \"id\": 5,\n" +
//                    "      \"login\": \"zdenny61\",\n" +
//                    "      \"firstName\": \"Zachary\",\n" +
//                    "      \"lastName\": \"denny\",\n" +
//                    "      \"email\": \"zdenny61@outlook.com\",\n" +
//                    "      \"activated\": true,\n" +
//                    "      \"langKey\": \"en\",\n" +
//                    "      \"imageUrl\": \"\",\n" +
//                    "      \"resetDate\": null\n" +
//                    "    }\n" +
//                    "  }");
//
//            return testing;

            //make the call and get the response
            JSONObject response = POST("survey-controller/submit-survey-answers", submissionRequestBody, authenticationTokenKey);

            return response;


        }catch (Exception e){
            throw e;
        }

    }

    //Used to update ticket
    public static JSONObject updateTicket(JsonObject ticketJson) throws Exception{ //TODO: use this to close ticket

        try {

            //make sure we have a authentication
            if(authenticationTokenKey == null){
                throw new Exception();
            }

            return new JSONObject(PUT("tickets", ticketJson, authenticationTokenKey));

        }catch (Exception e){
            throw e;
        }


    }

    //Used to add a new ticket comment
    public static JSONObject addTicketComment(JsonObject ticketCommentJson) throws Exception{ //TODO: use this to make reply from admin

        try {

            //make sure we have a authentication
            if(authenticationTokenKey == null){
                throw new Exception();
            }

            return POST("ticket-comments", ticketCommentJson, authenticationTokenKey);

        }catch (Exception e){
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
            ArrayList<TicketComment> openTicketComments = new ArrayList<>();


            //make a request to get get all the tickets
            String responseTicketsString = GET("tickets", authenticationTokenKey);


            //parse the json string to a json array
            JSONArray openTicketsJson = new JSONArray(responseTicketsString);

            //parse out each array element into a ticket comment item
            for(int i = 0; i<openTicketsJson.length(); i++) {

                JSONObject ticketJsonObject = (JSONObject) openTicketsJson.get(i);

                int id = ticketJsonObject.getInt("id");

                String ticketCreatedDate = ticketJsonObject.getString("created");
                JSONObject createdBy = ticketJsonObject.getJSONObject("createdBy");
                String firstName = createdBy.getString("firstName");
                String lastName = createdBy.getString("lastName");

                Product ticketProduct = null;

                //check if there is a product for this ticket yet
                if (ticketJsonObject.has("product") && !ticketJsonObject.isNull("product")) {
                    //extract the product from the ticket
                    ticketProduct = new Product(ticketJsonObject.getJSONObject("product"));
                }

                //now check if the ticket is actually closed or not. if so, lets just ignore it.
                if (ticketJsonObject.isNull("closed")) { // there is a closed date so lets not add this ticket to the view
                    //create a ticket item
                    Ticket ticket = new Ticket(id, ticketCreatedDate, new ArrayList<>(0), firstName, lastName, ticketProduct);

                    //add the ticket to the list of tickets
                    openTickets.add(ticket);
                } //else we don't want to add the ticket because it has a close date on it

            }



            //make the request to get all the ticket comments
            String responseTicketCommentsString = GET("ticket-comments", authenticationTokenKey);

            //parse the json string to a json array
            JSONArray openTicketCommentsJson = new JSONArray(responseTicketCommentsString);

            //parse out each array element into a ticket comment item
            for(int i = 0; i<openTicketCommentsJson.length(); i++){

                JSONObject ticketCommentJsonObject = (JSONObject)openTicketCommentsJson.get(i);

                //get values //TODO: add perform null checks
                int id = ticketCommentJsonObject.getInt("id");
                String content = ticketCommentJsonObject.getString("content");
                String ticketCommentCreatedDate = ticketCommentJsonObject.getString("created");
                TicketUserInfo ticketValues = Utilities.Companion.parseTicketUserInfo(ticketCommentJsonObject.getJSONObject("ticket"));
                TicketUserInfo user = Utilities.Companion.parseTicketUserInfo(ticketCommentJsonObject.getJSONObject("user"));

                //create the ticket comment object
                TicketComment ticketComment = new TicketComment(id, content, ticketCommentCreatedDate, ticketValues, user);

                //add the ticket comments to the list of comments
                openTicketComments.add(ticketComment);

            }


            //go through each ticket and see if it has any tickets and comments to put together
            for (Ticket ticket: openTickets) {

                int ticketID = ticket.getId();

                //go through each comment to see if it belows to the ticket
                for(TicketComment comment: openTicketComments){

                    //check if this comment belongs to the ticket
                    if(comment.getTicketValues().getMId().equals(ticketID)){
                        //this comment is part of our ticket so add the comment to it
                        ticket.getComments().add(comment);
                    }

                }

            }


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



