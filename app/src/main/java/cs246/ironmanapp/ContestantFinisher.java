package cs246.ironmanapp;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Robbie on 7/1/2015.
 */

public class ContestantFinisher implements  TaskCompletion {
    private static final String TAG_CONTESTANT_FINISHER = "Contestant Finisher";
    @Override
    public List<Structs.Contestant> finish(Activity activity, String json) {
        List<Structs.Contestant> contestants = null;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Structs.Contestant>>() {
            }.getType();
            contestants = gson.fromJson(json, listType);

            ListAdapter theAdapater = new ArrayAdapter<Structs.Contestant>(activity, android.R.layout.simple_list_item_1, contestants);

            ListView contestantView = (ListView) activity.findViewById(R.id.custom_list);

            contestantView.setAdapter(theAdapater);

            String output = "";
            for (Structs.Contestant contestant : contestants) {
                output += contestant.u_name + " " + contestant.percentage + "%\n";

            }
            Log.v(TAG_CONTESTANT_FINISHER, output);
        } catch (Exception e) {
            Log.e(TAG_CONTESTANT_FINISHER, "Error with gson or outputting or something", e);
        }

        return contestants;
    }
}
