package cs246.ironmanapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

//android:id="@+id/progressBar"

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
        lv1.setAdapter(new CustomListAdapter(this, image_details));
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int POSITION, long id) {
                Object o = lv1.getItemAtPosition(position);
                Structs.Contestant listContestants = (Structs.Contestant) o;
                Toast.makeText(MainActivity.this, "Selected :" + " " + listContestants, Toast.LENGTH_LONG).show();
            }
        });


        private ArrayList getListData() {
            ArrayList<Structs.Contestant> results = new ArrayList<Structs.Contestant>();
            Structs.Contestant arrayContestants = new Structs.Contestant();
            //newsData.setHeadline("Dance of Democracy");
            //newsData.setReporterName("Pankaj Gupta");
            //newsData.setDate("May 26, 2013, 13:35");
            //results.add(newsData);
            //lView = (ListView) findViewById(R.id.rankings);
            //adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, contestants);

        }
    }


    public static class CustomListAdapter extends BaseAdapter {
        private ArrayList<Structs.Contestant> listData;
        private LayoutInflater layoutInflater;

        public CustomListAdapter(Context aContext, ArrayList<Structs.Contestant> listData) {
            this.listData = listData;
            layoutInflater = LayoutInflater.from(aContext);
        }

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public Object getItem(int position) {
            return listData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.list_row_layout, null);
                holder = new ViewHolder();
                holder.u_name = (TextView) convertView.findViewById(R.id.title);
                holder.percentage = (TextView) convertView.findViewById(R.id.u_name);
                //holder.reportedDateView = (TextView) convertView.findViewById(R.id.date);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.nameView.setText(listData.get(position).u_name);
            holder.percentageView.setText(listData.get(position).percentage);
            //holder.reportedDateView.setText(listData.get(position).getDate());
            return convertView;
        }

        static class ViewHolder {
            TextView nameView;
            TextView percentageView;

        }
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

        new Thread(new Task(GET_CONTESTANTS_URL + getSelectedSemester())).start();
    }




        class Task implements Runnable {
            public String json;
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
         *
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
     *
     * @param json
     */
    public static List populateContestantsList(String json) {
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

        }catch (Exception e) {
            Log.e(TAG_MAIN_ACTIVITY, "Error trying to send a GET request with: ", e);
            e.printStackTrace();
        }
        new Thread(runnable).start();


            Log.v(TAG_MAIN_ACTIVITY, output);

        return contestants;
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
