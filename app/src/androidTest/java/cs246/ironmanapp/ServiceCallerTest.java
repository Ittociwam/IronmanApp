package cs246.ironmanapp;

import android.test.InstrumentationTestCase;

/**
 * Created by Jared on 6/10/2015.
 */

public class ServiceCallerTest extends InstrumentationTestCase {


    public void testURL(){

        ServiceCaller sCContestants = new ServiceCaller("http://localhost/webengii/webengII/ironman/getContestants.php","semester='SPRING2015'");
        assertTrue(sCContestants.connect());

    }

    public void isJson() {
        //boolean json = true;
        ServiceCaller json = new ServiceCaller("http://localhost/webengii/webengII/ironman/getContestants.php","semester='SPRING2015'");

        assertTrue(json.isJson());
    }

}
