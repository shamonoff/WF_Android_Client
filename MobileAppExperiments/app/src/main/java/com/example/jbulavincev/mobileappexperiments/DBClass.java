package com.example.jbulavincev.mobileappexperiments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.jbulavincev.mobileappexperiments.WorkorderData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by jbulavincev on 05.01.2015.
 */
public class DBClass extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String LOG_TAG = "DBClass" ;
    private static final String LOGIN_COLUMN_NAME = "LOGIN";
    private static final String CREDSBASE64_COLUMN_NAME = "CREDSBASE64";
    private static final String STATUS_COLUMN_NAME = "STATUS";
    private static final String DATABASE_NAME = "CREDS_TABLE";
    private static final String creds_table_create =
            "CREATE TABLE "+ DATABASE_NAME +
                    "("+LOGIN_COLUMN_NAME+" TEXT PRIMARY KEY," +
                    CREDSBASE64_COLUMN_NAME+ " TEXT ," +
                    STATUS_COLUMN_NAME+  " TEXT);";

    DBClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void reloadDatabase()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME+";");
        Log.d(LOG_TAG, "Database was dropped.");
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(creds_table_create);
        Log.d(LOG_TAG, "Database was created.");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
        Log.d(LOG_TAG, "Database " + DATABASE_NAME + "was updated.");
    }

    public static String getDBName()
    {
        return DATABASE_NAME;
    }


     public void addCreds (String login, String credsBase64, String status) {

        ContentValues values = new ContentValues();
        Log.d(LOG_TAG, "Adding creds...");
        Log.d(LOG_TAG, "Parameters:" + login + ":" + credsBase64 + ":" + status + ".");
        values.put(LOGIN_COLUMN_NAME, login);
        values.put(CREDSBASE64_COLUMN_NAME, credsBase64);
        values.put(STATUS_COLUMN_NAME, status);

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(DATABASE_NAME, null, values);
        db.close();
    }

    public String getCredsBase64()
    {
        Cursor cursor=null;
        String CredsBase64 = null;
        SQLiteDatabase db = this.getReadableDatabase();
        cursor =  db.rawQuery("select * from " + DATABASE_NAME, null);
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {

                Log.d(LOG_TAG, "Cursor at: "+cursor.getString(1));
                CredsBase64 = cursor.getString(cursor.getColumnIndex(CREDSBASE64_COLUMN_NAME));
                Log.d(LOG_TAG, CREDSBASE64_COLUMN_NAME +" result: "+ CredsBase64);
            }
            cursor.close();
        }
        return CredsBase64;
    }

    public void printAllData()
    {
        String query = "SELECT  * FROM " + DATABASE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Log.d(LOG_TAG, "Viewing all DB entries for last session");
        if (cursor.moveToFirst()) {
            do {
                Log.d(LOG_TAG, "Query result parameters: "+cursor.toString());

            } while (cursor.moveToNext());
        }
        Log.d(LOG_TAG, "End of DB data list.");
    }

       /* public static final String wo_table_create =
            "CREATE TABLE "+ DATABASE_NAME +"(ID integer PRIMARY KEY  AUTOINCREMENT NOT NULL," +
                    "WOID TEXT ," +
                    " WORKERNAME           TEXT    ," +
                    " ENDDATE           TEXT   ," +
                    " ADDRESS           TEXT    ," +
                    " LATITUDE           TEXT   ," +
                    " STATE           TEXT   ," +
                    " TYPE           TEXT   ," +
                    " STARTDATE           TEXT ," +
                    " UUID           TEXT ," +
                    " STATUS           TEXT  ," +
                    " CUSTOMER           TEXT  ," +
                    " LONGITUDE           TEXT  ," +
                    " CREW           TEXT   ," +
                    "DATE            TEXT  );";*/

  /*  public int getRowsCount()
    {
        String query = "SELECT  * FROM " + DATABASE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor=null;
        Integer rowsCount=0;
            cursor =  db.rawQuery("select * from " + DATABASE_NAME, null);
            cursor.moveToLast();
            rowsCount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("ID")));
        return rowsCount;
    }*/

    /* public void populateDatabase()
    {
        // to implement for a real DB
        Log.d(LOG_TAG, "Database was populated.");
    }*/

  /*  public void populateDatabaseWithArray(ArrayList<WorkorderData> WOArray)
    {
        for (int i=0; i<WOArray.size() ; i++ ) {
            this.addWorkorder(WOArray.get(i));
            Log.d("WorkordersActivity", "Populating DB with "+i+" entry...");
        }
        Log.d(LOG_TAG, "Database was populated.");
    }*/
   /* public WorkorderData getCertainWOById (int Id)
    {
        Cursor cursor=null;
        WorkorderData WOfromDB = null;
        SQLiteDatabase db = this.getReadableDatabase();
        cursor =  db.rawQuery("select * from " + DATABASE_NAME + " where ID =" + Id  , null);
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {

                Log.d(LOG_TAG, "Cursor at: "+cursor.getString(1));
                Log.d(LOG_TAG, "QUERY: select * from " + DATABASE_NAME + " where ID =" + Id);
                String woid = cursor.getString(cursor.getColumnIndex("WOID"));
                String worker = cursor.getString(cursor.getColumnIndex("WORKERNAME"));
                String date = cursor.getString(cursor.getColumnIndex("DATE"));
                String ENDDATE = cursor.getString(cursor.getColumnIndex("ENDDATE"));
                String ADDRESS = cursor.getString(cursor.getColumnIndex("ADDRESS"));
                String LATITUDE = cursor.getString(cursor.getColumnIndex("LATITUDE"));
                String STATE = cursor.getString(cursor.getColumnIndex("STATE"));
                String TYPE = cursor.getString(cursor.getColumnIndex("TYPE"));
                String STARTDATE = cursor.getString(cursor.getColumnIndex("STARTDATE"));
                String UUID = cursor.getString(cursor.getColumnIndex("UUID"));
                String STATUS = cursor.getString(cursor.getColumnIndex("STATUS"));
                String CUSTOMER = cursor.getString(cursor.getColumnIndex("CUSTOMER"));
                String LONGITUDE = cursor.getString(cursor.getColumnIndex("LONGITUDE"));
                String CREW = cursor.getString(cursor.getColumnIndex("CREW"));
                Log.d(LOG_TAG, "Query result parameters: "+ woid + " "+ worker + " " + date + " "+ ENDDATE + " "+ STARTDATE + " "
                        + LATITUDE + " "+ STATE + " "+ TYPE + " "+ UUID + " "+ STATUS + " "+ CUSTOMER + " "+ LONGITUDE + " " + CREW +".");
                WOfromDB=new WorkorderData();
                WOfromDB.setWOId(woid);
                WOfromDB.setWorker(worker);
                WOfromDB.setDate(date);
                WOfromDB.enddate = ENDDATE;
                WOfromDB.address = ADDRESS;
                WOfromDB.latitude = LATITUDE;
                WOfromDB.state = STATE;
                WOfromDB.type = TYPE;
                WOfromDB.startdate = STARTDATE;
                WOfromDB.uuid = UUID;
                WOfromDB.status = STATUS;
                WOfromDB.customer = CUSTOMER;
                WOfromDB.longitude = LONGITUDE;
                WOfromDB.crew = CREW;
            }
            cursor.close();
        }
        return WOfromDB;
    }*/

     /*   public WorkorderData getLastWOItem ()
    {
        Cursor cursor=null;
        WorkorderData WOfromDB = null;
        SQLiteDatabase db = this.getReadableDatabase();
        cursor =  db.rawQuery("select * from " + DATABASE_NAME, null);
        if (cursor != null)
        {
            if (cursor.moveToLast())
            {

                Log.d(LOG_TAG, "Cursor at: "+cursor.getString(1));
                Log.d(LOG_TAG, "QUERY: select * from " + DATABASE_NAME);
                String woid = cursor.getString(cursor.getColumnIndex("WOID"));
                String worker = cursor.getString(cursor.getColumnIndex("WORKERNAME"));
                String date = cursor.getString(cursor.getColumnIndex("DATE"));
                String ENDDATE = cursor.getString(cursor.getColumnIndex("ENDDATE"));
                String ADDRESS = cursor.getString(cursor.getColumnIndex("ADDRESS"));
                String LATITUDE = cursor.getString(cursor.getColumnIndex("LATITUDE"));
                String STATE = cursor.getString(cursor.getColumnIndex("STATE"));
                String TYPE = cursor.getString(cursor.getColumnIndex("TYPE"));
                String STARTDATE = cursor.getString(cursor.getColumnIndex("STARTDATE"));
                String UUID = cursor.getString(cursor.getColumnIndex("UUID"));
                String STATUS = cursor.getString(cursor.getColumnIndex("STATUS"));
                String CUSTOMER = cursor.getString(cursor.getColumnIndex("CUSTOMER"));
                String LONGITUDE = cursor.getString(cursor.getColumnIndex("LONGITUDE"));
                String CREW = cursor.getString(cursor.getColumnIndex("CREW"));
                Log.d(LOG_TAG, "Query result parameters: "+ woid + " "+ worker + " " + date + " "+ ENDDATE + " "+ STARTDATE + " "
                        + LATITUDE + " "+ STATE + " "+ TYPE + " "+ UUID + " "+ STATUS + " "+ CUSTOMER + " "+ LONGITUDE + " " + CREW +".");
                WOfromDB=new WorkorderData();
                WOfromDB.setWOId(woid);
                WOfromDB.setWorker(worker);
                WOfromDB.setDate(date);
                WOfromDB.enddate = ENDDATE;
                WOfromDB.address = ADDRESS;
                WOfromDB.latitude = LATITUDE;
                WOfromDB.state = STATE;
                WOfromDB.type = TYPE;
                WOfromDB.startdate = STARTDATE;
                WOfromDB.uuid = UUID;
                WOfromDB.status = STATUS;
                WOfromDB.customer = CUSTOMER;
                WOfromDB.longitude = LONGITUDE;
                WOfromDB.crew = CREW;
            }
            cursor.close();
        }
        return WOfromDB;
    }*/

    /*public void addWorkorder (WorkorderData WOData) {

        ContentValues values = new ContentValues();
        Log.d(LOG_TAG, "Adding WO...");
        Log.d(LOG_TAG, "Parameters:" + WOData.woid + " " + WOData.worker + " " + WOData.date + " " + WOData.enddate + " " +
                WOData.address + " " + WOData.latitude + " " + WOData.state + " " + WOData.type + " " + WOData.startdate + " " + WOData.uuid + " "
                + WOData.status + " " + WOData.customer + " " + WOData.crew + " " + WOData.longitude + ".");
        values.put("WOID", WOData.woid);
        values.put("WORKERNAME", WOData.worker);
        values.put("DATE", WOData.date);
        values.put("ENDDATE", WOData.enddate);
        values.put("ADDRESS", WOData.address);
        values.put("LATITUDE", WOData.latitude);
        values.put("STATE", WOData.state);
        values.put("TYPE", WOData.type);
        values.put("STARTDATE", WOData.startdate);
        values.put("UUID", WOData.uuid);
        values.put("STATUS", WOData.status);
        values.put("CUSTOMER", WOData.customer);
        values.put("LONGITUDE", WOData.longitude);
        values.put("CREW", WOData.crew);

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(DATABASE_NAME, null, values);
        db.close();
    }*/

}