package com.example.imdmarket.activities;

import android.content.ContentValues;
import android.database.Cursor;
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

public class UpdateActivity extends AppCompatActivity {

    private EditText editTextProductCode;
    private EditText editTextProductName;
    private EditText editTextProductDescription;
    private EditText editTextStock;

    private IMDMarketDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        editTextProductCode = findViewById(R.id.editTextProductCode);
        editTextProductName = findViewById(R.id.editTextProductName);
        editTextProductDescription = findViewById(R.id.editTextProductDescription);
        editTextStock = findViewById(R.id.editTextStock);

        Button buttonUpdateProduct = findViewById(R.id.buttonUpdateProduct);

        dbHelper = new IMDMarketDBHelper(this);

        buttonUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProduct();
            }
        });
    }

    private void updateProduct() {
        String productCodeStr = editTextProductCode.getText().toString().trim();

        if (TextUtils.isEmpty(productCodeStr)) {
            Toast.makeText(this, "Please enter the product code", Toast.LENGTH_SHORT).show();
            return;
        }

        int productCode = Integer.parseInt(productCodeStr);

        // Check if the product with the given code exists
        if (productExists(productCode)) {
            // Get optional values
            String productName = editTextProductName.getText().toString().trim();
            String productDescription = editTextProductDescription.getText().toString().trim();
            String stockStr = editTextStock.getText().toString().trim();

            // Update the product in the database
            boolean success = updateProductInDatabase(productCode, productName, productDescription, stockStr);

            if (success) {
                Toast.makeText(this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                finish(); // Close the ProductModificationActivity
            } else {
                Toast.makeText(this, "Error updating product", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Product with code " + productCode + " does not exist", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean productExists(int productCode) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {"code"};
        String selection = "code = ?";
        String[] selectionArgs = {String.valueOf(productCode)};

        try (Cursor cursor = db.query("products", projection, selection, selectionArgs, null, null, null)) {
            return cursor.getCount() > 0;
        }
    }

    private boolean updateProductInDatabase(int productCode, String productName, String productDescription, String stockStr) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", productName);
        values.put("description", productDescription);

        if (!TextUtils.isEmpty(stockStr)) {
            int stock = Integer.parseInt(stockStr);
            values.put("stock", stock);
        }

        String whereClause = "code = ?";
        String[] whereArgs = {String.valueOf(productCode)};

        int rowsAffected = db.update("products", values, whereClause, whereArgs);

        return rowsAffected > 0;
    }
}
