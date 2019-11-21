package com.felipevelasquez.logintest.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.felipevelasquez.logintest.R;
import com.felipevelasquez.logintest.tools.Constant;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnCompleteListener {

    private EditText email;
    private EditText password;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initElement();
    }

    private void initElement() {
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btLogin:

                break;
        }
    }

    private void signIn(String email, String password) {
        Log.d("Message", "SignIn: " + email);
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, this);
    }

    @Override
    public void onComplete(@NonNull Task task) {

    }
}
