package cs246.ironmanapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by John on 7/6/15.
 */
public class Pop extends Activity {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.addwindow);

        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width), (int) (height * 0.6));

        Button btnBack = (Button) findViewById(R.id.button);

        btnBack.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                Intent intent = new Intent(Pop.this, MainActivity.class);
                EditText number = (EditText) findViewById(R.id.miles);
                String message = "Your favorite number is " + number.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, message);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

    }
}
