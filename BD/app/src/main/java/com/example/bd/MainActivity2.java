package com.example.bd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.nio.file.attribute.GroupPrincipal;

public class MainActivity2 extends AppCompatActivity {

    EditText gameName, gameInfo;
    Button addGame;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        gameName = findViewById(R.id.name) ;
        gameInfo = findViewById(R.id.describe);
        addGame = findViewById(R.id.btAddGame);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

        addGame.setOnClickListener(v -> {
            Groups groups = new Groups(0, gameName.getText().toString(),
                    gameInfo.getText().toString());

            dataBaseHelper.addGame(groups);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}