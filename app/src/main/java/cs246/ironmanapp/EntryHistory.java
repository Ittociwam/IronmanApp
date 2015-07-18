package cs246.ironmanapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Jared on 7/17/2015.
 */
public class EntryHistory extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context context = getApplicationContext();
        super.onCreate(savedInstanceState);

        ArrayList<Structs.Entry> entries = null;

        setContentView(R.layout.entryhistory_window);

        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width), (int) (height * 0.6));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        String json = preferences.getString("entries", "Nothing found");
        //Log.i(TAG_RANK, "the JSON is: " + json);

        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Structs.Entry>>() {
        }.getType();

        entries = gson.fromJson(json, listType);

        ArrayList<String> modeList = new ArrayList<String>();
        for (Structs.Entry entry : entries) {
            //output += contestant.u_name;

//            if (entry.mode.equals("swim") || entry.mode.equals("bike")) {
//                modeList.add(entry.mode + "      " + entry.distance);
//            }
//            else {
//                modeList.add(entry.mode + "       " + entry.distance);
//            }

            modeList.add(entry.mode + "   " + entry.distance);

        }
//
        ListView modes = (ListView) findViewById(R.id.entries);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, modeList);

        modes.setAdapter(adapter);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.id.simple_list_item_1, modeList);

    }
};
