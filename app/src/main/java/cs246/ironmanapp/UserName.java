package cs246.ironmanapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import cs246.ironmanapp.R;

/**
 * Created by John on 7/17/15.
 */
public class UserName extends Activity {
    private static final String NEW_USER_URL = "http://robbise.no-ip.info/ironman/newUser.php";
    private static final String TAG_USERNAME = "User Name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG_USERNAME, "in oncreate of UserName");

        Context context = getApplicationContext();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.username_window);

        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width), (int) (height * 0.6));

        Button submit = (Button) findViewById(R.id.button2);


        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getUsername();
                finish();
            }
        });


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
        Log.v(TAG_USERNAME, "Create new user is sending " + NEW_USER_URL + username);

        t.setTaskCompletion(new NewUserFinisher());

        new Thread(t).start();
    }


    /**
     * This function will display a textbox overlay prompting the user to either enter in a new user name
     * or be saved in the database as a random number.
     *
     * @return - Returns whatever the user enters into the new username overlay
     */
    private void getUsername() {
        String username = "billay!";
       //TODO String username = get username from text input
        createNewUser(username);
    }

}
