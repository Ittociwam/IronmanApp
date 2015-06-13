package cs246.ironmanapp;

import android.test.InstrumentationTestCase;

import java.util.List;

/**
 * Created by Robbie on 6/13/2015.
 */
public class ContestantsGetterTest extends InstrumentationTestCase {




    public void validatePercentages(List<Contestant> contestants){
        ContestantsGetter cG = new ContestantsGetter("url", "params");
        for (Contestant c : contestants)
        {
            assertTrue(c.percentage < 100 && c.percentage > 0);
        }
    }
}
