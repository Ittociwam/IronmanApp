package cs246.ironmanapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by John on 7/18/15.
 */
public class ErrorMessage extends Activity {
    private static final String TAG_USERNAME = "User Name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG_USERNAME, "INSIDE THE ERROR MESSAGE");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.error_window);
        Intent intent = getIntent();
        String message = intent.getStringExtra(UserName.EXTRA_MESSAGE);
        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width), (int) (height * 0.9));
    }
}
