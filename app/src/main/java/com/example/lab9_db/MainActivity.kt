package com.example.lab9_db

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity()
{
    private lateinit var etName: EditText
    private lateinit var etMajor: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: StudentAdapter? = null
    private var std:StudentModel? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)

        btnAdd.setOnClickListener{addStudent()}
        btnView.setOnClickListener { getStudents() }
        btnUpdate.setOnClickListener{updateStudent()}

        adapter?.setOnClickItem {
            Toast.makeText(this , it.name, Toast.LENGTH_SHORT).show()
            etName.setText(it.name)
            etMajor.setText(it.major)
            std = it
        }

        adapter?.setonClickItemDelete {
            deleteStudent(it.id)
        }
    }

    private fun deleteStudent(id: Int){
        sqLiteHelper.deleteStudent(id)
        getStudents()
    }

    private fun updateStudent() {
        val name = etName.text.toString()
        val major = etMajor.text.toString()

        if(name == std?.name && major == std?.major)
        {
            Toast.makeText(this , "Record not changed", Toast.LENGTH_SHORT).show()
        }

        val std = StudentModel(id = std!!.id, name = name, major = major)
        val status = sqLiteHelper.updateStudent(std)

        if(status > -1)
        {
            clearEditText()
            getStudents()
        }
        else
        {
            Toast.makeText(this , "Update failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getStudents() {
        val stdList = sqLiteHelper.getAllStudents()
        Log.e("number of students: ", "${stdList.size}")

        adapter?.addItems(stdList)
    }

    private fun addStudent() {
        val name = etName.text.toString()
        val major = etMajor.text.toString()

        if(name.isEmpty() || major.isEmpty())
        {
            Toast.makeText(this , "some fields are empty", Toast.LENGTH_SHORT).show()
        }
        else
        {
            val std = StudentModel(name = name,major = major)
            val status = sqLiteHelper.insertStudent(std)

            if(status > -1)
            {
                Toast.makeText(this , "Student Added", Toast.LENGTH_SHORT).show()
                clearEditText()
                getStudents()
            }
            else
            {
                Toast.makeText(this , "STUDENT NOT ADDED", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearEditText() {
        etName.setText("")
        etMajor.setText("")
        etName.requestFocus()
    }

    private fun initRecyclerView()
    {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView()
    {
        etName = findViewById(R.id.etName)
        etMajor = findViewById(R.id.etMajor)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.rvView)
    }
}