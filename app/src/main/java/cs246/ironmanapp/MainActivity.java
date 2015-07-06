package cs246.ironmanapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private static final String GET_CONTESTANTS_URL = "http://robbise.no-ip.info/ironman/getContestants.php?semester=";
    private static final String GET_EENTRIES_URL = "http://robbise.no-ip.info/ironman/getEntries.php";
    public static List<Structs.Contestant> contestants;
    public List<Structs.Entry> entries;
    private android.os.Handler handler;
    //public static String json;
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


        ArrayList image_details = getListData();
        final ListView lv1 = (ListView) findViewById(R.id.custom_list);
        lv1.setAdapter(new contestantListAdapter.CustomListAdapter(this, image_details));
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                position = 0;
                Object o = lv1.getItemAtPosition(position);
                Structs.Contestant listContestants = (Structs.Contestant) o;
                Toast.makeText(MainActivity.this, "Selected :" + " " + listContestants, Toast.LENGTH_LONG).show();
            }
        });
    }


    private ArrayList getListData() {
        ArrayList<Structs.Contestant> results = new ArrayList<Structs.Contestant>();
        Structs.Contestant arrayContestants = new Structs.Contestant();
        //newsData.setHeadline("Dance of Democracy");
        arrayContestants.u_name = "Jared Mackie";

        arrayContestants.percentage = 20;
        //newsData.setDate("May 26, 2013, 13:35");
        results.add(arrayContestants);

        return results;
    }


    public void testProgress(View view) {

        gprogress = (ProgressBar) findViewById(R.id.progressBar2);
        bprogress = (ProgressBar) findViewById(R.id.progressBar);
        pprogress = (ProgressBar) findViewById(R.id.progressBar3);
        progressStatus += 5;
        progressStatus = progressStatus % 105;
        gprogress.setProgress(progressStatus);

    }

    /**
     * This method simply starts a Task thread with the getcontestants url
     */
    public void getContestants() {

        new Thread(new Task(GET_CONTESTANTS_URL + getSelectedSemester())).start();
    }


    class Task implements Runnable {
        public String json;
        private String url;
        private String params;

        /**
         * Constructor for a GET request takes only a url ex. "http://robbise.no-ip.info/ironman/getContestants.php?semester=FALL2015"
         *
         * @param url
         */
        public Task(String url) {
            this.url = url;
            this.params = "";
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


        /**
         * Takes a json string and turns it into a list so that we can then place it in a list view
         *
         * @param json - a json string
         */
        public void populateContestantsList(String json) {
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


