package cs246.ironmanapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by John on 7/8/15.
 */
public class Rank extends Activity {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    private int textViewResourceId;



    @Override
    protected void onCreate (Bundle savedInstanceState) {
        Context context = getApplicationContext();
        super.onCreate(savedInstanceState);

        ListView listView;
        ArrayList<Structs.Contestant> contestants = null;


        setContentView(R.layout.rankwindow);

        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width), (int) (height * 0.6));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        String json = preferences.getString("contestants", "Nothing found");
        Log.i("tag Rank", "the JSON is: " + json);

        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Structs.Contestant>>() {
        }.getType();

        contestants = gson.fromJson(json, listType);

        //ArrayList<String> theContestants = getContestants();

        //String stringPercentage = "";
        //ArrayList<String> listContestant = new ArrayList<String>();

//        for (Structs.Contestant contestant : contestants) {
//            //output += contestant.u_name;
//            listContestant.add(contestant.u_name);
//            stringPercentage += String.valueOf(contestant.percentage);
//            listContestant.add(stringPercentage);
//            //listContestant = userList;
//            //userList.add(contestant.percentage);
//        }


//        ArrayList<Structs.Contestant> listContestant = new ArrayList<Structs.Contestant>();
//
//
//        for (Structs.Contestant contestant: contestants) {
//
//            for (: listContestant) {
//                String stringPercentage = "";
//                listContestant.add(Structs.Contestant.getU_name());
//            }
//
//        }


        //ArrayList<Structs.Contestant> contestants = getContestants();

//        public ArrayList<Structs.Contestant> getContestants(){
//
//            ArrayList<Structs.Contestant> listContestant = new ArrayList<Structs.Contestant>();
//            //ArrayList
//
//            ArrayList<String> userList = new ArrayList<String>();
//            for (Structs.Contestant contestant : contestants) {
//                //output += contestant.u_name;
//                userList.add(contestant.u_name);
//                //listContestant = userList;
//                //userList.add(contestant.percentage);
//            }
//
//            ArrayList<String> percentageList = new ArrayList<String>();
//            String stringPercentage = "";
//            for (Structs.Contestant contestant : contestants) {
//                //output += contestant.u_name;
//                stringPercentage += String.valueOf(contestant.percentage);
//                percentageList.add(stringPercentage);
//                //userList.add(contestant.percentage);
//            }
//
//        }

        //contestants = getContestants();

        listView  = (ListView) findViewById(R.id.rankings);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,listView, userList);

        listView.setAdapter(new AdapterContestant(this, R.id.rankings, contestants));


        //Button btnBack = (Button) findViewById(R.id.button);

        //btnBack.setOnClickListener(new View.OnClickListener() {


    }

    //public ArrayList<Structs.Contestant> getContestants(){


//        ArrayList<Structs.Contestant> arrayContestant = new ArrayList<Structs.Contestant>();
//        SharedPreferences conPreferences = PreferenceManager.getDefaultSharedPreferences(Context);
//
//        String contJson = conPreferences.getString("contestants", "Nothing found");
//        //Log.i("tag Rank", "the JSON is: " + json);
//
//        Gson contGson = new Gson();
//        Type listCont = new TypeToken<ArrayList<Structs.Contestant>>() {
//        }.getType();
//        //
//        arrayContestant = contGson.fromJson(contJson, listCont);
//
//        return arrayContestant;
   // }


}
