package com.example.testing

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testing.adapter.CategoryAdapter
import com.example.testing.model.Category
import com.example.testing.sqllite.DatabaseHelper
import com.example.testing.ui.fragments.categories.CategoriesFragment
import java.util.*

class MainActivity : AppCompatActivity() {
    var categoryAdapter: CategoryAdapter? = null
    var arrayList: ArrayList<*>? = null
    var category: Category? = null
    var ar: ArrayList<*>? = null
    var counter = 0
    var exist = false
    private var db: DatabaseHelper? = null
    var categories: ArrayList<Category>? = null
    var create_category: Button? = null
    var show_categories: Button? = null
    var buildDialog: AlertDialog.Builder? = null
    var dialogo: AlertDialog? = null
    var vista: View? = null
    var name = ""
    var key = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        create_category = findViewById<View>(R.id.create_category) as Button
        show_categories = findViewById<View>(R.id.show_categories) as Button
        create_category!!.setOnClickListener { openFormCreateCategory() }
        show_categories!!.setOnClickListener {
            val fragment = CategoriesFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.addToBackStack(null)
            transaction.replace(R.id.main_Activity, fragment)
            transaction.commit()
        }
        //db = DatabaseHelper(applicationContext)
        //categories = ArrayList()
        //categories = db!!.categories

    }

    fun openFormCreateCategory() {
        val ed_name: EditText
        val ed_key: EditText
        val cancel: Button
        val save: Button
        buildDialog = AlertDialog.Builder(this@MainActivity)
        var vista =layoutInflater.inflate(
                R.layout.form_categoria, // Custom view/ layout
                null
        );

        ed_name = vista.findViewById<View>(R.id.name_category) as EditText
        ed_key = vista.findViewById<View>(R.id.key_category) as EditText
        cancel = vista.findViewById<View>(R.id.cancel) as Button
        save = vista.findViewById<View>(R.id.save) as Button
        buildDialog!!.setView(vista)
        dialogo = buildDialog!!.create()
        dialogo.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogo.show()
        cancel.setOnClickListener { dialogo.dismiss() }
        save.setOnClickListener {
            name = ed_name.text.toString()
            key =  ed_key.text.toString()
            if (name!!.length >= 1 && key!!.length >= 1) {
                try {
                    val a = db!!.insert(name, key)
                    if (a) {
                        Toast.makeText(applicationContext, "Save successful", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(applicationContext, "Key exists 2", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(applicationContext, "Please, fill name and key category", Toast.LENGTH_LONG).show()
            }
        }
    }
/*
    fun validateCategory(category: String?, key: String): ArrayList<*>? {
        exist = false
        if (ar!!.isEmpty()) {
            addCategoryArray(counter, category, key)
        } else {
            for (ij in ar.indices) {
                val cl = ar!![ij] as Category
                if (cl.key == key) {
                    exist = true
                }
            }
            if (exist == false) {
                addCategoryArray(counter, category, key)
            }
        }
        return ar
    }
*/
    fun addCategoryArray(id: Int, category: String?, key: String?) {
        counter = id + 1
        val c = Category(counter, category!!, key!!)
        ar!!.add(c)
    }
}