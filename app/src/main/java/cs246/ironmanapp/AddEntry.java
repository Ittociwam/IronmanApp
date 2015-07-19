package cs246.ironmanapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.internal.widget.TintImageView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by John on 7/6/15.
 */


public class AddEntry extends Activity {

    private static final String NEW_ENTRY_URL = "http://robbise.no-ip.info/ironman/newEntry.php";
    private static final String TAG_OUTPUT_ALL_THE_THINGS = "For Debugging";
    private static final String TAG_ADD_ENTRY = "Add Entry";
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    public Activity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //THIS CODE IS TO RESET USER_ID FOR TESTING!!!!!
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("user_id");
        editor.commit();


        Intent intent = getIntent();

        mainActivity = (Activity) intent.getSerializableExtra("activity");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.addwindow);

        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width), (int) (height * 0.6));

        Log.v(TAG_ADD_ENTRY, "Assigning buton");
        Button submit = (Button) findViewById(R.id.button);

        /**
         * Just making this stuff look prettier
         */
        RadioButton swim = (RadioButton) findViewById(R.id.radioButton);
        RadioButton bike = (RadioButton) findViewById(R.id.radioButton2);
        RadioButton run = (RadioButton) findViewById(R.id.radioButton3);

        swim.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText text = (EditText) findViewById(R.id.miles);
                EditText text2 = (EditText) findViewById(R.id.editText2);
                //text.setBackgroundColor(Color.parseColor("#ff0000"));
            }

        });



        submit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.v(TAG_ADD_ENTRY, "doing shared preferences stuff");
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.context);
                Log.v(TAG_ADD_ENTRY, "about to get string");
                String user = sharedPreferences.getString("user_id", "");

                if (user.isEmpty()) {
                    //
                    // prompt user if they want to have a display name or not and call
                    Log.v(TAG_ADD_ENTRY, "user is empty");
                    //getUsername();


                    Intent intent = new Intent(AddEntry.this, UserName.class);

                    Log.v(TAG_ADD_ENTRY, "intent created: " + intent.toString());


                    startActivity(intent);

                }
                else {
                    sendNewEntry(user);
                    finish();
                }
            }
        });




    }

    /**
     * This function will be called when a user pushes submit on the newEntry form.
     * It will call 2 additional functions, createNewUser and sendNewEntry. createNewUser will only be
     * called if there is no userid stored in the system. Insert info will handle any error messages returned
     * from these 2 functions
     */
//    public void insertInfo() {
//
//
//        if (user.isEmpty()) {
//            //
//            // prompt user if they want to have a display name or not and call
//            Log.v(TAG_ADD_ENTRY, "user is empty");
//            //getUsername();
//
//            Intent intent = new Intent(this, UserName.class);
//
//            startActivity(intent);
//
//        }
//
//        else {
//
//            sendNewEntry(user);
//        }
//    }

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
    private void sendNewEntry(String uuid) {

        Log.v(TAG_ADD_ENTRY, "In send New entry! yay!!! uuid: " + uuid);

//        // Gets a reference to our radio group
//// rBtnDigits is the name of our radio group (code not shown)
//        RadioGroup g = (RadioGroup) findViewById(R.id.RGroup);
//
//// Returns an integer which represents the selected radio button's ID
//        int selected = g.getCheckedRadioButtonId();
//
//// Gets a reference to our "selected" radio button
//        RadioButton b = (RadioButton) findViewById(selected);
//
//// Now you can get the text or whatever you want from the "selected" radio button
//        b.getText();
//
//        String mode = null; // get a 1 2 or 3 from form values bike, swim run respectively
//        String distance = null; // get the distance 2.34 from the form
//        String date = null; // in SQL format please! 2015-07-03 get from the form

    }


}