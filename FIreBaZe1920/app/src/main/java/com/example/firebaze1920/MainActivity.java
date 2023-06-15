package com.example.firebaze1920;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnEntry;
    private String email, password;
    private static final String USER_PREFERENCES = "user_settings";
    private static final String USER_PREFERENCES_EMAIL = "email";
    private static final String USER_PREFERENCES_PASS = "password";

    SharedPreferences userPreferences;
    FirebaseAuth firebaseAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        btnEntry = findViewById(R.id.enter);
        firebaseAuth = FirebaseAuth.getInstance();
        userPreferences =getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        checkAuth();
    }


    private void checkAuth(){
        if (userPreferences.contains(USER_PREFERENCES_EMAIL) && userPreferences.contains(USER_PREFERENCES_PASS)){
            String prefEmail = userPreferences.getString(USER_PREFERENCES_EMAIL, "null");
            String prefPassword = userPreferences.getString(USER_PREFERENCES_PASS, "null");
            firebaseAuth.signInWithEmailAndPassword(prefEmail, prefPassword)
                    .addOnSuccessListener(authResult -> {
                        Intent intent = new Intent(MainActivity.this, MainMenu.class);
                        intent.putExtra("EMAIL", prefEmail);
                        startActivity(intent);
                    });
        }
    }

    public void clickReg(View view) {
        Intent intent = new Intent(this, Reg.class);
        startActivity(intent);
    }

    public void clickEntry(View view) {
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Неверная почта");
        }
        else if(TextUtils.isEmpty(password)){
            etPassword.setError("Поле с паролем не может быть пустым!");
        }
        else if(password.length() < 6){
            etPassword.setError("Пароль должен содержать в себе больше 6 символов!");
        }
        else firebaseAutorization();
    }

    private void firebaseAutorization(){
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    Intent intent = new Intent(MainActivity.this, MainMenu.class);
                    intent.putExtra("EMAIL", email);
                    startActivity(intent);

                    SharedPreferences.Editor editor = userPreferences.edit();
                    editor.putString(USER_PREFERENCES_EMAIL, email);
                    editor.putString(USER_PREFERENCES_PASS, password);
                    editor.apply();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(MainActivity.this, "Error! "+ e.getMessage(), Toast.LENGTH_LONG).show());
    }
}