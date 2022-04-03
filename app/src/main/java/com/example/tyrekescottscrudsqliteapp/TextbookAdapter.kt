package com.example.tyrekescottscrudsqliteapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TextbookAdapter : RecyclerView.Adapter<TextbookAdapter.TextbookViewHolder>() {
    private var txtbkList: ArrayList<TextbookModel> = ArrayList()
    private var onClickItem: ((TextbookModel) -> Unit?)? = null
    private var onClickDeleteItem: ((TextbookModel) -> Unit?)? = null


    fun addItems(items:ArrayList<TextbookModel>){
        this.txtbkList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (TextbookModel)->Unit){
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (TextbookModel) -> Unit){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TextbookViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.txtbk_records_card, parent, false)
    )

    override fun onBindViewHolder(holder: TextbookViewHolder, position: Int) {
        val txtbk = txtbkList[position]
        holder.bindView(txtbk)
        holder.itemView.setOnClickListener { onClickItem?.invoke(txtbk) }
        holder.delRecBtn.setOnClickListener { onClickDeleteItem?.invoke(txtbk)}
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
        var delRecBtn = view.findViewById<Button>(R.id.delRecBtn)

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