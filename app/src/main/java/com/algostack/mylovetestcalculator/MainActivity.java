package com.algostack.mylovetestcalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
   private int Splash_time=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

try {


        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent newIntent = new Intent(MainActivity.this, home.class);
                startActivity(newIntent);
                finish();
            }
        }, Splash_time);

    }catch(Exception ex){
    Toast empty = Toast.makeText(getApplicationContext(), "Sorry app not opening", Toast.LENGTH_SHORT);
    empty.setGravity(Gravity.BOTTOM, 0, 200);
    empty.show();
    }

    }
}