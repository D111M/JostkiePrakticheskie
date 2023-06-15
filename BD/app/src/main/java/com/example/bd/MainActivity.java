package com.example.bd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    RecyclerView ListGame;
    Button goAddGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListGame = findViewById(R.id.recycler);
        goAddGame = findViewById(R.id.btAdd);

        goAddGame.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
            startActivity(intent);
        });

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        ListGame.setLayoutManager(new LinearLayoutManager(this));
        ListGame.setHasFixedSize(true);
        Adapter adapter = new Adapter(this, dataBaseHelper.getGameList());
        ListGame.setAdapter(adapter);
    }
}