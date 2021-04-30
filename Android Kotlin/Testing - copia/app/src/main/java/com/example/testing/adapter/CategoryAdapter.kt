package com.example.testing.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.testing.R
import com.example.testing.model.Category
import java.util.*

class CategoryAdapter(private val mContext: Context, private val mResource: Int, objects: ArrayList<Category?>?) : ArrayAdapter<Category?>(mContext, mResource, objects!!) {
    private var mlistener: OnItemClickListener? = null

    private class ViewHolder {
        var cd_category: TextView? = null
        var cdv_category: CardView? = null
    }

    @SuppressLint("WrongViewCast")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        /*Integer id, String author,  String title, String description, String url,
                String source, String image, String category, String language, String country, String published_at*/
        var convertView = convertView
        val id = getItem(position)!!.id
        val nombre = getItem(position)!!.nombre
        val key = getItem(position)!!.key
        val category = Category(id, nombre, key)
        val holder: ViewHolder
        if (convertView == null) {
            val inflater = LayoutInflater.from(mContext)
            convertView = inflater.inflate(mResource, parent, false)
            holder = ViewHolder()
            holder.cd_category = convertView.findViewById<View>(R.id.cd_category) as TextView
            holder.cdv_category = convertView.findViewById<View>(R.id.cdv_category) as CardView
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.cd_category!!.text = category.nombre
        holder.cdv_category!!.setOnClickListener {
            if (mlistener != null) {
                val pos = getPosition(category)
                mlistener!!.onItemClick(position)
            }
        }
        return convertView!!
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mlistener = listener
    }
}