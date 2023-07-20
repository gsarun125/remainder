package com.mini.remainder.DataBase

import android.R.string
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


val DATABASENAME = "MY DATABASE"
val TABLENAME = "Users"
val COL_TITLE = "title"
val COL_DATE = "date"
val COL_ID = "id"
class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASENAME, null,
    1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "CREATE TABLE " + TABLENAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_TITLE + " VARCHAR(1000)," + COL_DATE + " VARCHAR(1000))"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //onCreate(db);
    }

    fun insertData( title: String, date: Long) {
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
        val query = "SELECT * FROM $TABLENAME ORDER BY $COL_DATE ASC;"

        return db.rawQuery(query, null)
    }

    fun delete(id: String) {

        val db = this.writableDatabase

        db.delete(TABLENAME, "id=?", arrayOf<String>(id))
        db.close()
    }
    fun update(original: String, id: String, newtitle: String) {

        // calling a method to get writable database.
        val db = this.writableDatabase
        val values = ContentValues()

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(COL_TITLE, newtitle)

        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        db.update(TABLENAME, values, "id=?", arrayOf<String>(id))
        db.close()
    }
}