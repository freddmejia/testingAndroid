package com.example.testing.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.testing.R
import com.example.testing.model.Data
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DataAdapter(private val mContext: Context, private val mResource: Int, objects: ArrayList<Data?>?) :
        ArrayAdapter<Data?>(mContext, mResource, objects!!) {
    private var mlistener: OnItemClickListener? = null

    private class ViewHolder {
        var cd_source: TextView? = null
        var cd_date: TextView? = null
        var cd_tittle: TextView? = null
        var cd_description: TextView? = null
        var cd_card: CardView? = null
    }

    @SuppressLint("WrongViewCast")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        /*Integer id, String author,  String title, String description, String url,
                String source, String image, String category, String language, String country, String published_at*/
        var convertView = convertView
        val id = getItem(position)!!.id
        val author = getItem(position)!!.author
        val title = getItem(position)!!.title
        val description = getItem(position)!!.description
        val url = getItem(position)!!.url
        val source = getItem(position)!!.source
        val image = getItem(position)!!.image
        val category = getItem(position)!!.category
        val language = getItem(position)!!.language
        val country = getItem(position)!!.country
        val published_at = getItem(position)!!.published_at
        val card = Data(id, author, title, description, url, source, image,
                category, language, country, published_at)
        val holder: ViewHolder
        if (convertView == null) {
            val inflater = LayoutInflater.from(mContext)
            convertView = inflater.inflate(mResource, parent, false)
            holder = ViewHolder()
            holder.cd_source = convertView.findViewById<View>(R.id.cd_source) as TextView
            holder.cd_date = convertView.findViewById<View>(R.id.cd_date) as TextView
            holder.cd_tittle = convertView.findViewById<View>(R.id.cd_tittle) as TextView
            holder.cd_description = convertView.findViewById<View>(R.id.cd_description) as TextView
            holder.cd_card = convertView.findViewById<View>(R.id.cd_card) as CardView
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        val dtStart = card.published_at // "2010-10-15T09:27:37Z";
        //"2021-01-01T08:43:43+00:00"
        val format = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ", Locale.US)
        try {
            val date = format.parse(dtStart)
            val dayOfTheWeek = DateFormat.format("EEEE", date) as String // Thursday
            val day = DateFormat.format("dd", date) as String // 20
            val monthString = DateFormat.format("MMM", date) as String // Jun
            val monthNumber = DateFormat.format("MM", date) as String // 06
            val year = DateFormat.format("yyyy", date) as String // 2013
            holder.cd_date!!.text = "$monthString $day, $year"
            //println(date)
        } catch (e: ParseException) {
            Log.e("getError", e.message.toString())
            e.printStackTrace()
        }


        /*      String day          = (String) DateFormat.format("dd",   Long.parseLong(card.getPublished_at())); // 20
        String monthString  = (String) DateFormat.format("MMM",  Long.parseLong(card.getPublished_at())); // Jun
        String monthNumber  = (String) DateFormat.format("MM",   Long.parseLong(card.getPublished_at())); // 06
        String year         = (String) DateFormat.format("yyyy", Long.parseLong(card.getPublished_at())); // 2013
*/holder.cd_source!!.text = card.source
        holder.cd_tittle!!.text = card.title
        holder.cd_description!!.text = card.description
        holder.cd_card!!.setOnClickListener {
            if (mlistener != null) {
                val pos = getPosition(card)
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