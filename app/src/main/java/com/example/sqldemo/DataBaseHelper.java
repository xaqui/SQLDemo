package com.example.sqldemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String COLUMN_CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String COLUMN_CUSTOMER_AGE = "CUSTOMER_AGE";
    public static final String COLUMN_ACTIVE_CUSTOMER = "ACTIVE_CUSTOMER";
    public static final String COLUMN_ID = "ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    // First time a database is accessed.
    // Code to create a new database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + CUSTOMER_TABLE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CUSTOMER_NAME + " TEXT, " + COLUMN_CUSTOMER_AGE + " TEXT, " +
                COLUMN_ACTIVE_CUSTOMER + " BOOL )";
        db.execSQL(createTableStatement);
    }

    // Called if the database version number changes.
    // It prevents apps from crashing when you change the database design
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // CREATE
    public boolean addOne(CustomerModel customerModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CUSTOMER_NAME, customerModel.getName());
        cv.put(COLUMN_CUSTOMER_AGE, customerModel.getAge());
        cv.put(COLUMN_ACTIVE_CUSTOMER, customerModel.isActive());

        long insert = db.insert(CUSTOMER_TABLE, null, cv);
        return insert >= 0;
    }


    // READ
    public List<CustomerModel> getAllCustomers(){
        List<CustomerModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM "+ CUSTOMER_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            // loop through results and create customer object
            do {
                int customerID = cursor.getInt(0);
                String customerName = cursor.getString(1);
                int customerAge = cursor.getInt(2);
                boolean customerActive = cursor.getInt(3) == 1;

                CustomerModel newCustomer = new CustomerModel(customerID, customerName, customerAge, customerActive);
                returnList.add(newCustomer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return  returnList;
    }

    // DELETE
    public boolean deleteOne(CustomerModel customerModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM "+ CUSTOMER_TABLE + " WHERE " +COLUMN_ID + " = "+customerModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);
        return cursor.moveToFirst();
    }

}
