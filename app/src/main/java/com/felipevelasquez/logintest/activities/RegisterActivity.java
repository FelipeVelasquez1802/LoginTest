package com.felipevelasquez.logintest.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.felipevelasquez.logintest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, OnCompleteListener {

    private EditText email;
    private EditText password;
    private EditText repeatPassword;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initElements();
    }

    private void initElements() {
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        repeatPassword = findViewById(R.id.etRepeatPassword);

        auth = FirebaseAuth.getInstance();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSend:
                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();
                createAccount(emailString, passwordString);
                break;
        }
    }

    private void createAccount(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, this);
    }

    @Override
    public void onComplete(@NonNull Task task) {
        if (task.isSuccessful()) {
            Log.d("Mensaje", "Register:Success");
            FirebaseUser user = auth.getCurrentUser();
            updateActivity(user);
        } else {
            Log.e("Mensaje", "Register:Failure");
            Toast.makeText(this, "Register:Failure", Toast.LENGTH_SHORT).show();
            updateActivity(null);
        }
    }

    private void updateActivity(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Email or password incorrect", Toast.LENGTH_SHORT).show();
        }
    }
}
