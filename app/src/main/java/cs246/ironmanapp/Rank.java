package cs246.ironmanapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 7/8/15.
 */
public class Rank extends Activity {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        Context context = getApplicationContext();
        super.onCreate(savedInstanceState);

        ListView listView;
        List<Structs.Contestant> contestants = null;


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
        Type listType = new TypeToken<List<Structs.Contestant>>() {
        }.getType();
        contestants = gson.fromJson(json, listType);

        //ArrayList<Structs.Contestant> contestants = new ArrayList<>();
        ArrayList<String> userList = new ArrayList<String>();
        for (Structs.Contestant contestant : contestants) {
            //output += contestant.u_name;
            userList.add(contestant.u_name);

        }




        listView  = (ListView) findViewById(R.id.rankings);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, userList);

        listView.setAdapter(adapter);
        //Button btnBack = (Button) findViewById(R.id.button);

        //btnBack.setOnClickListener(new View.OnClickListener() {


          //  public void onClick(View v) {
            //    Intent intent = new Intent(Rank.this, MainActivity.class);
                //EditText number = (EditText) findViewById(R.id.number);
                //String message = "Your favorite number is " + number.getText().toString();
                //intent.putExtra(EXTRA_MESSAGE, message);
              //  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               // startActivity(intent);

            //}
        //});







    }
}
