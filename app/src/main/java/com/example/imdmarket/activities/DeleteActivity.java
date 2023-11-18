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

public class DeleteActivity extends AppCompatActivity {

    private EditText editTextProductCode;

    private IMDMarketDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        editTextProductCode = findViewById(R.id.editTextProductCode);

        Button buttonDeleteProduct = findViewById(R.id.buttonDeleteProduct);

        dbHelper = new IMDMarketDBHelper(this);

        buttonDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct();
            }
        });
    }

    private void deleteProduct() {
        String productCodeStr = editTextProductCode.getText().toString().trim();

        if (TextUtils.isEmpty(productCodeStr)) {
            Toast.makeText(this, "Please enter the product code", Toast.LENGTH_SHORT).show();
            return;
        }

        int productCode = Integer.parseInt(productCodeStr);

        // Check if the product with the given code exists
        if (productExists(productCode)) {
            // Delete the product from the database
            boolean success = deleteProductFromDatabase(productCode);

            if (success) {
                Toast.makeText(this, "Product deleted successfully", Toast.LENGTH_SHORT).show();
                finish(); // Close the DeleteActivity
            } else {
                Toast.makeText(this, "Error deleting product", Toast.LENGTH_SHORT).show();
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

    private boolean deleteProductFromDatabase(int productCode) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String whereClause = "code = ?";
        String[] whereArgs = {String.valueOf(productCode)};

        int rowsAffected = db.delete("products", whereClause, whereArgs);

        return rowsAffected > 0;
    }
}
