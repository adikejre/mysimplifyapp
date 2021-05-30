package com.example.myatlabproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Date;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class Notesapp extends AppCompatActivity implements NotesRecyclerAdapter.NoteListener {


    private Button btn2;
    private static final String TAG = "Notesapp";
    RecyclerView recycler_View1;
    NotesRecyclerAdapter notesRecyclerAdapter;
    Context context;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notesapp);
        //RecyclerView rview=findViewById(R.id.recyclerlist);
        FloatingActionButton fab = findViewById(R.id.note_edit);
         btn2=findViewById(R.id.btn4);
        recycler_View1=findViewById(R.id.recycler_View);
        recycler_View1.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entermynote();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRecyclerView(FirebaseAuth.getInstance().getCurrentUser());
            }
        });
    }

    private void entermynote() {
        final EditText  editnotes=new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("Add Note")
                .setView(editnotes)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick: "+editnotes.getText());
                        addnote(editnotes.getText().toString());
                        Log.d(TAG, "note added---------->>--------->>>>------------>>>-----");
                        initRecyclerView(FirebaseAuth.getInstance().getCurrentUser());
                        Log.d(TAG, "adapter---------->>--------->>>>------------>>>-----");
                    }
                })
                .setNegativeButton("Cancel",null)
                .show();

    }

    private void addnote(String text) {
        String userid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        Notee note =new Notee(text,false,new Timestamp(new Date()),userid);
        FirebaseFirestore.getInstance().collection("notes")
                .add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "onSuccess: success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Notesapp.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void initRecyclerView(FirebaseUser user){
        Query query= FirebaseFirestore.getInstance()
                .collection("notes")
                .whereEqualTo("userid",user.getUid())
        .orderBy("created",Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Notee> options=new FirestoreRecyclerOptions.Builder<Notee>()
                .setQuery(query,Notee.class).build();

        notesRecyclerAdapter=new NotesRecyclerAdapter(options,this);
        recycler_View1.setLayoutManager(new LinearLayoutManager(Notesapp.this));
        recycler_View1.setAdapter(notesRecyclerAdapter);
        notesRecyclerAdapter.startListening();

        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recycler_View1);
    }



    @Override
    public void handleEditNote(DocumentSnapshot snapshot) {
        Notee note=snapshot.toObject(Notee.class);
        EditText editText=new EditText(this);
        editText.setText(note.getText().toString());
        editText.setSelection(note.getText().length());

        new AlertDialog.Builder(this)
                .setTitle("Edit Note")
                .setView(editText)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newText=editText.getText().toString();
                        note.setText(newText);
                        snapshot.getReference().set(note);
                        initRecyclerView(FirebaseAuth.getInstance().getCurrentUser());

                    }
                })
                .setNegativeButton("Cancel",null).show();


    }

    @Override
    public void handleDeleteItem(DocumentSnapshot snapshot) {
        snapshot.getReference().delete();
    }

    ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if(direction==ItemTouchHelper.LEFT){
                //Toast.makeText(context, "delete my item", Toast.LENGTH_SHORT).show();
                NotesRecyclerAdapter.NoteViewHolder noteViewHolder= (NotesRecyclerAdapter.NoteViewHolder) viewHolder;
                noteViewHolder.deleteItem();
                initRecyclerView(FirebaseAuth.getInstance().getCurrentUser());
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(Notesapp.this, R.color.design_default_color_error))
                    .addActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        }
    };
}