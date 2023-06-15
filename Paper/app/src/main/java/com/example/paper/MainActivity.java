package com.example.paper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    RecyclerView list_model;
    Button main_add;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list_model = findViewById(R.id.list_model);
        Paper.init(this);
        main_add = findViewById(R.id.main_add);
        main_add.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AddActivity.class);
            startActivity(intent);
        });
        DataBaseHelper dataBaseHelper = new DataBaseHelper();
        list_model.setLayoutManager(new LinearLayoutManager(this));
        list_model.setHasFixedSize(true);
        Adapter adapter = new Adapter(this, dataBaseHelper.getModel());
        list_model.setAdapter(adapter);
    }
}