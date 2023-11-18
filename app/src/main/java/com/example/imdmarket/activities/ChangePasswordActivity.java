package com.example.imdmarket.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.imdmarket.R;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText editTextOldPassword;
    private EditText editTextNewPassword;
    private EditText editTextConfirmNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        editTextOldPassword = findViewById(R.id.editTextOldPassword);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmNewPassword = findViewById(R.id.editTextConfirmNewPassword);
        Button buttonChangePassword = findViewById(R.id.buttonChangePassword);

        // Set a click listener for the change password button
        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    private void changePassword() {
        String oldPassword = editTextOldPassword.getText().toString().trim();
        String newPassword = editTextNewPassword.getText().toString().trim();
        String confirmNewPassword = editTextConfirmNewPassword.getText().toString().trim();

        // Check if old, new, and confirm passwords are not empty
        if (TextUtils.isEmpty(oldPassword) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmNewPassword)) {
            Toast.makeText(this, "Please enter all password fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the new password and its confirmation match
        if (!newPassword.equals(confirmNewPassword)) {
            Toast.makeText(this, "New password and confirmation do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the old password matches the saved password
        if (oldPasswordMatchesSavedPassword(oldPassword)) {
            // Save the new password to SharedPreferences
            saveNewPassword(newPassword);

            Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show();
            finish(); // Close the ChangePasswordActivity
        } else {
            Toast.makeText(this, "Incorrect old password", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean oldPasswordMatchesSavedPassword(String oldPassword) {
        // Retrieve the saved password from SharedPreferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedPassword = preferences.getString("password", "");

        // Check if the entered old password matches the saved password
        return oldPassword.equals(savedPassword);
    }

    private void saveNewPassword(String newPassword) {
        // Save the new password to SharedPreferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("password", newPassword);
        editor.apply();
    }
}
