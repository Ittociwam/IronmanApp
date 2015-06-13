package cs246.ironmanapp;

/**
 * Created by John on 6/10/15.
 */
public class ServiceCaller implements Runnable {

    String url;
    String parameters;

    public ServiceCaller(String u, String p){
        this.url = u;
        this.parameters = p;

    }

    public void run() {


    }

    public boolean connect() {
        // connect to url
        return false;
    }

    public boolean isJson() {
        return false;
    }

}
