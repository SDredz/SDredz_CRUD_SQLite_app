package com.example.tyrekescottscrudsqliteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private  lateinit var txtbkNameGrab: EditText
    private  lateinit var txtbkAuthGrab: EditText
    private  lateinit var txtbkCrsGrab: EditText
    private  lateinit var txtbkIsbnGrab: EditText
    private  lateinit var addRecBtn: Button
    private  lateinit var viewRecBtn: Button

    private lateinit var helperSQLite: HelperSQLite
    private lateinit var recyclerView: RecyclerView
    private var adapter:TextbookAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        helperSQLite = HelperSQLite(this)

        addRecBtn.setOnClickListener { addBook() }
        viewRecBtn.setOnClickListener { getBooks() }
    }

    private fun getBooks() {
        val txtbkList = helperSQLite.getALLTextbook()
        Log.e("pppp", "${txtbkList.size}")

        adapter?.addItems(txtbkList)
    }

    private fun addBook() {
        val name = txtbkNameGrab.text.toString()
        val author = txtbkAuthGrab.text.toString()
        val course = txtbkCrsGrab.text.toString()
        val isbn = txtbkIsbnGrab.text.toString()

        if (name.isEmpty() || author.isEmpty() || course.isEmpty() || isbn.isEmpty()){
            Toast.makeText(this, "Please fill all fields in the form", Toast.LENGTH_SHORT).show()
        }   else{
            val txtbk = TextbookModel(name = name, author = author, course = course, isbn = isbn)
            val status = helperSQLite.insertTextbook(txtbk)
            //To check insert success or fail
            if(status >-1){
                Toast.makeText(this, "TextBook Added", Toast.LENGTH_SHORT).show()
                clearEditText()
                getBooks()
            }else{
                Toast.makeText(this, "TextBook not added", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearEditText() {
        txtbkNameGrab.setText("")
        txtbkAuthGrab.setText("")
        txtbkCrsGrab.setText("")
        txtbkIsbnGrab.setText("")
        txtbkNameGrab.requestFocus()
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TextbookAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView(){
        txtbkNameGrab = findViewById((R.id.txtbkNameGrab))
        txtbkAuthGrab = findViewById((R.id.txtbkAuthGrab))
        txtbkCrsGrab = findViewById((R.id.txtbkCrsGrab))
        txtbkIsbnGrab = findViewById((R.id.txtbkIsbnGrab))
        addRecBtn = findViewById((R.id.addRecBtn))
        viewRecBtn = findViewById((R.id.viewRecBtn))
        recyclerView = findViewById(R.id.recyclerView)
    }
}