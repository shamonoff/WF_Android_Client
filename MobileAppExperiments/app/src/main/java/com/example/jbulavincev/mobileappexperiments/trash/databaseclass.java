package com.example.jbulavincev.mobileappexperiments.trash;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.jbulavincev.mobileappexperiments.WorkorderData;

import java.util.ArrayList;

/**
 * Created by jbulavincev on 05.01.2015.
 */
public class databaseclass extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DICTIONARY_TABLE_NAME = "dictionary";
    private static final String DATABASE_NAME = "MYDATABASE";
    public static final String wo_table_create =
            "CREATE TABLE WOTABLE(WOID INT PRIMARY KEY     NOT NULL," +
                    " WORKERNAME           TEXT    NOT NULL," +
                    "DATE            TEXT     NOT NULL);";

    databaseclass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(wo_table_create);
        Log.d("WordordersActivity", "Database was created.");
        populateDatabase();
    }

    public void populateDatabase()
    {
        // to implement for a real DB
        Log.d("WordordersActivity", "Database was populated.");
    }

    public void populateDatabaseWithArray(ArrayList<WorkorderData> WOArray)
    {

        Log.d("WordordersActivity", "Database was populated.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("WordordersActivity", "Database was updated.");
    }
}