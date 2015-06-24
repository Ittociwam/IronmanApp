package cs246.ironmanapp;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robbie on 6/13/2015.
 */
public class ContestantsGetter extends ServiceCaller{

    private static final String CONTESTANT_TAG = " contestant getter tag";

    List<Contestant> contestants;

    public ContestantsGetter(String url, String parameters) {
        super(url, parameters);


        contestants = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            contestants.add(new Contestant(-12));
        }


    }


    public void run() {
        Log.i(CONTESTANT_TAG, "contestants were put into an Arraylist");
    }
}
