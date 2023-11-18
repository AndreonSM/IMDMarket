package com.example.imdmarket.activities;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.imdmarket.R;
import com.example.imdmarket.database.IMDMarketDBHelper;

public class CreateActivity extends AppCompatActivity {

    private EditText editTextProductCode;
    private EditText editTextProductName;
    private EditText editTextProductDescription;
    private EditText editTextStock;

    private IMDMarketDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        editTextProductCode = findViewById(R.id.editTextProductCode);
        editTextProductName = findViewById(R.id.editTextProductName);
        editTextProductDescription = findViewById(R.id.editTextProductDescription);
        editTextStock = findViewById(R.id.editTextStock);

        Button buttonRegisterProduct = findViewById(R.id.buttonRegisterProduct);

        dbHelper = new IMDMarketDBHelper(this);

        buttonRegisterProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerProduct();
            }
        });
    }

    private void registerProduct() {
        String productCodeStr = editTextProductCode.getText().toString().trim();
        String productName = editTextProductName.getText().toString().trim();
        String productDescription = editTextProductDescription.getText().toString().trim();
        String stockStr = editTextStock.getText().toString().trim();

        if (TextUtils.isEmpty(productCodeStr) || TextUtils.isEmpty(productName) ||
                TextUtils.isEmpty(productDescription) || TextUtils.isEmpty(stockStr)) {
            Toast.makeText(this, "Please enter all product details", Toast.LENGTH_SHORT).show();
            return;
        }

        int productCode = Integer.parseInt(productCodeStr);
        int stock = Integer.parseInt(stockStr);

        // Insert the product into the database
        long newRowId = insertProduct(productCode, productName, productDescription, stock);

        if (newRowId != -1) {
            Toast.makeText(this, "Product registered successfully with ID: " + newRowId, Toast.LENGTH_SHORT).show();
            finish(); // Close the RegistrationActivity
        } else {
            Toast.makeText(this, "Error registering product", Toast.LENGTH_SHORT).show();
        }
    }

    private long insertProduct(int productCode, String productName, String productDescription, int stock) {
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put("code", productCode);
        values.put("name", productName);
        values.put("description", productDescription);
        values.put("stock", stock);

        // Insert the new row, returning the primary key value of the new row
        return db.insert("products", null, values);
    }


}
