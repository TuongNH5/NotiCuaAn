package com.example.android.salary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R

class SalaryActivity : AppCompatActivity() {
    val categories = listOf(
        Category("Category 1", listOf(
            Category("Subcategory 1.1", emptyList()),
            Category("Subcategory 1.2", emptyList())
        )),
        Category("Category 2", listOf(
            Category("Subcategory 2.1", emptyList()),
            Category("Subcategory 2.2", listOf(
                Category("Subcategory 2.2.1", emptyList()),
                Category("Subcategory 2.2.2", emptyList())
            ))
        )),
                Category("Category 3", listOf(
            Category("Subcategory 2.1", emptyList()),
            Category("Subcategory 2.2", listOf(
                Category("Subcategory 2.2.1", emptyList()),
                Category("Subcategory 2.2.2", emptyList())
            ))
        ))
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salary)
        initList()


    }
  private fun  initList(){
        val recyclerView = findViewById<RecyclerView>(R.id.category_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = CategoryAdapter(categories)
        recyclerView.adapter = adapter
    }
}