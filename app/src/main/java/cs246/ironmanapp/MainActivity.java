package cs246.ironmanapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
    private static final String GET_CONTESTANTS_URL = "http://robbise.no-ip.info/ironman/getContestants.php?";
    private static final String GET_ENTRIES_URL = "http://robbise.no-ip.info/ironman/getEntries.php?";
    private android.os.Handler handler;
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
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
       // System.out.println(message);



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView add = (ImageView) findViewById(R.id.imageView3);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Pop.class));
            }
        });

        handler = new android.os.Handler();

        getContestants();
        getEntries();


        MainActivity.context = MainActivity.this.getApplicationContext();

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
        Task t = new Task(GET_CONTESTANTS_URL + "semester=" + getSelectedSemester());

        t.setTaskCompletion(new ContestantFinisher());

        new Thread(t).start();
    }

    public void getEntries() {
        Task t = new Task(GET_ENTRIES_URL + "semester=" + getSelectedSemester() + "&id=" + getContestantID());

        t.setTaskCompletion(new EntryFinisher());

        new Thread(t).start();
    }


    class Task implements Runnable {
        public String json;
        private String url;
        private String params;
        private boolean isPost;
        private TaskCompletion taskCompletion;

        public void setTaskCompletion(TaskCompletion t) {
            taskCompletion = t;
        }

        /**
         * Constructor for a GET request takes only a url ex. "http://robbise.no-ip.info/ironman/getContestants.php?semester=FALL2015"
         *
         * @param url
         */
        public Task(String url) {
            this.url = url;
            this.params = "";
            isPost = false;

        }

        /**
         * Constructor for a POST request takes a url string and parameters
         * ex. url = "http://robbise.no-ip.info/ironman/newUser.php"
         * params = username=batman
         *
         * @param url
         * @param params
         */
        public Task(String url, String params) {
            this.url = url;
            this.params = params;
            if (!params.isEmpty()) {
                isPost = true;
            } else
                isPost = false;
        }


        /**
         * This is the overridden run method for Task which is a runnable class. It creates a
         * new urlReader with the url that was passed in and then sends a get request and assigns the
         * returned data to a static json object.
         * THIS METHOD IS UNDER CONSTRUCTION AND WILL NOT LOOK LIKE THIS LATER
         */

        @Override
        public void run() {
            URLReader urlReader = new URLReader(url, params);
            try {
                Log.i(TAG_MAIN_ACTIVITY, url);
                if (isPost == true) {
                    json = urlReader.sendPost(url, params);
                } else {
                    json = urlReader.sendGet(url);
                }
            } catch (Exception e) {
                Log.e(TAG_MAIN_ACTIVITY, "Error trying to send a GET request with: ", e);
                e.printStackTrace();
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    taskCompletion.finish(json);
                }
            });
        }
    }


    /**
     * this method will need to get the semester from a select
     * drop down menu on activity_main.xml and return it.
     *
     * @return a string that is the selected semester
     */
    private String getSelectedSemester() {

        return "FALL2015"; // a default value for testing
    }

    /**
     * this method will need to get the ID from a locally stored ID number
     * and return it.
     *
     * @return a string that is the ID of the current user
     */
    private String getContestantID() {

        return "55943eb52b381"; // id for Scooby Doo
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
