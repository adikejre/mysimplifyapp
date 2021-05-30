package com.example.myatlabproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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

        FloatingActionButton fab1=findViewById(R.id.myfabrefresh);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.getInstance()
                        .collection("moods")
                        .whereEqualTo("userid", FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .whereEqualTo("mood","happy").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        haptxt.setText(Integer.toString(task.getResult().size()));
//                        Log.d(TAG, "onComplete:----------------------->>>>>>>>>>>>>>>>>>>>> "+happycount);
                    }
                });

                FirebaseFirestore.getInstance()
                        .collection("moods")
                        .whereEqualTo("userid", FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .whereEqualTo("mood","sad").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        sedtxt.setText(Integer.toString(task.getResult().size()));
//                        Log.d(TAG, "onComplete:----------------------->>>>>>>>>>>>>>>>>>>>> "+happycount);
                    }
                });


                FirebaseFirestore.getInstance()
                        .collection("moods")
                        .whereEqualTo("userid", FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .whereEqualTo("mood","neutral").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ntrltxt.setText(Integer.toString(task.getResult().size()));
//                        Log.d(TAG, "onComplete:----------------------->>>>>>>>>>>>>>>>>>>>> "+happycount);
                    }
                });





            }





        });



    }
}