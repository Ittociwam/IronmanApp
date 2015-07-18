package cs246.ironmanapp;

import android.test.InstrumentationTestCase;
import android.util.Log;

/**
 * Created by Robbie on 7/13/2015.
 */
public class NewUserTest extends InstrumentationTestCase{
    private Structs.ReturnMessage createNewUser(String username){
        Task t = new Task(NEW_USER_URL, "username=" + username); // two parameters here because it's a post http request

        t.setTaskCompletion(new NewUserFinisher());

        new Thread(t).start();

        return null;

    }
}

class Task implements Runnable {
    public String json;
    private String url;
    private String params;
    private boolean isPost;
    private TaskCompletion taskCompletion;

    public void setTaskCompletion(TaskCompletion t) {
        taskCompletion = t;
    }

    /**
     * Constructor for a GET request takes only a url ex. "http://robbise.no-ip.info/ironman/getContestants.php?semester=FALL2015"
     *
     * @param url
     */
    public Task(String url) {
        this.url = url;
        this.params = "";
        isPost = false;
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
        if (!params.isEmpty()) {
            isPost = true;
        } else
            isPost = false;
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
            Log.i("test", url);
            if (isPost == true) {
                json = urlReader.sendPost(url, params);
            } else {
                json = urlReader.sendGet(url);
            }
        } catch (Exception e) {
            Log.e('test', "Error trying to send a GET request with: ", e);
            e.printStackTrace();
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                taskCompletion.finish(activity, json);

            }
        });
    }
}
