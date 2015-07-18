package cs246.ironmanapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;

import cs246.ironmanapp.R;

/**
 * Created by John on 7/17/15.
 */
public class UserName extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Context context = getApplicationContext();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.username_window);

        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width), (int) (height * 0.6));
    }

}
