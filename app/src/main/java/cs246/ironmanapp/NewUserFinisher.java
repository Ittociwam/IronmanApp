package cs246.ironmanapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Robbie on 7/6/2015.
 */
public class NewUserFinisher implements TaskCompletion {
    private static final String TAG_NEW_USER_FINISHER = "New User Finisher";

    @Override
    public void finish(Activity activity, String json) {
        Structs.ReturnMessage newUserMessage = null;
        try {
            Gson gson = new Gson();
            newUserMessage = gson.fromJson(json, Structs.ReturnMessage.class);
            String output = "";
            output += newUserMessage.code + " " + newUserMessage.message;

            // as long as the return code in the new user message was equal to 0
            // store the message value (the 13 digit code) locally for future reference
            // otherwise handle the error and kick out of this function
            switch(newUserMessage.code) {
                case 0:
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user_id", newUserMessage.message);
                    editor.commit();
                    break;

                case -1:
                    Log.e(TAG_NEW_USER_FINISHER, "An error from the database in insert info: " + newUserMessage.message);
                    break;
                case 1:
                    Log.w(TAG_NEW_USER_FINISHER, "this name was a " + newUserMessage.message);
                    break;
                case 2:
                    Log.w(TAG_NEW_USER_FINISHER, newUserMessage.message);
                    break;
                default:
                    Log.wtf(TAG_NEW_USER_FINISHER, "Got a strange code back from PHP");
            }
            Log.v(TAG_NEW_USER_FINISHER, output);
        } catch (Exception e) {
            Log.e(TAG_NEW_USER_FINISHER, "Error with gson or outputting or something", e);
        }
    }
}
