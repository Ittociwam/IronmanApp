package cs246.ironmanapp;

import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by John on 6/22/15.
 */
public class URLReader implements Runnable {
    private final String USER_AGENT = "Mozilla/5.0";
    private String url;
    private String params;
    private static final String TAG_URL_READER = "Url Reader";

    /**
     * Constructor for a GET request takes only a url ex. "http://robbise.no-ip.info/ironman/getContestants.php?semester=FALL2015"
     * @param url
     */
    public URLReader(String url) {
        this.url = url;
        this.params = "";
    }

    /**
     * CONstructor for a POST request takes a url string and parameters
     * ex. url = "http://robbise.no-ip.info/ironman/newUser.php"
     * params = username=batman
     * @param url
     * @param params
     */
    public URLReader(String url, String params) {
        this.url = url;
        this.params = params;
    }


    @Override
    public void run() {
        if (this.params.isEmpty()) {
            try {
                sendGet(this.url);
            } catch (Exception e) {
                Log.e(TAG_URL_READER, "Error trying to send a GET request with: " + url, e);
                e.printStackTrace();
            }
        } else {
            if (this.url.contains("?")) {
                Log.w(TAG_URL_READER, "Your url contains a '?' you may be trying to send a POST request with a GET url string.");
            }
            try {
               sendPost(this.url, this.params);
            } catch (Exception e) {
                Log.e(TAG_URL_READER, "Error trying to send a POST request with: " + this.url + " as url and: " + this.params + " as params.", e);
            }
        }
    }


    public String sendGet(String url) throws Exception {


        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        Log.i(TAG_URL_READER, "\nSending 'GET' request to URL : " + url);
        Log.i(TAG_URL_READER, "Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //return result
        String jsonResponse = response.toString();
        if (isJSONValid(jsonResponse))
            return response.toString();
        else
            Log.e(TAG_URL_READER, jsonResponse + " is not valid JSON");
        return "{}";

    }

    // HTTP POST request
    public String sendPost(String url, String urlParameters) throws Exception {

        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");


        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        Log.i(TAG_URL_READER, "\nSending 'POST' request to URL : " + url);
        Log.i(TAG_URL_READER, "Post parameters : " + urlParameters);
        Log.i(TAG_URL_READER, "Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //return result
        String jsonResponse = response.toString();
        if (isJSONValid(jsonResponse))
            return response.toString();
        else
            Log.e(TAG_URL_READER, jsonResponse + " is not valid JSON");
        return "{}";

    }

    public boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                Log.e(TAG_URL_READER, test + "is not json");
                return false;
            }
        }
        return true;
    }
}
