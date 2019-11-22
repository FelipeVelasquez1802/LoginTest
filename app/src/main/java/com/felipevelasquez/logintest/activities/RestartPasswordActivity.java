package com.felipevelasquez.logintest.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.felipevelasquez.logintest.R;
import com.felipevelasquez.logintest.tools.Validations;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RestartPasswordActivity extends AppCompatActivity implements View.OnClickListener, OnCompleteListener {

    private Validations validations;

    private EditText email;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restart_password);
        initObjects();
        initElements();
    }

    private void initObjects() {
        validations = new Validations();
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
                if (emailString.isEmpty()) {
                    email.setError(getString(R.string.not_empty));
                    return;
                } else {
                    if (!validations.isEmail(emailString)) {
                        email.setError(getString(R.string.email_error));
                        return;
                    } else {
                        email.setError(null);
                    }
                }
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
