package com.example.myatlabproject1;


import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class NotesRecyclerAdapter extends FirestoreRecyclerAdapter<Notee, NotesRecyclerAdapter.NoteViewHolder> {

    NoteListener noteListener;
    public NotesRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Notee> options,NoteListener noteListener) {
        super(options);
        this.noteListener=noteListener;
    }
    
    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull Notee model) {
        holder.notetext.setText(model.getText());
        //holder.checkbox1.setChecked(true);
        holder.checkbox1.setChecked(model.isCompleted());
        CharSequence dateCharSeq = DateFormat.format("EEEE, MMM d, yyyy h:mm:ss a", model.getCreated().toDate());
        holder.datetext.setText(dateCharSeq);


    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.note_row,parent,false);
        return new NoteViewHolder(view);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{
        private static final String TAG = "NoteViewHolder";
        TextView notetext,datetext;
        CheckBox checkbox1;
         public NoteViewHolder(@NonNull View itemView) {
             super(itemView);

             notetext=itemView.findViewById(R.id.notetext);
             datetext=itemView.findViewById(R.id.datetext);
             checkbox1=itemView.findViewById(R.id.checkBox);
             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     DocumentSnapshot snapshot=getSnapshots().getSnapshot(getAdapterPosition());
                     noteListener.handleEditNote(snapshot);
                 }
             });


         }

        public void deleteItem() {
            noteListener.handleDeleteItem(getSnapshots().getSnapshot(getAdapterPosition()));
        }

     }

     interface NoteListener{
        public void handleEditNote(DocumentSnapshot snapshot);
        public void handleDeleteItem(DocumentSnapshot snapshot);
     }

}
