package cs246.ironmanapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;


public class MainActivity extends ActionBarActivity {
    private final String USER_AGENT = "Mozilla/5.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
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
