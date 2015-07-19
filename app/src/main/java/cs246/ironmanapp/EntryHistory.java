package cs246.ironmanapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Jared on 7/17/2015.
 */
public class EntryHistory extends Activity {

   public final static String TAG_ENTRY_HISTORY = "Entry History";


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
        Log.i(TAG_ENTRY_HISTORY, "the JSON is: " + json);

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Type listType = new TypeToken<ArrayList<Structs.Entry>>() {
        }.getType();
        Log.i(TAG_ENTRY_HISTORY, "after gson: " + gson.toString());

        entries = gson.fromJson(json, listType);
        Log.i(TAG_ENTRY_HISTORY, "entries for: " + entries.get(0).u_name);

        ArrayList<String> modeList = new ArrayList<String>();
        for (Structs.Entry entry : entries) {
            modeList.add("Date: " + entry.entry_date + "                                                            " + "Mode: " + entry.mode + "                                                               " + "Distance: " + entry.distance);
        }

        ListView modes = (ListView) findViewById(R.id.entries);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, modeList);

        modes.setAdapter(adapter);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.id.simple_list_item_1, modeList);

    }
};
