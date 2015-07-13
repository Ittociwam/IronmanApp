package cs246.ironmanapp;

import android.app.Activity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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


            ListView contestantView;
            List<String> names = new ArrayList<String>();
            String output = "";
            LinearLayout rankings = (LinearLayout)activity.findViewById(R.id.rankings);

            for (Structs.Contestant contestant : contestants) {

                //names.add(contestant.u_name);

                LinearLayout contestantDisplay = (LinearLayout) activity.findViewById(R.id.contestantDisplay);

               // View contestantDisplay = activity.getLayoutInflater().inflate(R.layout.contestantdisplay, null);

                TextView contestantName = (TextView) activity.getLayoutInflater().inflate(R.layout.contestantdisplay, null);

                TextView percentage = (TextView) activity.getLayoutInflater().inflate(R.layout.contestantdisplay, null);

                contestantName.setText(contestant.u_name);
                percentage.setText((int) contestant.percentage);

                contestantDisplay.addView(contestantName);
                contestantDisplay.addView(percentage);

                rankings.addView(contestantDisplay);


               // LayoutInflater inflater = LayoutInflater.from(activity.getBaseContext());

               // output += contestant.u_name + " " + contestant.percentage + "%\n";
                Toast.makeText(activity.getApplicationContext(), contestant.u_name, Toast.LENGTH_LONG ).show();

            }

             //contestantView = (ListView) activity.findViewById(R.id.custom_list);




            Log.v(TAG_CONTESTANT_FINISHER, "The output after conversion to a list: " + output);
        } catch (Exception e) {
            Log.e(TAG_CONTESTANT_FINISHER, "Error with gson or outputting or something", e);
        }

    }

}
