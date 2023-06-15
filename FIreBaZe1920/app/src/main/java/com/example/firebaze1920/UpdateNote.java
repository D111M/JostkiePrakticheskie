package com.example.firebaze1920;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateNote extends AppCompatActivity {
    EditText FUP, SUP;
    Button updateBtn;
    String id, email;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);
        FUP = findViewById(R.id.FirstUp);
        SUP = findViewById(R.id.SecondUp);
        updateBtn = findViewById(R.id.updateNote);
        id = getIntent().getStringExtra("id");
        email = getIntent().getStringExtra("EMAIL");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("notes").document(id).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()){
                        Notes note = new Notes(documentSnapshot.getString("title"),
                                documentSnapshot.getString("content"),
                                documentSnapshot.getString("email"));

                        fill(note);
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(UpdateNote.this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void fill(Notes notes){
        SUP.setText(notes.getContent());
        FUP.setText(notes.getTitle());
    }

    public void changeNote(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference noteRef = db.collection("notes").document(id);

        noteRef.update("title", FUP.getText().toString(),
                        "content", SUP.getText().toString())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Заметка успешно изменена", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                        intent.putExtra("EMAIL", email);
                        startActivity(intent);
                    }

                });
    }
    public void deleteNote(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference noteRef = db.collection("notes").document(id);

        noteRef.delete().addOnSuccessListener(unused -> {
            Toast.makeText(getApplicationContext(), "Заметка успешно удалена", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainMenu.class);
            intent.putExtra("EMAIL", email);
            startActivity(intent);
        });
    }
}