package com.example.gearup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvNewHere;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Handle login button click event
        btnLogin.setOnClickListener(view -> {
            String userEmail = etEmail.getText().toString().trim();
            String userPassword = etPassword.getText().toString().trim();

            if (userEmail.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(Login.this, "Please enter both Email and Password", Toast.LENGTH_SHORT).show();
            } else {
                // Attempt to sign in with email and password
                signInUser(userEmail, userPassword);
            }
        });

        // Handle Sign Up link click event
        tvNewHere.setOnClickListener(view -> {
            startActivity(new Intent(Login.this, Register.class));
        });

        // Sign Up takes to Register page
        tvNewHere.setOnClickListener(view -> {
            startActivity(new Intent(Login.this, Register.class));
        });
    }

    private void signInUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign-in success
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        // Proceed to next activity (e.g., MainActivity)
                        startActivity(new Intent(Login.this, MainActivity.class));
                        finish();
                    } else {
                        // If sign-in fails
                        Toast.makeText(Login.this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void init(){
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvNewHere = findViewById(R.id.tvNewHere);
    }
}