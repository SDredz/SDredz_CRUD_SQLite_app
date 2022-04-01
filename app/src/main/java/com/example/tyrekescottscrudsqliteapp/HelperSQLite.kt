package com.example.tyrekescottscrudsqliteapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

class HelperSQLite(context:Context) : SQLiteOpenHelper(context, DATABASE_NAME, null,DATABASE_VERSION){


    companion object{

        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "textbooks.db"
        private const val TBL_TXTBK = "tbl_txtbk"
        private const val ID = "id"
        private const val TXTBK_NAME = "txtbkname"
        private const val AUTHOR = "author"
        private const val COURSE = "course"
        private const val ISBN = "isbn"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val createTblTxtbk = ("CREATE TABLE " + TBL_TXTBK + "("
                + ID + " INTEGER PRIMARY KEY," + TXTBK_NAME + " TEXT," + AUTHOR + " TEXT," +
                COURSE + " TEXT," + ISBN + " TEXT"
                + ")")
        p0?.execSQL(createTblTxtbk)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS $TBL_TXTBK")
        onCreate(p0)
    }

    fun insertTextbook(txtbk: TextbookModel): Long{
        val p0 = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, txtbk.id)
        contentValues.put(TXTBK_NAME, txtbk.name)
        contentValues.put(AUTHOR, txtbk.author)
        contentValues.put(COURSE, txtbk.course)
        contentValues.put(ISBN, txtbk.isbn)

        val success = p0.insert(TBL_TXTBK, null , contentValues)
        p0.close()
        return success
    }


    fun getALLTextbook():ArrayList<TextbookModel>{
        val txtbkList : ArrayList<TextbookModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_TXTBK"
        val p0 = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = p0.rawQuery(selectQuery, null)
        }catch (e: Exception){
            e.printStackTrace()
            p0.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var name: String
        var author: String
        var course: String
        var isbn: String

        if (cursor.moveToNext()){
            do {
                id = cursor.getInt(cursor.getColumnIndex(ID))
                name = cursor.getString(cursor.getColumnIndex(TXTBK_NAME))
                author = cursor.getString(cursor.getColumnIndex(AUTHOR))
                course = cursor.getString(cursor.getColumnIndex(COURSE))
                isbn = cursor.getString(cursor.getColumnIndex(ISBN))

                val txtbk = TextbookModel(id = id, name = name, author = author, course = course, isbn = isbn)
                txtbkList.add(txtbk)
            }while (cursor.moveToNext())
        }

        return txtbkList
    }


}