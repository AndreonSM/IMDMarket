package com.example.imdmarket.activities;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.imdmarket.R;
import com.example.imdmarket.database.IMDMarketDBHelper;

import java.util.ArrayList;

public class ReadActivity extends AppCompatActivity {
    // For simplicity, showing the details in an "Alert" dialog. Feel free to try and show those on another screen if you wish!

    private ListView listViewProducts;
    private ArrayList<String> productNames;
    private ArrayAdapter<String> arrayAdapter;

    private IMDMarketDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        listViewProducts = findViewById(R.id.listViewProducts);

        dbHelper = new IMDMarketDBHelper(this);

        // Initialize the ArrayList to store product names
        productNames = new ArrayList<>();

        // Initialize the ArrayAdapter
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productNames);

        // Set the ArrayAdapter on the ListView
        listViewProducts.setAdapter(arrayAdapter);

        // Set a click listener on the ListView items
        listViewProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected product name
                String selectedProductName = productNames.get(position);

                // Show the details in an "Alert" dialog
                showProductDetails(selectedProductName);
            }
        });

        // Load product names from the database
        loadProductNames();
    }

    private void loadProductNames() {
        // Clear the existing product names
        productNames.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        String[] projection = {"name"};

        try (Cursor cursor = db.query("products", projection, null, null, null, null, null)) {
            while (cursor.moveToNext()) {
                // Get the product name and add it to the ArrayList
                String productName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                productNames.add(productName);
            }
        }

        // Notify the ArrayAdapter that the data set has changed
        arrayAdapter.notifyDataSetChanged();
    }

    private void showProductDetails(String productName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        String[] projection = {"code", "name", "description", "stock"};

        // Define the selection criteria
        String selection = "name = ?";
        String[] selectionArgs = {productName};

        try (Cursor cursor = db.query("products", projection, selection, selectionArgs, null, null, null)) {
            if (cursor.moveToFirst()) {
                // Get the product details from the cursor
                int productCode = cursor.getInt(cursor.getColumnIndexOrThrow("code"));
                String productDescription = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                int stock = cursor.getInt(cursor.getColumnIndexOrThrow("stock"));

                // Build the product details message
                String productDetails = "Product Code: " + productCode + "\n"
                        + "Product Name: " + productName + "\n"
                        + "Product Description: " + productDescription + "\n"
                        + "Stock: " + stock;

                // Show an "Alert" dialog with the product details
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Product Details")
                        .setMessage(productDetails)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        }
    }
}
