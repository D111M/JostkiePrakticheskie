package com.example.room;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static final String SHARED_PATH = "settings";
    public static String SHARED_LOGIN = "login";
    public static String SHARED_PASSWORD = "password";
    public static int ID;
    public static SharedPreferences shared;
    EditText login, password;
    TextView allUsers;
    AppDatabase db;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shared = getSharedPreferences(SHARED_PATH, Context.MODE_PRIVATE);
        db = AppDatabase.getObInstance(getApplicationContext());
        login = findViewById(R.id.editText2);
        password = findViewById(R.id.editText);
        allUsers = findViewById(R.id.textView);

        if(!shared.getString(SHARED_LOGIN, "").equals("")){
            try{
                User user = db.userDao().getUser(shared.getString(SHARED_LOGIN, ""));
                if(Objects.equals(user.getPassword(), shared.getString(SHARED_PASSWORD, ""))){
                    ID = user.getId();
                    Intent intent = new Intent(this, MenuActivity.class);
                    intent.putExtras(getIntent());
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();

                }
            } catch (Exception e){
                System.out.println(shared.getString(SHARED_LOGIN, "") + e);
            }
        }
        fetchUsers();
    }

    @SuppressLint("SetTextI18n")
    private void fetchUsers() {
        AppDatabase db = AppDatabase.getObInstance(getApplicationContext());
        List<User> users = db.userDao().getAllUsers();
        allUsers.setText("");
        for(int i = 0; i < users.size(); i++){
            allUsers.append("Id: "+ users.get(i).getId() + "; Login: "+ users.get(i).getLogin() + "; Password: "+ users.get(i).getPassword()+"; Balance: "+users.get(i).getBalance()+"\n");
        }
    }

    public void onClickReg(View view) {
        User user = new User(
                login.getText().toString(),
                password.getText().toString(),
                0
        );
        if(login.getText().toString().equals("")){
            Toast.makeText(this, "Логин должен содержать минимум один символ", Toast.LENGTH_LONG).show();
            return;
        }
        if(password.getText().toString().equals("")){
            Toast.makeText(this, "Пароль должен содержать минимум один символ", Toast.LENGTH_LONG).show();
            return;
        }
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = shared.edit();
        try {
            db.userDao().insertUser(user);
            editor.putString(SHARED_LOGIN, login.getText().toString());
            editor.putString(SHARED_PASSWORD, password.getText().toString());
            editor.apply();
        } catch (Exception e){
            Toast.makeText(this, "Такой логин уже занят", Toast.LENGTH_LONG).show();
        }
        fetchUsers();
    }

    public void onClickEnter(View view) {
        User use;
        try {
            use = db.userDao().getUser(login.getText().toString());
            if(use == null){
                Toast.makeText(this, "Нет такого логина!", Toast.LENGTH_LONG).show();
                return;
            }
        } catch (Exception e){
            Toast.makeText(this, "Нет такого логина!", Toast.LENGTH_LONG).show();
            return;
        }
        if(password.getText().toString().equals("")){
            Toast.makeText(this, "Пароль должен содержать минимум один символ", Toast.LENGTH_LONG).show();
            return;
        }
        if(login.getText().toString().equals("")){
            Toast.makeText(this, "Логин должен содержать минимум один символ", Toast.LENGTH_LONG).show();
            return;
        }
        if(use.getPassword().equals(password.getText().toString())){
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = shared.edit();
            editor.putString(SHARED_LOGIN, login.getText().toString());
            editor.putString(SHARED_PASSWORD, password.getText().toString());
            editor.apply();
            ID = use.getId();
            Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtras(getIntent());
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        } else {
            Toast.makeText(this, "Не верные данные!", Toast.LENGTH_LONG).show();
        }
    }
}