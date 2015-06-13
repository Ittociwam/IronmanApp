package cs246.ironmanapp;

import android.test.InstrumentationTestCase;

/**
 * Created by Jared on 6/10/2015.
 */
public class EntryGetterTest extends InstrumentationTestCase {

<<<<<<< HEAD
    EntriesGetter e = new EntriesGetter("someurl", "some parameters");
=======
    EntriesGetter e = new EntriesGetter("url", "");
>>>>>>> 88cb69a5c780fea3612823f0e4794fb890b70bcc
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

}
