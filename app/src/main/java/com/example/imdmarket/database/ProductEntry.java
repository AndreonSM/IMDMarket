package com.example.imdmarket.database;

import android.provider.BaseColumns;

public class ProductEntry implements BaseColumns {
    public static final String TABLE_NAME = "products";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_STOCK = "stock_quantity";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_CODE = "code";
    // ... add other column names
}
// Nome, código, descrição, estoque