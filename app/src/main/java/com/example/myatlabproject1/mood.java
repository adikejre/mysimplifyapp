package com.example.myatlabproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class mood extends AppCompatActivity {

    private ImageButton sedbtn;
    private ImageButton hppybtn;
    private ImageButton neutralbtn;
    private FirebaseAuth auth;
    private Button track;
    private static final String TAG = "mood";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);
        auth=FirebaseAuth.getInstance();
        sedbtn=findViewById(R.id.sedbtn);
        hppybtn=findViewById(R.id.happybtn);
        neutralbtn=findViewById(R.id.neutralbtn);
        track=findViewById(R.id.score);
        String userid= auth.getCurrentUser().getUid();


//        CollectionReference docRef = (CollectionReference) FirebaseFirestore.getInstance()
//                .collection("moods")
//                .whereEqualTo("userid",auth.getCurrentUser().getUid());

        hppybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






                Map<String,Object> map=new HashMap<>();


                //map.put("happy", FieldValue.increment(1));

                map.put("mood","happy");
                map.put("userid",userid);
                map.put("created",new Timestamp(new Date()));
                FirebaseFirestore.getInstance().collection("moods")
                        .add(map);

            }
        });
        sedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String,Object> map=new HashMap<>();
                map.put("mood","sad");
                map.put("userid",userid);
                map.put("created",new Timestamp(new Date()));
                FirebaseFirestore.getInstance().collection("moods")
                        .add(map);

            }
        });
        neutralbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> map=new HashMap<>();
                map.put("mood","neutral");
                map.put("userid",userid);
                map.put("created",new Timestamp(new Date()));
                FirebaseFirestore.getInstance().collection("moods")
                        .add(map);
            }
        });

        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Query query= FirebaseFirestore.getInstance()
//                        .collection("moods")
//                        .whereEqualTo("userid",auth.getCurrentUser().getUid())
//                        .whereEqualTo("mood","happy");
//            QuerySnapshot sp=


                FirebaseFirestore.getInstance()
                        .collection("moods")
                        .whereEqualTo("userid",auth.getCurrentUser().getUid())
                        .whereEqualTo("mood","happy").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int aa=task.getResult().size();
                        Log.d(TAG, "onComplete:----------------------->>>>>>>>>>>>>>>>>>>>> "+aa);
                    }
                });

            }
        });


    }
}

