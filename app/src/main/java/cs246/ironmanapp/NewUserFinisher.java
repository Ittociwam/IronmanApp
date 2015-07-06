package cs246.ironmanapp;

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
    public void finish(String json) {
        Structs.ReturnMessage newUserMessage = null;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Structs.Contestant>>() {
            }.getType();
            newUserMessage = gson.fromJson(json, Structs.ReturnMessage.class);
            String output = "";
            output += newUserMessage.code + " " + newUserMessage.message;

            Log.v(TAG_NEW_USER_FINISHER, output);
        } catch (Exception e) {
            Log.e(TAG_NEW_USER_FINISHER, "Error with gson or outputting or something", e);
        }
    }
}
