package cs246.ironmanapp;

import android.test.InstrumentationTestCase;

import com.google.gson.Gson;

import junit.framework.Assert;

import cs246.ironmanapp.Structs;

/**
 * Created by Robbie on 6/23/2015.
 */
public class URLReaderTest  extends InstrumentationTestCase {
    public void testGetRequest(){
        URLReader urlReader = new URLReader();

        //getContestants
        String semester = "FALL2015";
        String json = "";

        try {
            json = urlReader.sendGet("http://robbise.no-ip.info/ironman?semester=" + semester);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        Structs.Contestants contestants = gson.fromJson(json, Structs.Contestants.class);

        assertTrue(contestants.contestantList.contains(Contestant.class));

        for(Structs.Contestant contestant : contestants.contestantList){
            System.out.println(contestant.u_name + " " + contestant.percentage + "%");

        }

    }
}
