package com.example.lab9_db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context:Context) : SQLiteOpenHelper(context, DATABASE_NAME,null ,DATABASE_VERSION) {

    companion object
    {
        private const val DATABASE_VERSION =  1
        private const val DATABASE_NAME = "student.db"
        private const val TBL_STUDENT = "tbl_student"
        private const val ID = "id"
        private const val NAME = "name"
        private const val MAJOR = "major"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblStudent = ("CREATE TABLE " + TBL_STUDENT +
                "(" + ID + " INTEGER PRIMARY KEY," + NAME + " TEXT," +
                MAJOR + " TEXT" + ")")
        db?.execSQL(createTblStudent)
    }

    override fun onUpgrade(db: SQLiteDatabase?, old: Int, new: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_STUDENT")
        onCreate(db)
    }

    fun insertStudent(std: StudentModel): Long
    {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(ID, std.id)
        contentValues.put(NAME, std.name)
        contentValues.put(MAJOR, std.major)

        val success = db.insert(TBL_STUDENT, null, contentValues)
        db.close()

        return success
    }

    @SuppressLint("Range")
    fun getAllStudents(): ArrayList<StudentModel>
    {
        val stdList: ArrayList<StudentModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_STUDENT"
        val db = this.readableDatabase

        val cursor: Cursor?

        try
        {
            cursor = db.rawQuery(selectQuery,null)
        }
        catch (e: Exception)
        {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var student = StudentModel()

        if(cursor.moveToFirst())
        {
            do {
                student.id = cursor.getInt(cursor.getColumnIndex("id"))
                student.name = cursor.getString(cursor.getColumnIndex("name"))
                student.major = cursor.getString(cursor.getColumnIndex("major"))

                val studentToAdd = StudentModel(student.id, student.name, student.major)

                stdList.add(studentToAdd)
            } while (cursor.moveToNext())
        }
        return stdList
    }

    fun updateStudent(std: StudentModel): Int
    {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, std.id)
        contentValues.put(NAME, std.name)
        contentValues.put(MAJOR, std.major)

        val success = db.update(TBL_STUDENT, contentValues, "id="+std.id,null)
        db.close()

        return success
    }


    fun deleteStudent(id: Int): Int
    {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(ID, id)

        val success = db.delete(TBL_STUDENT, "id=$id",null)
        db.close()
        return success
    }


}