package com.example.imdmarket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.imdmarket.R;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        // Find buttons by their IDs
        Button buttonCreate = findViewById(R.id.buttonCreate);
        Button buttonRead = findViewById(R.id.buttonRead);
        Button buttonUpdate = findViewById(R.id.buttonUpdate);
        Button buttonDelete = findViewById(R.id.buttonDelete);

        // Set click listeners for each button
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the CreateActivity
                startActivity(new Intent(MainMenuActivity.this, CreateActivity.class));
            }
        });

        buttonRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the ReadActivity
                startActivity(new Intent(MainMenuActivity.this, ReadActivity.class));
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the UpdateActivity
                startActivity(new Intent(MainMenuActivity.this, UpdateActivity.class));
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the DeleteActivity
                startActivity(new Intent(MainMenuActivity.this, DeleteActivity.class));
            }
        });
    }
}
