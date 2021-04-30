package com.example.testing.ui.fragments.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.testing.R
import com.example.testing.adapter.CategoryAdapter
import com.example.testing.model.Category
import com.example.testing.sqllite.DatabaseHelper
import com.example.testing.ui.fragments.data.DataFragment
import java.util.*

class CategoriesFragment : Fragment(), CategoryAdapter.OnItemClickListener {
    //var rootView: View? = null
    var lv_categories: ListView? = null
    private var db: DatabaseHelper? = null
    var categories = ArrayList<Category?>()
    var categoryAdapter: CategoryAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.listview_categories, container, false)
        lv_categories = rootView.findViewById<View>(R.id.lv_categories) as ListView
        getCategories()
        return rootView
    }

    fun getCategories() {
        db = DatabaseHelper(context)
        //var categories = ArrayList<Category>()//Creating an empty arraylist
        //categories = ArrayList()
        categories = db!!.categories
        if (categories.size > 0) {
            categoryAdapter = CategoryAdapter(activity!!, R.layout.card_category, categories)
            categoryAdapter!!.setOnItemClickListener(this@CategoriesFragment)
            lv_categories!!.adapter = categoryAdapter
        } else {
            Toast.makeText(context, "No found categories", Toast.LENGTH_LONG).show()
        }
    }

    override fun onItemClick(position: Int) {
        val category = categories!![position]
        val bundle = Bundle()
        bundle.putString("key_category", category!!.key)
        bundle.putString("name_category", category.nombre)
        val fragment = DataFragment()
        fragment.arguments = bundle
        val transaction = fragmentManager!!.beginTransaction()
        transaction.addToBackStack(null)
        transaction.replace(R.id.listview_categories, fragment)
        transaction.commit()

        //Toast.makeText(getContext(),category.getNombre()+" || "+category.getKey()+" || "+String.valueOf(category.getId()),
        //        Toast.LENGTH_LONG).show();
    }
}