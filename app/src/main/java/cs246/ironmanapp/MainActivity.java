package cs246.ironmanapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;



public class MainActivity extends ActionBarActivity {
    int progressStatus = 0;
    ProgressBar gprogress;
    ProgressBar bprogress;
    ProgressBar pprogress;
    //EntriesGetter e;
    private final String USER_AGENT = "Mozilla/5.0";

    private final String TAG_MAIN_ACTIVITY = "Main Activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlDriver();

        //e.run();

    }

    public void testProgress(View view) {

        gprogress = (ProgressBar) findViewById(R.id.progressBar2);
        bprogress = (ProgressBar) findViewById(R.id.progressBar);
        pprogress = (ProgressBar) findViewById(R.id.progressBar3);
        progressStatus += 5;
        progressStatus = progressStatus % 105;
        gprogress.setProgress(progressStatus);

    }

    public void urlDriver() {



    System.out.println("about to do the  run thing");
        System.out.println("Here in urlDriver()");

        URLReader urlReader = new URLReader();

        //getContestants
        String semester = "FALL2015";
        String json = "";

        try {
            json = urlReader.sendGet("http://robbise.no-ip.info/ironman?semester=" + semester);

        } catch (Exception e) {
            System.out.println("an error of sorts: ");
            e.toString();
            //e.printStackTrace();

        }

        System.out.print("HELLO!!!!!!!!!!!!!!!!!!!!! HERE IS SOME JSON FOR YOUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");

        System.out.print(json);

        Gson gson = new Gson();
        Structs.Contestants contestants = gson.fromJson(json, Structs.Contestants.class);
        

        for (Structs.Contestant contestant : contestants.contestantList) {
            System.out.println(contestant.u_name + " " + contestant.percentage + "%");

        }

        Log.v(TAG_MAIN_ACTIVITY, "About to do this runnable thing");

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.v(TAG_MAIN_ACTIVITY, "In runnable inline class");
                URLReader urlReader = new URLReader();

                //getContestants
                String semester = "FALL2015";
                String myURL = "http://robbise.no-ip.info/ironman/getContestants.php?semester=" + semester;
                String json = "";

                try {
                    Log.i(TAG_MAIN_ACTIVITY, "The URL is: " + myURL);
                    json = urlReader.sendGet(myURL);
                    Log.i(TAG_MAIN_ACTIVITY, "Returned Json: " + json);

                } catch (Exception e) {
                    Log.e(TAG_MAIN_ACTIVITY, "Problem sending Get", e);
                }
                try {

                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Structs.Contestant>>(){}.getType();
                    //List<Structs.Contestant> contestList = (List<Structs.Contestant>) gson.fromJson(json, Structs.Contestant.class);
                    //Structs.Contestant[] contestList = gson.fromJson(json, Structs.Contestant[].class);
                    //Structs.Contestants contestants = gson.fromJson(json, Structs.Contestants.class);
                    List<Structs.Contestant> contestList = gson.fromJson(json, listType);
                    String output = "";
                    for (Structs.Contestant contestant : contestList) {
                        output += contestant.u_name + " " + contestant.percentage + "%\n";

                    }
                    Log.v(TAG_MAIN_ACTIVITY, output);
                }
                catch (Exception e){
                    Log.e(TAG_MAIN_ACTIVITY, "Error with gson or outputting or something", e);
                }

            }
        };
        new Thread(runnable).start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
