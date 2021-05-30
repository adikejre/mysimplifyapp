package com.example.myatlabproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity  {
    private Button lgout;
    private FirebaseAuth auth;
   // private Button mood;
   // private Button gotonotes;
    private static final String TAG = "HomePage";

     RecyclerView myrecyclerView;
    NotesRecyclerAdapter notesRecyclerAdapter;
    public CardView remind, mood, grocerylst, gotonotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        remind= (CardView) findViewById(R.id.c1);
        mood= (CardView) findViewById(R.id.c2);
        grocerylst= (CardView) findViewById(R.id.c3);
        gotonotes= (CardView) findViewById(R.id.c4);



        lgout=findViewById(R.id.logout);
        //mood=findViewById(R.id.logout);
        //gotonotes=findViewById(R.id.button2);

       myrecyclerView=(RecyclerView)findViewById(R.id.recycler_View);

        auth=FirebaseAuth.getInstance();
        lgout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                if(notesRecyclerAdapter!=null){
                    notesRecyclerAdapter.stopListening();
                }
                Toast.makeText(HomePage.this, "Logged Out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomePage.this,MainActivity.class));
            }
        });
        mood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(HomePage.this,mood.class));



//                FirebaseFirestore.getInstance().collection("notes")
//                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                            @Override
//                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
//                                if(e !=null){
//                                    Log.d(TAG, "onEvent: ",e);
//                                    return;
//                                }
//                                if(value!=null){
//                                    Log.d(TAG, "onEvent: --------------");
//                                    List<DocumentSnapshot> snapshotList=value.getDocuments();
//                                    for(DocumentSnapshot snapshot:snapshotList){
//                                        Log.d(TAG, "onEvent: " +snapshot.getData());
//                                    }
//                                }
//                                else{
//                                    Log.d(TAG, "onEvent: snap was null");
//                                }
//                            }
//                        });


            }
        });
        gotonotes.setOnClickListener(new View.OnClickListener() {
            //initRecyclerView()
            public void onClick(View v) {
                //initRecyclerView(auth.getCurrentUser());
                startActivity(new Intent(HomePage.this,Notesapp.class));


            }
        });


        remind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomePage.this,SetReminder.class);
                startActivity(i);
            }
        });



        grocerylst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomePage.this,GroceryList.class);
                startActivity(i);
            }
        });



    }

//    public void initRecyclerView(FirebaseUser user){
//        Query query= FirebaseFirestore.getInstance()
//                .collection("notes")
//                .whereEqualTo("userid",user.getUid());
//
//        FirestoreRecyclerOptions<Notee> options=new FirestoreRecyclerOptions.Builder<Notee>()
//                .setQuery(query,Notee.class).build();
//
//        notesRecyclerAdapter=new NotesRecyclerAdapter(options,HomePage.this);
//        myrecyclerView.setLayoutManager(new LinearLayoutManager(HomePage.this));
//        myrecyclerView.setAdapter(notesRecyclerAdapter);
//        notesRecyclerAdapter.startListening();
//    }



}