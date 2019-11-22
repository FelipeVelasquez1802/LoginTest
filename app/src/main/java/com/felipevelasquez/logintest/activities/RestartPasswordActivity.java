package com.felipevelasquez.logintest.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.felipevelasquez.logintest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RestartPasswordActivity extends AppCompatActivity implements View.OnClickListener, OnCompleteListener {

    private EditText email;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restart_password);
        initElements();
    }

    private void initElements() {
        email = findViewById(R.id.etEmail);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSend:
                String emailString = email.getText().toString();
                sendPasswordReset(emailString);
                break;
        }
    }

    private void sendPasswordReset(String email) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(this, this);
    }

    @Override
    public void onComplete(@NonNull Task task) {
        if (task.isSuccessful()) {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
            Toast.makeText(this, "Send email", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }
}
