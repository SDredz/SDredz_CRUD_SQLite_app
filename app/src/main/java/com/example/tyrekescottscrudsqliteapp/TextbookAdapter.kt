package com.example.tyrekescottscrudsqliteapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TextbookAdapter : RecyclerView.Adapter<TextbookAdapter.TextbookViewHolder>() {
    private var txtbkList: ArrayList<TextbookModel> = ArrayList()

    fun addItems(items:ArrayList<TextbookModel>){
        this.txtbkList = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TextbookViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.txtbk_records_card, parent, false)
    )

    override fun onBindViewHolder(holder: TextbookViewHolder, position: Int) {
        val txtbk = txtbkList[position]
        holder.bindView(txtbk)

    }

    override fun getItemCount(): Int {
        return txtbkList.size
    }

    class TextbookViewHolder(var view: View): RecyclerView.ViewHolder(view){
        private var id = view.findViewById<TextView>(R.id.txtbkId)
        private var name = view.findViewById<TextView>(R.id.txtbkName)
        private var author = view.findViewById<TextView>(R.id.txtbkAuth)
        private var course = view.findViewById<TextView>(R.id.txtbkCrs)
        private var isbn = view.findViewById<TextView>(R.id.txtbkIsbn)
        private var delRecBtn = view.findViewById<Button>(R.id.delRecBtn)

        fun bindView(txtbk: TextbookModel){
            id.text = txtbk.id.toString()
            name.text = txtbk.name
            author.text = txtbk.author
            course.text = txtbk.course
            isbn.text = txtbk.isbn
        }
//hopefully it work
    }
}