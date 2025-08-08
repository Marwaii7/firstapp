package com.example.firstapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    CheckBox savePasswordCheckBox;
    Button loginButton;

    SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "LoginPrefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        savePasswordCheckBox = findViewById(R.id.savePasswordCheckBox);
        loginButton = findViewById(R.id.loginButton);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        loadSavedData();

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            if (savePasswordCheckBox.isChecked()) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_EMAIL, email);
                editor.putString(KEY_PASSWORD, password);
                editor.apply();
                Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
            } else {
                sharedPreferences.edit().clear().apply();
            }

            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadSavedData() {
        String savedEmail = sharedPreferences.getString(KEY_EMAIL, "");
        String savedPassword = sharedPreferences.getString(KEY_PASSWORD, "");

        emailEditText.setText(savedEmail);
        passwordEditText.setText(savedPassword);

        if (!savedEmail.isEmpty() && !savedPassword.isEmpty()) {
            savePasswordCheckBox.setChecked(true);
        }
    }
}
