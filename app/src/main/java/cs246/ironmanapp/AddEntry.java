package cs246.ironmanapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by John on 7/6/15.
 */


public class AddEntry extends Activity {
    private static final String NEW_USER_URL = "http://robbise.no-ip.info/ironman/newUser.php";
    private static final String NEW_ENTRY_URL = "http://robbise.no-ip.info/ironman/newEntry.php";
    private static final String TAG_OUTPUT_ALL_THE_THINGS = "For Debugging";
    private static final String TAG_ADD_ENTRY = "Add Entry";
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    public Activity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();

        mainActivity = (Activity) intent.getSerializableExtra("activity");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.addwindow);

        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width), (int) (height * 0.6));

        Button btnBack = (Button) findViewById(R.id.button);
        Button submit = (Button) findViewById(R.id.button);


        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                insertInfo();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
//                Intent intent = new Intent(AddEntry.this, MainActivity.class);
                //EditText number = (EditText) findViewById(R.id.number);
                //String message = "Your favorite number is " + number.getText().toString();
                //intent.putExtra(EXTRA_MESSAGE, message);

                Intent intent = new Intent(AddEntry.this, UserName.class);
                //intent.putExtra("activity", AddEntry.this);
                startActivity(intent);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               // startActivity(intent);
                finish();

            }
        });


    }


    /**
     * This function will be called when a user pushes submit on the newEntry form.
     * It will call 2 additional functions, createNewUser and sendNewEntry. createNewUser will only be
     * called if there is no userid stored in the system. Insert info will handle any error messages returned
     * from these 2 functions
     */
    public void insertInfo() {
        Log.v(TAG_OUTPUT_ALL_THE_THINGS, "doing shared preferences stuff");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.context);
        Log.v(TAG_OUTPUT_ALL_THE_THINGS, "about to get string");
        String user = sharedPreferences.getString("user_id", "");

        if (user.isEmpty()) {
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

        // Gets a reference to our radio group
// rBtnDigits is the name of our radio group (code not shown)
        RadioGroup g = (RadioGroup) findViewById(R.id.RGroup);

// Returns an integer which represents the selected radio button's ID
        int selected = g.getCheckedRadioButtonId();

// Gets a reference to our "selected" radio button
        RadioButton b = (RadioButton) findViewById(selected);

// Now you can get the text or whatever you want from the "selected" radio button
        b.getText();

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
    private void createNewUser(String username) {
        MainActivity.Task t = new MainActivity.Task(NEW_USER_URL, "username=" + username); // two parameters here because it's a post http request
        Log.v(TAG_ADD_ENTRY, "Create new user is sending " + NEW_USER_URL + username);

        t.setTaskCompletion(new NewUserFinisher());

        new Thread(t).start();
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
}