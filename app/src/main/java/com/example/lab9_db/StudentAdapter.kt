package com.example.lab9_db

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>(){
    private var stdList : ArrayList<StudentModel> = ArrayList()
    private var onClickItem:((StudentModel)-> Unit)? = null
    private var onClickItemDelete:((StudentModel)-> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StudentViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
    )

    fun setOnClickItem(callback: ((StudentModel)-> Unit))
    {
       this.onClickItem = callback
    }

    fun setonClickItemDelete(callback: ((StudentModel)-> Unit))
    {
        this.onClickItemDelete = callback
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val std = stdList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener { onClickItem?.invoke(std) }
        holder.btnDelete.setOnClickListener{ onClickItemDelete?.invoke(std) }
    }

    override fun getItemCount(): Int {
        return stdList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(items: ArrayList<StudentModel>)
    {
        this.stdList = items
        notifyDataSetChanged()
    }

    class StudentViewHolder(var view: View) : RecyclerView.ViewHolder(view)
    {
        private var id = view.findViewById<TextView>(R.id.tvId)
        private var name = view.findViewById<TextView>(R.id.tvName)
        private var major = view.findViewById<TextView>(R.id.tvMajor)
        val btnDelete: Button = view.findViewById<Button>(R.id.btnDelete)

        fun bindView(std: StudentModel)
        {
            id.text = std.id.toString()
            name.text = std.name
            major.text = std.major
        }
    }

}