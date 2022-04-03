package com.example.tyrekescottscrudsqliteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private  lateinit var txtbkNameGrab: EditText
    private  lateinit var txtbkAuthGrab: EditText
    private  lateinit var txtbkCrsGrab: EditText
    private  lateinit var txtbkIsbnGrab: EditText
    private  lateinit var addRecBtn: Button
    private  lateinit var viewRecBtn: Button
    private  lateinit var updateRecBtn: Button


    private lateinit var helperSQLite: HelperSQLite
    private lateinit var recyclerView: RecyclerView
    private var adapter:TextbookAdapter? = null
    private var txtbk:TextbookModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        helperSQLite = HelperSQLite(this)

        addRecBtn.setOnClickListener { addBook() }
        viewRecBtn.setOnClickListener { getBooks() }
        updateRecBtn.setOnClickListener { updateBooks() }

        adapter?.setOnClickItem {
            Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()

            txtbkNameGrab.setText(it.name)
            txtbkAuthGrab.setText(it.author)
            txtbkCrsGrab.setText(it.course)
            txtbkIsbnGrab.setText(it.isbn)
            txtbk = it
        }

        adapter?.setOnClickDeleteItem {
            deleteBook(it.id)
        }

    }

    private fun updateBooks() {
        val name = txtbkNameGrab.text.toString()
        val author = txtbkAuthGrab.text.toString()
        val course = txtbkCrsGrab.text.toString()
        val isbn = txtbkIsbnGrab.text.toString()

        if(name == txtbk?.name && author == txtbk?.author && course == txtbk?.course && isbn == txtbk?.isbn){
            Toast.makeText(this, "No changes to record...", Toast.LENGTH_SHORT).show()
            return
        }

        if(txtbk == null) return

        val txtbk = TextbookModel(id = txtbk!!.id, name = name, author = author, course = course, isbn = isbn)
        val status = helperSQLite.upadteTextbook(txtbk)
        if(status > -1) {
            clearEditText()
            getBooks()
        }else {
            Toast.makeText(this, "Record update failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteBook(id: Int?){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete record?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){ dialog, _ ->
            helperSQLite.deleteBookById(id!!)
            getBooks()
            dialog.dismiss()
        }
        builder.setNegativeButton("No"){ dialog, _ ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()

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
        updateRecBtn = findViewById((R.id.updateRecBtn))
        recyclerView = findViewById(R.id.recyclerView)
    }
}