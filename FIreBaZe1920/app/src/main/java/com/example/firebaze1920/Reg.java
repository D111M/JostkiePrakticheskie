package com.example.firebaze1920;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Reg extends AppCompatActivity {
    private EditText ReM, ReP;
    private Button signUpBtn;
    private String email, password;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef = db.collection("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        ReM = findViewById(R.id.RegEmail);
        ReP = findViewById(R.id.RegPasw);
        signUpBtn = findViewById(R.id.Regstr);
        firebaseAuth = FirebaseAuth.getInstance();
        signUpBtn.setOnClickListener(v -> validData());

    }


    private void validData(){
        email = ReM.getText().toString().trim();
        password = ReP.getText().toString().trim();
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            ReM.setError("Неверно введена почта");
        }
        else if(TextUtils.isEmpty(password)){
            ReP.setError("Пароль не может быть пустым");
        }
        else if(password.length() < 6){
            ReP.setError("Пароль должен быть больше 6 символов");
        }
        else firebaseRegistration();
    }
    private void firebaseRegistration(){
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    String currentUserEmail = firebaseUser.getEmail();

                    User newUser = new User(firebaseUser.getEmail(), firebaseUser.getUid(), null);

                    userRef.add(newUser)
                            .addOnSuccessListener(documentReference -> Toast.makeText(getApplicationContext(),
                                    "Пользователь " + currentUserEmail + " зарегестрирован",
                                    Toast.LENGTH_LONG).show())
                            .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show());

                    Intent intent = new Intent(Reg.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(Reg.this, e.getMessage(), Toast.LENGTH_LONG).show());
    }

}