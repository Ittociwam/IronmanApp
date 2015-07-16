package cs246.ironmanapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.SharedPreferences;

import android.content.Intent;

import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListAdapter;
import android.widget.Button;
import android.widget.ImageView;


import android.widget.ListAdapter;

import android.widget.Button;
import android.widget.ImageView;


import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private static final String GET_CONTESTANTS_URL = "http://robbise.no-ip.info/ironman/getContestants.php?";
    private static final String GET_ENTRIES_URL = "http://robbise.no-ip.info/ironman/getEntries.php?";
    private static final String NEW_USER_URL = "http://robbise.no-ip.info/ironman/newUser.php";
    private static final String NEW_ENTRY_URL = "http://robbise.no-ip.info/ironman/newEntry.php";
    private android.os.Handler handler;
    public ListView lView;
    private static Context context;
    public static Activity activity;
    public ArrayAdapter<String> adapter;

    int progressStatus = 0;
    ProgressBar gprogress;
    ProgressBar bprogress;
    ProgressBar pprogress;
    //EntriesGetter e;
    private final String USER_AGENT = "Mozilla/5.0";
    private static final String TAG_MAIN_ACTIVITY = "Main Activity";
    private static final String TAG_OUTPUT_ALL_THE_THINGS = "For Debugging";
    private static String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        MainActivity.context = MainActivity.this.getApplicationContext();

        activity = this;


        Log.v(TAG_OUTPUT_ALL_THE_THINGS, "We have started!");
        super.onCreate(savedInstanceState);
        Log.v(TAG_OUTPUT_ALL_THE_THINGS, "setContentView!");
        setContentView(R.layout.activity_main);

        Log.v(TAG_OUTPUT_ALL_THE_THINGS, "Creating Handler");
        handler = new android.os.Handler();

        Log.v(TAG_OUTPUT_ALL_THE_THINGS, "Shared preference stuff");
       SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Log.v(TAG_OUTPUT_ALL_THE_THINGS, "Shared preference getting user");
        user = sharedPreferences.getString("user_id", "");

        ImageView add = (ImageView) findViewById(R.id.imageView3);
        ImageView rank = (ImageView) findViewById(R.id.imageView5);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, Pop.class));
            }
        });

        rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Rank.class));


            }
        });

        handler = new android.os.Handler();
        Log.v(TAG_OUTPUT_ALL_THE_THINGS, "About to get contestants!");
        getContestants();

        Log.v(TAG_OUTPUT_ALL_THE_THINGS, "About to get entries!");
        getEntries();

        Log.v(TAG_OUTPUT_ALL_THE_THINGS, "about to insert info");
        insertInfo();

    }

    public void testProgress(View view) {

        gprogress = (ProgressBar) findViewById(R.id.progressBar2);
        bprogress = (ProgressBar) findViewById(R.id.progressBar);
        pprogress = (ProgressBar) findViewById(R.id.progressBar3);
        progressStatus += 5;
        progressStatus = progressStatus % 105;
        gprogress.setProgress(progressStatus);

    }


    public void getContestants() {
        Task t = new Task(GET_CONTESTANTS_URL + "semester=" + getSelectedSemester());

        t.setTaskCompletion(new ContestantFinisher());

        new Thread(t).start();

    }

    public void getEntries() {
        Task t = new Task(GET_ENTRIES_URL + "semester=" + getSelectedSemester() + "&id=" + getContestantID());

        t.setTaskCompletion(new EntryFinisher());

        new Thread(t).start();
    }

    /**
     * This function will be called when a user pushes submit on the newEntry form.
     * It will call 2 additional functions, createNewUser and sendNewEntry. createNewUser will only be
     * called if there is no userid storred in the system. Insert info will handle any error messages returned
     * from these 2 functions
     */
    public void insertInfo() {
        Log.v(TAG_OUTPUT_ALL_THE_THINGS, "doing shared preferences stuff");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Log.v(TAG_OUTPUT_ALL_THE_THINGS, "about to get string");
        user = sharedPreferences.getString("user_id", "");

        if(user.isEmpty()){
        Structs.ReturnMessage newUserMessage = null;
            // prompt user if they want to have a display name or not and call
            Log.v(TAG_OUTPUT_ALL_THE_THINGS, "user is empty");
            user = getUsername();
            createNewUser(user);


        }


        Structs.ReturnMessage newEntryMessage = null;

        // take the users id and call
         //newEntryMessage = sendNewEntry(user);


//        if(newEntryMessage.code == 0)
//        {
//            Log.v(TAG_MAIN_ACTIVITY, "newEntry good! " + newEntryMessage.message);
//        }
//        else{
//            Log.e(TAG_MAIN_ACTIVITY, "There was an issue sending new entry" + newEntryMessage.message);
//        }
    }

    /**
     * This function will take a unique identifier for the current user and then send it, along with
     * the mode, distance, and date from a form in the app that the user will fill out with this
     * information. It will return a message object that contains a code and a message. If everythign succeded
     * the code will be 0
     * <p/>
     * See newEntry.php in the Ironman web services google doc for other message possibilities
     *
     * @param uuid - this is a 13 digit unique identifier that we will send to the web service so
     *             it knows what user we are trying to insert for
     * @return - this will return a ReturnMessage object for insertID to handle
     * @see <a href="https://docs.google.com/document/d/1tAoB8SyYUl-wQ2T6NnoV7d-_Y1-sQZ6a9S2OFklqa7A/edit#bookmark=id.3azfvv3x9lua">NewEntry.php Documentation</a>
     */
    private Structs.ReturnMessage sendNewEntry(String uuid) {

        String mode = null; // get a 1 2 or 3 from form values bike, swim run respectively
        String distance = null; // get the distance 2.34 from the form
        String date = null; // in SQL format please! 2015-07-03 get from the form

        return null;
    }

    /**
     * This function will take a display name or null and try to insert the user into the database.
     * If this function is successful it will return a code 0 with a message that is a 13 digit uuid
     * that can then be stored locally on the device.
     * <p/>
     * See newUser.php in the Ironman web services google doc for other message possibilities
     *
     * @param username - either the username the user wants as a display name or null if the user wants
     *                 to be identified as a number
     * @return - a new user message that contains a code as to what kind of message it is and the
     * message itself
     * @see <a href="https://docs.google.com/document/d/1tAoB8SyYUl-wQ2T6NnoV7d-_Y1-sQZ6a9S2OFklqa7A/edit#bookmark=id.wwyex9oo7k64">NewUser.php Documentatoin</a>
     */
    private void createNewUser(String username){
        Task t = new Task(NEW_USER_URL, "username=" + username); // two parameters here because it's a post http request

        t.setTaskCompletion(new NewUserFinisher());

        new Thread(t).start();
    }


    class Task implements Runnable {
        public String json;
        private String url;
        private String params;
        private boolean isPost;
        private TaskCompletion taskCompletion;

        public void setTaskCompletion(TaskCompletion t) {
            taskCompletion = t;
        }

        /**
         * Constructor for a GET request takes only a url ex. "http://robbise.no-ip.info/ironman/getContestants.php?semester=FALL2015"
         *
         * @param url
         */
        public Task(String url) {
            this.url = url;
            this.params = "";
            isPost = false;
        }

        /**
         * Constructor for a POST request takes a url string and parameters
         * ex. url = "http://robbise.no-ip.info/ironman/newUser.php"
         * params = username=batman
         *
         * @param url
         * @param params
         */
        public Task(String url, String params) {
            this.url = url;
            this.params = params;
            if (!params.isEmpty()) {
                isPost = true;
            } else
                isPost = false;
        }


        /**
         * This is the overridden run method for Task which is a runnable class. It creates a
         * new urlReader with the url that was passed in and then sends a get request and assigns the
         * returned data to a static json object.
         * THIS METHOD IS UNDER CONSTRUCTION AND WILL NOT LOOK LIKE THIS LATER
         */

        @Override
        public void run() {
            URLReader urlReader = new URLReader(url, params);
            try {
                Log.i(TAG_MAIN_ACTIVITY, url);
                if (isPost == true) {
                    json = urlReader.sendPost(url, params);
                } else {
                    json = urlReader.sendGet(url);
                }
            } catch (Exception e) {
                Log.e(TAG_MAIN_ACTIVITY, "Error trying to send a GET request with: ", e);
                e.printStackTrace();
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    taskCompletion.finish(activity,json);

                }
            });
        }
    }


    /**
     * This function will display a textbox overlay prompting the user to either enter in a new user name
     * or be saved in the database as a random number.
     *
     * @return - Returns whatever the user enters into the new username overlay
     */
    private String getUsername() {
        //TODO john, we need an overlay here to prompt user for a username
        return "TestUser";
    }


        /**
         * this method will need to get the semester from a select
         * drop down menu on activity_main.xml and return it.
         *
         * @return a string that is the selected semester
         */
        private String getSelectedSemester() {

            return "FALL2015"; // a default value for testing
        }

        /**
         * this method will need to get the ID from a locally stored ID number
         * and return it.
         *
         * @return a string that is the ID of the current user
         */
        private String getContestantID() {

            return "55943eb52b381"; // id for Scooby Doo
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
    }


