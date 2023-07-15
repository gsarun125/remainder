package com.mini.remainder.DataBase

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASENAME = "MY DATABASE"
val TABLENAME = "Users"
val COL_TITLE = "title"
val COL_DATE = "date"
val COL_ID = "id"
class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASENAME, null,
    1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "CREATE TABLE " + TABLENAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_TITLE + " VARCHAR(256)," + COL_DATE + " VARCHAR(256))"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //onCreate(db);
    }

    fun insertData( title: String, date: String) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_TITLE, title)
        contentValues.put(COL_DATE, date)
        val result = database.insert(TABLENAME, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun getData(): Cursor? {
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLENAME"
        return db.rawQuery(query, null)
    }
}