package cs246.ironmanapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robbie on 6/13/2015.
 */
public class ContestantsGetter extends ServiceCaller{

    List<Contestant> contestants;

    public ContestantsGetter(String url, String parameters) {
        super(url, parameters);

        contestants = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            contestants.add(new Contestant(-12));
        }
    }


}
