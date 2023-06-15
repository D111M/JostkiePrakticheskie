package com.example.paper;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
    EditText NG, OG;
    Button AG;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        NG = findViewById(R.id.name);
        OG = findViewById(R.id.describe);
        AG = findViewById(R.id.add);
        DataBaseHelper dataBaseHelper = new DataBaseHelper();
        AG.setOnClickListener (view -> {
            Model model = new Model(NG.getText().toString(), OG.getText().toString());
            dataBaseHelper.AddModel(model, AddActivity.this);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}