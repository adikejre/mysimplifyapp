package com.example.myatlabproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    private Button loginbtn;
    private Button Regpg;
    private EditText eid;
    private EditText pwd;
    private FirebaseAuth auth;
    public RecyclerView recyclerView;
    NotesRecyclerAdapter notesRecyclerAdapter;
    private static final String TAG = "MainActivity";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginbtn=findViewById(R.id.login);
        Regpg=findViewById(R.id.register);
        eid=findViewById(R.id.emailid);
        pwd=findViewById(R.id.passwd);
        recyclerView=findViewById(R.id.recycler_View);
//        if(recycler_View!=null)
//        {
//            Log.d(TAG, "recycler found --------------------------------");
//        }
//        else{
//            Log.d(TAG, "noooooooooo--------------------------------------------------");
//        }
        auth=FirebaseAuth.getInstance();

        Regpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this,registrationpg.class);
                startActivity(i);
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String my_email_txt=eid.getText().toString();
                String pwd_txt=pwd.getText().toString();
                loginUser(my_email_txt,pwd_txt);
            }
        });
    }

    private void loginUser(String my_email_txt, String pwd_txt) {



        auth.signInWithEmailAndPassword(my_email_txt,pwd_txt).addOnCompleteListener(MainActivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                    Intent in=new Intent(MainActivity.this,HomePage.class);

                    startActivity(in);
                    //initRecyclerView(auth.getCurrentUser());
                    //Log.d(TAG, "my current user ---------- "+auth.getCurrentUser().getUid());
                    finish();
                }
                if(!task.isSuccessful()||!task.isComplete())
                {
                    Toast.makeText(MainActivity.this, "Wrong Password or email!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

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
//        notesRecyclerAdapter=new NotesRecyclerAdapter(options);
//        recyclerView.setAdapter(notesRecyclerAdapter);
//        notesRecyclerAdapter.startListening();
//
//
//    }

}