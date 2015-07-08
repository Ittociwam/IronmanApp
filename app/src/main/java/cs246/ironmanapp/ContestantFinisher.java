package cs246.ironmanapp;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robbie on 7/1/2015.
 */

public class ContestantFinisher implements  TaskCompletion {
    private static final String TAG_CONTESTANT_FINISHER = "Contestant Finisher";
    @Override
    public void finish(Activity activity, String json) {
        ArrayList<Structs.Contestant> contestants = null;
        try {
            Log.i(TAG_CONTESTANT_FINISHER, "Contestant string to convert: " + json);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Structs.Contestant>>() {
            }.getType();
            contestants = gson.fromJson(json, listType);


            ListAdapter theAdapater = new ArrayAdapter<Structs.Contestant>(activity, android.R.layout.simple_list_item_1, contestants);

            ListView contestantView = (ListView) activity.findViewById(R.id.custom_list);

            contestantView.setAdapter(theAdapater);


//            AdapterContestant adbContestant;
//            adbContestant = new AdapterContestant(activity, 0, contestants);
//
////            ListAdapter theAdapter = new ArrayAdapter<Structs.Contestant>(activity, android.R.layout.simple_list_item_1, contestants)
////
//
//            ListView contestantView = (ListView) activity.findViewById(R.id.custom_list);
//
//          // ListView v = (ListView) adbContestant.getView(1,contestantView, contestantView );
//
//            contestantView.setAdapter(adbContestant);
//            Log.i(TAG_CONTESTANT_FINISHER, contestantView.toString());
//
//            Log.i(TAG_CONTESTANT_FINISHER, adbContestant.toString());
//
  //          ListView contestantView;
            List<String> names = new ArrayList<String>();

            String output = "";
            for (Structs.Contestant contestant : contestants) {

                names.add(contestant.u_name);

                output += contestant.u_name + " " + contestant.percentage + "%\n";
                Toast.makeText(activity.getApplicationContext(), contestant.u_name, Toast.LENGTH_LONG ).show();

            }

             contestantView = (ListView) activity.findViewById(R.id.custom_list);

            ArrayAdapter namesAdapter = new ArrayAdapter<String>(activity,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    names);

            contestantView.setAdapter(namesAdapter);


            Log.v(TAG_CONTESTANT_FINISHER, "The output after conversion to a list: " + output);
        } catch (Exception e) {
            Log.e(TAG_CONTESTANT_FINISHER, "Error with gson or outputting or something", e);
        }

    }
}
