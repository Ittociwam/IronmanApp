package cs246.ironmanapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.content.Intent;

import com.google.gson.Gson;

/**
 * Created by Robbie on 7/6/2015.
 */

public class NewUserFinisher extends Activity implements TaskCompletion {
    private static final String TAG_NEW_USER_FINISHER = "New User Finisher";


    @Override
    public void finish(Activity activity, String json) {

        MainActivity mainActivity = (MainActivity) activity;
        Structs.ReturnMessage newUserMessage = null;
        try {

            Log.v(TAG_NEW_USER_FINISHER, "Json in new user finisher: " + json);
            Gson gson = new Gson();
            newUserMessage = gson.fromJson(json, Structs.ReturnMessage.class);
            String output = "";
            output += newUserMessage.code + " " + newUserMessage.message;

            // as long as the return code in the new user message was equal to 0
            // store the message value (the 13 digit code) locally for future reference
            // otherwise handle the error and kick out of this function
            String EMessage = "";
            UserName username = new UserName();
            switch(newUserMessage.code) {
                case 0:
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mainActivity.getContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user_id", newUserMessage.message);
                    editor.commit();
                    Log.v(TAG_NEW_USER_FINISHER, "saved user_id: " + newUserMessage.message);
                    break;
                case -1:
                    Log.e(TAG_NEW_USER_FINISHER, "An error from the database in insert info: " + newUserMessage.message);
                    EMessage = "We're sorry, but there is an error with our servers. Don't blame yourself. This is our fault.";
                    displayMessage(EMessage, NewUserFinisher.this);
                    break;
                case 1:
                    // duplicate
                    EMessage = "We're sorry, but this username has already been taken, please try again.";
                    displayMessage(EMessage, NewUserFinisher.this);
//                    AlertDialog.Builder builderData = new AlertDialog.Builder(activity);
//                    builderData.setMessage("We're sorry, but this username has already been taken, please try again.")
//                            .setCancelable(false)
//                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    //do things
//                                }
//                            });
//                    AlertDialog alert1 = builderData.create();
//                    alert1.show();
                    Log.w(TAG_NEW_USER_FINISHER, "this name was a " + newUserMessage.message);
                    break;
                case 2:
                    // no ironman in progress
                    EMessage = "We're sorry, but the Lazy Man Iron Man is currently not running. Visit the activities center for more information.";
                    displayMessage(EMessage, NewUserFinisher.this);
                    Log.w(TAG_NEW_USER_FINISHER, newUserMessage.message);
                    break;
                default:
                    EMessage = "Ummm... I don't even know what this error is. You may want to bring this up with the activities center.";
                    displayMessage(EMessage, NewUserFinisher.this);
                    Log.wtf(TAG_NEW_USER_FINISHER, "Got a strange code back from PHP");
            }

            Log.v(TAG_NEW_USER_FINISHER, output);
        } catch (Exception e) {
            Log.e(TAG_NEW_USER_FINISHER, "Error with gson or outputting or something", e);
        }
    }

    public void displayMessage(String message, Activity activity) {

        String dummy = "nothing";
        Log.i(TAG_NEW_USER_FINISHER, "HERE I AM IM WORKING!!!!!!!!!!!!!!");

        Intent intent = new Intent(NewUserFinisher.this, ErrorMessage.class);
        Log.i(TAG_NEW_USER_FINISHER, "AFTERWARDS");
        //intent.putExtra(EXTRA_MESSAGE, message);

        startActivity(intent);
    }
}
