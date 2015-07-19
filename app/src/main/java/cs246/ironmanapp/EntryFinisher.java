package cs246.ironmanapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robbie on 7/1/2015.
 */
public class EntryFinisher implements TaskCompletion {
    private static final String TAG_ENTRY_FINISHER = "Entry Finisher";

    @Override
    public void finish(Activity activity, String json) {

        ArrayList<Structs.Entry> entries = null;

        try {
            SharedPreferences sharePreferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
            SharedPreferences.Editor editor = sharePreferences.edit();
            editor.putString("entries", json);
            editor.commit();

            String output = "";

            //This for loop will be replaced by what sends the contestants list to the main activity
//            for (Structs.Entry entry : entries) {
//                output += entry.mode + " " + entry.distance + "%\n";
//
//            }

            Log.v(TAG_ENTRY_FINISHER, output);
        } catch (Exception e) {
            Log.e(TAG_ENTRY_FINISHER, "Error with gson or outputting or something", e);

        }
    }
}
