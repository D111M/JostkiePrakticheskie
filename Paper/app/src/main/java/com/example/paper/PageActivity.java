package com.example.paper;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import io.paperdb.Paper;

public class PageActivity extends AppCompatActivity {
    int Model_id;
    EditText sec_name, sec_desc;
    TextView nameModel, description;
    ArrayList<Model> model_list;
    Model model;
    Button delete, change, back;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        Paper.init(this);
        description = findViewById(R.id.description);
        nameModel = findViewById(R.id.nameModel);
        delete = findViewById(R.id.delete);
        back = findViewById(R.id.back);
        sec_name = findViewById(R.id.sec_name);
        sec_desc = findViewById(R.id.sec_desc);
        change = findViewById(R.id.change);
        DataBaseHelper data = new DataBaseHelper();
        Model_id = getIntent().getIntExtra("model_id", 0);
        model_list = data.getModel();
        model = model_list.get(0);

        description.setText(model.getDescription());
        nameModel.setText(model.getName());
        back.setOnClickListener(view -> {
            Intent intent = new Intent(PageActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        change.setOnClickListener(view -> {
            data.ChangeColumn(Model_id, sec_name.getText().toString(), sec_desc.getText().toString());
            Intent intent = new Intent(PageActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        delete.setOnClickListener(view -> {
            data.DeleteColumn(Model_id);
            Intent intent = new Intent(PageActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

    }
}