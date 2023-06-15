package com.example.bd;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {

    String Id_Game;
    EditText nameGame, gameInfo;
    Button deletebt, updatebt;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        deletebt = findViewById(R.id.deletebt);
        updatebt = findViewById(R.id.updatebt);
        nameGame = findViewById(R.id.nametx);
        gameInfo = findViewById(R.id.describetx);

        Id_Game = getIntent().getStringExtra("idGame");
        nameGame.setText(getIntent().getStringExtra("GameName"));
        gameInfo.setText(getIntent().getStringExtra("GameInfo"));

        deletebt.setOnClickListener(v -> {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity3.this);
            dataBaseHelper.deleteGame(Id_Game);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });

        updatebt.setOnClickListener(v -> {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity3.this);
            dataBaseHelper.updateGame(Id_Game, nameGame.getText().toString(), gameInfo.getText().toString());
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

}