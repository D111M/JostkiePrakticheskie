package com.example.firebaze1920;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class NotesAd extends RecyclerView.Adapter<NotesAd.NoteViewHolder> {
    private Context context;
    private List<DocumentSnapshot> notes;

    public NotesAd(Context context, List<DocumentSnapshot> notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notes_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvTitle.setText(notes.get(position).getString("title"));
        holder.tvText.setText(notes.get(position).getString("content"));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateNote.class);
                intent.putExtra("id", notes.get(position).getId());
                intent.putExtra("EMAIL", notes.get(position).getString("email"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
    public final static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvText;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.title);
            tvText = itemView.findViewById(R.id.text);
        }
    }
}
