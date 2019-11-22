package com.felipevelasquez.logintest.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.felipevelasquez.logintest.R;
import com.felipevelasquez.logintest.tools.Validations;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnCompleteListener {

    private Validations validations;

    private EditText email;
    private EditText password;
    private ProgressBar progressBar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initObjects();
        initElements();
    }

    private void initObjects() {
        validations = new Validations();
    }

    private void initElements() {
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        progressBar = findViewById(R.id.progress);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        updateActivity(currentUser, false);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btLogin:
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
                String passwordString = password.getText().toString();
                if (passwordString.isEmpty()) {
                    password.setError(getString(R.string.not_empty));
                    return;
                } else {
                    password.setError(null);
                }
                signIn(emailString, passwordString);
                break;
            case R.id.tvRegister:
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.tvRestartPassword:
                intent = new Intent(this, RestartPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void signIn(String email, String password) {
        Log.d("Message", "SignIn: " + email);
        progressBar.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, this);
    }

    @Override
    public void onComplete(@NonNull Task task) {
        if (task.isSuccessful()) {
            Log.d("Mensaje", "Login:Success");
            FirebaseUser user = auth.getCurrentUser();
            updateActivity(user, true);
        } else {
            Log.e("Mensaje", "Login:Failure");
            Toast.makeText(this, "Login:Failure", Toast.LENGTH_SHORT).show();
            updateActivity(null, true);
        }
    }

    private void updateActivity(FirebaseUser user, boolean flag) {
        progressBar.setVisibility(View.GONE);
        if (user != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            if (flag) {
                Toast.makeText(this, "Email or password incorrect", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
