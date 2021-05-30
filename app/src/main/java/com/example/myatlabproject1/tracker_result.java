package com.example.myatlabproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class tracker_result extends AppCompatActivity {

    private TextView haptxt;
    private TextView ntrltxt;
    private TextView sedtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker_result);

        Intent mIntent = getIntent();
        int happys=mIntent.getIntExtra("hscore",0);
        int sads=mIntent.getIntExtra("sscore",0);
        int ntrls=mIntent.getIntExtra("nscore",0);

        haptxt=(TextView) findViewById(R.id.htxt);
        ntrltxt=(TextView) findViewById(R.id.ntxt);
        sedtxt=(TextView) findViewById(R.id.stxt);

        haptxt.setText(Integer.toString(happys));
        ntrltxt.setText(Integer.toString(ntrls));
        sedtxt.setText(Integer.toString(sads));



    }
}