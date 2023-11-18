package com.example.imdmarket.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

// the helper class is primarily responsible for managing the SQLite database, and placing it in a
// dedicated database package helps keep related database code in one location.
public class IMDMarketDBHelper extends SQLiteOpenHelper {
    public IMDMarketDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private static final String DATABASE_NAME = "imdmarket.db";
    private static final int DATABASE_VERSION = 1;

    public IMDMarketDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create the Product table
        String createProductTable = "CREATE TABLE " + ProductEntry.TABLE_NAME + " ("
                + ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ProductEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + ProductEntry.COLUMN_CODE + " INTEGER NOT NULL, "
                + ProductEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, "
                + ProductEntry.COLUMN_STOCK + " INTEGER NOT NULL, "
                // ... add other columns as needed
                + ");";

        // Execute the SQL statement
        db.execSQL(createProductTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop the old table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + ProductEntry.TABLE_NAME);

        // Create a new table
        onCreate(db);
    }
}

