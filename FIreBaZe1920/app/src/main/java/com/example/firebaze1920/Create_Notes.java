package com.example.firebaze1920;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

public class Create_Notes extends AppCompatActivity {
    EditText etTitle;
    EditText etText;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notes);
        Intent intent = getIntent();
        email = intent.getStringExtra("EMAIL");
        etTitle = findViewById(R.id.etTitle_create);
        etText = findViewById(R.id.etText_create);
    }

    public void createNote(View view) {
        if (etTitle.getText().length() > 0
                && etText.getText().length() > 0){
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            Notes newNote = new Notes(etTitle.getText().toString(), etText.getText().toString(), email);
            db.collection("notes").add(newNote)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(getApplicationContext(), "Заметка успешно создана!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                        intent.putExtra("EMAIL", email);
                        startActivity(intent);
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(Create_Notes.this, e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

}