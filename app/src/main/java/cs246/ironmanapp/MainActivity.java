package cs246.ironmanapp;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Struct;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Handler;


public class MainActivity extends ActionBarActivity {
    private static final String GET_CONTESTANTS_URL = "http://robbise.no-ip.info/ironman/getContestants.php?semester=";
    private static final String GET_EENTRIES_URL = "http://robbise.no-ip.info/ironman/getEntries.php";
    public static List<Structs.Contestant> contestants;
    public List<Structs.Entry> entries;
    private android.os.Handler handler;
    public static String json;
    public ListView lView;
    private static Context context;
    public ArrayAdapter<String> adapter;

    int progressStatus = 0;
    ProgressBar gprogress;
    ProgressBar bprogress;
    ProgressBar pprogress;
    //EntriesGetter e;
    private final String USER_AGENT = "Mozilla/5.0";
    private static final String TAG_MAIN_ACTIVITY = "Main Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new android.os.Handler();
        getContestants();
        MainActivity.context = MainActivity.this.getApplicationContext();
        //lView = (ListView) findViewById(R.id.rankings);
        adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, contestants);


    }

    public void testProgress(View view) {

        gprogress = (ProgressBar) findViewById(R.id.progressBar2);
        bprogress = (ProgressBar) findViewById(R.id.progressBar);
        pprogress = (ProgressBar) findViewById(R.id.progressBar3);
        progressStatus += 5;
        progressStatus = progressStatus % 105;
        gprogress.setProgress(progressStatus);

    }

    public void getContestants() {

        new Thread(new Task(GET_CONTESTANTS_URL + getSelectedSemester())).start();
    }


        class Task implements Runnable {
            private String url;
            private String params;
            /**
             * Constructor for a GET request takes only a url ex. "http://robbise.no-ip.info/ironman/getContestants.php?semester=FALL2015"
             * @param url
             */
            public Task(String url) {
                this.url = url;
                this.params = "";
            }

            /**
             * CONstructor for a POST request takes a url string and parameters
             * ex. url = "http://robbise.no-ip.info/ironman/newUser.php"
             * params = username=batman
             * @param url
             * @param params
             */
            public Task(String url, String params) {
                this.url = url;
                this.params = params;
            }
            @Override
            public void run() {
                URLReader urlReader = new URLReader(url, params);
                try {
                    //String json = "";
                    json = urlReader.sendGet(GET_CONTESTANTS_URL + getSelectedSemester());
                } catch (Exception e) {
                    Log.e(TAG_MAIN_ACTIVITY, "Error trying to send a GET request with: ", e);
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        populateContestantsList(json);
                    }
                });
            }
        }

    /**
     * Takes a json string and turns it into a
     * @param json
     */
    public static void populateContestantsList(String json) {
        List<Structs.Contestant> contestList = null;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Structs.Contestant>>() {
            }.getType();
            contestants = gson.fromJson(json, listType);
            String output = "";
            for (Structs.Contestant contestant : contestants) {
                output += contestant.u_name + " " + contestant.percentage + "%\n";

            }
            Log.v(TAG_MAIN_ACTIVITY, output);
        } catch (Exception e) {
            Log.e(TAG_MAIN_ACTIVITY, "Error with gson or outputting or something", e);
        }

    }

    private String getSelectedSemester() {
        // this method will need to get the semester from a select drop down menu on activity_main.xml
        // and return it.
        return "FALL2015";
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
