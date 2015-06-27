package cs246.ironmanapp;

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

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by John on 6/22/15.
 */



public class URLReader {
    private final String USER_AGENT = "Mozilla/5.0";

    private static final String TAG_URL_READER = "Url Reader";

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
        if(isJSONValid(jsonResponse))
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
        if(isJSONValid(jsonResponse))
            return response.toString();
        else
            Log.e(TAG_URL_READER, jsonResponse + " is not valid JSON");
        return "{}";

    }

    public boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
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
