package com.example.firstapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginScreen extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    CheckBox savePasswordCheckBox;
    Button loginButton;

    SharedPreferences sharedPreferences;
    public static final String PREFS_NAME = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        savePasswordCheckBox = findViewById(R.id.savePasswordCheckBox);
        loginButton = findViewById(R.id.loginButton);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        String savedEmail = sharedPreferences.getString("email", "");
        String savedPassword = sharedPreferences.getString("password", "");
        emailEditText.setText(savedEmail);
        passwordEditText.setText(savedPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString();

                if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(LoginScreen.this, "ادخل إيميل صحيح", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(LoginScreen.this, "كلمة المرور يجب ألا تكون اقل من 6 حروف", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (savePasswordCheckBox.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", email);
                    editor.putString("password", password);
                    editor.apply();
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                }

                Toast.makeText(LoginScreen.this, "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
