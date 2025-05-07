package com.example.gearup;

import android.content.Intent;
import android.os.Bundle;
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

public class Register extends AppCompatActivity {

    EditText etFullname, etShopName, etPhone, etShopAddress, etEmail, etPassword, etConfirmPassword;
    TextView tv_login_redirect;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();

        btnRegister.setOnClickListener(view -> {
            String fullname = etFullname.getText().toString().trim();
            String shopName = etShopName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String address = etShopAddress.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            if (fullname.isEmpty() || shopName.isEmpty() || phone.isEmpty() || address.isEmpty()
                    || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(Register.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(Register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                signUpUser(email, password);
                Toast.makeText(Register.this, "User Registered", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Register.this, Login.class));
                finish();
            }
        });

        // Sign Up takes to Register page
        tv_login_redirect.setOnClickListener(view -> {
            startActivity(new Intent(Register.this, Login.class));
        });
    }

    private void signUpUser(String email, String password){
        FirebaseAuth authInstance = FirebaseAuth.getInstance();

        authInstance.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Registration successful
                        FirebaseUser registeredUser = authInstance.getCurrentUser();
                        Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        // Proceed to the next screen (e.g., HomeActivity)
                    } else {
                        // If registration fails
                        Toast.makeText(Register.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void init(){
        etFullname = findViewById(R.id.et_fullname);
        etShopName = findViewById(R.id.et_shop_name);
        etPhone = findViewById(R.id.et_phone);
        etShopAddress = findViewById(R.id.et_shop_address);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnRegister = findViewById(R.id.btn_register);
        tv_login_redirect=findViewById(R.id.tv_login_redirect);
    }
}