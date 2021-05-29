package com.example.myatlabproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registrationpg extends AppCompatActivity {

    private EditText myemail;
    private  EditText passwd;
    private Button reg;

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrationpg);
        myemail=findViewById(R.id.myemail);
        passwd=findViewById(R.id.passwrd);
        reg=findViewById(R.id.registerbtn);

        auth=FirebaseAuth.getInstance();

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_txt=myemail.getText().toString();
                String pwd_txt=passwd.getText().toString();
                if(email_txt.isEmpty()||pwd_txt.isEmpty())
                    Toast.makeText(registrationpg.this, "Empty password or email", Toast.LENGTH_SHORT).show();
                else if(pwd_txt.length()<6)
                    Toast.makeText(registrationpg.this, "password too short!", Toast.LENGTH_SHORT).show();
                else{
                    registerUser(email_txt,pwd_txt);
                }

            }
        });
    }

    private void registerUser(String email_txt, String pwd_txt) {
        auth.createUserWithEmailAndPassword(email_txt,pwd_txt).addOnCompleteListener(registrationpg.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                    Toast.makeText(registrationpg.this, "Successful Registration!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(registrationpg.this, "Failed!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}