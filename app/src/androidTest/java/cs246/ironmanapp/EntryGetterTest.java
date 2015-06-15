package cs246.ironmanapp;

import android.test.InstrumentationTestCase;

/**
 * Created by Jared on 6/10/2015.
 */
public class EntryGetterTest extends InstrumentationTestCase {

    EntriesGetter e = new EntriesGetter("blah", "blah2");

    public void testNumbers() {

        boolean test = true;

        if(e.getSwim() > 2.4) {
            test = false;
        }
        assertTrue(test);

        if(e.getBike() > 112) {
            test = false;
        }
        assertTrue(test);

        if(e.getRun() > 26) {
            test = false;
        }
        assertTrue(test);

    }

    public void testPercentage() {

        boolean test = true;

        if(e.getSwimP() > 33.4 || e.getBikeP() > 33.4 || e.getRunP() > 33.4) {
            test = false;
        }
        assertTrue(test);
    }

    public void testOverallPercentage() {

        boolean testP = true;

        if(e.getPercentP() > 100) {
            testP = false;
        }
        assertTrue(testP);
    }

    public void testURL() {

        ServiceCaller sCContestants = new ServiceCaller("http://localhost/webengii/webengII/ironman/getContestants.php","semester='SPRING2015'");
        assertTrue(sCContestants.connect());

    }

    public void isJsonTest() {
        ServiceCaller json = new ServiceCaller("http://localhost/webengii/webengII/ironman/getContestants.php","semester='SPRING2015'");

        assertTrue(json.isJson());
    }

}
