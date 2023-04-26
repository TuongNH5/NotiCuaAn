package com.example.android.salary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R

class CategoryAdapter(private val categories: List<Category>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView: TextView = itemView.findViewById(R.id.title)
        val subcategoryView: RecyclerView = itemView.findViewById(R.id.subcategories)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.titleView.text = category.title
//        holder.subcategoryView.adapter = CategoryAdapter(category.subcategories)

        if (category.subcategories.isNotEmpty()) {
            holder.subcategoryView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = CategoryAdapter(category.subcategories)
            }
        } else {
            holder.subcategoryView.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int {
        return categories.size
    }
}
