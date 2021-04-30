package com.example.testing.ui.fragments.data

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.testing.R
import com.example.testing.adapter.DataAdapter
import com.example.testing.http.Internet
import com.example.testing.http.Request
import com.example.testing.http.Request.getResponse
import com.example.testing.model.Data
import com.example.testing.picker.DatePickerFragment
import com.example.testing.ui.fragments.data.DataFragment
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class DataFragment : Fragment(), DataAdapter.OnItemClickListener {

    var lv_news: ListView? = null
    var dataArrayList: ArrayList<Data?>? = null
    var dataAdapter: DataAdapter? = null
    var dt: Data? = null
    var name_category: TextView? = null
    var datei: EditText? = null
    var id: Int? = null
    var author: String? = null
    var title: String? = null
    var description: String? = null
    var url: String? = null
    var source: String? = null
    var image: String? = null
    var category: String? = null
    var language: String? = null
    var country: String? = null
    var published_at: String? = null
    var btn_previous: Button? = null
    var btn_next: Button? = null
    var btn_update: Button? = null
    var sp_limit: Spinner? = null
    var totalDataG = 0
    var limit = 0
    var offset = 0
    var lastOffeset = 0
    var al1: ArrayList<Int>? = null
    var ad1: ArrayAdapter<Int>? = null
    var key_category: String? = null
    var nam_category: String? = null
    var newDate: String = ""
    var al: FragmentManager? = null
    var internet: Internet? = null
    var request: Request? = null
    var page = 0
    var handler: Handler? = null
    var runnable: Runnable? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater.inflate(R.layout.listview_data, container, false)
        lv_news = rootView.findViewById<View>(R.id.lv_news) as ListView
        name_category = rootView.findViewById<View>(R.id.name_category) as TextView
        btn_previous = rootView.findViewById<View>(R.id.btn_previous) as Button
        btn_next = rootView.findViewById<View>(R.id.btn_next) as Button
        btn_update = rootView.findViewById<View>(R.id.btn_update) as Button
        sp_limit = rootView.findViewById<View>(R.id.sp_limit) as Spinner
        datei = rootView.findViewById<View>(R.id.datei) as EditText
        newDate = ""
        al1 = ArrayList<Int>()
        al1!!.add(25)
        al1!!.add(50)
        al1!!.add(100)
        al1!!.add(350)
        al1!!.add(650)
        key_category = ""
        nam_category = ""
        key_category = arguments!!.getString("key_category")
        nam_category = arguments!!.getString("name_category")
        name_category!!.text = nam_category
        newDate = GDate()
        datei!!.setText(newDate)
        ad1 = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, al1!!)
        sp_limit!!.adapter = ad1
        sp_limit!!.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                limit = Integer.parseInt( sp_limit!!.getItemAtPosition(position).toString())

                getNews(key_category, limit, "+", newDate)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        btn_next!!.setOnClickListener { getNews(key_category, limit, "+", newDate) }
        btn_previous!!.setOnClickListener {
            offset = offset - limit
            getNews(key_category, limit, "-", newDate)
        }
        al = activity!!.supportFragmentManager
        datei!!.setOnClickListener { DatePickerFragment.showDatePickerDialog(al, datei!!) }
        btn_update!!.setOnClickListener {
            newDate = datei!!.text.toString()
            if (newDate.length >= 1) {
                offset = 0
                loop()
                //getNews(key_category,limit,"00",newDate);
            } else {
                Toast.makeText(context, "Enter date", Toast.LENGTH_LONG).show()
            }
        }
        return rootView
    }

    fun getNews(categ: String?, li: Int, operator: String, date: String?) {
        id = 0
        author = ""
        title = ""
        description = ""
        url = ""
        source = ""
        image = ""
        category = ""
        language = ""
        country = ""
        published_at = ""
        val parameters = "&categories=" + categ + "&offset=" + offset.toString() +
                "&limit=" + li.toString() + "&date=" + date
        Log.e("PARAME", parameters)
        try {
            internet = Internet()
            if (internet!!.checkInternet(context!!, activity) == true) {
                dataArrayList = ArrayList<Data?>()
                val response = request!!.getResponse(parameters)
                val jsonObject = JSONObject(response)
                val data_g = jsonObject.getJSONArray("data")
                val pagination = JSONObject(jsonObject.getString("pagination"))
                if (data_g != null) {
                    lastOffeset = offset
                    totalDataG = Integer.parseInt( pagination.getString("total").toString())
                    for (i in 0 until data_g.length()) {
                        val des_data = data_g.getJSONObject(i)
                        id = id!! + 1
                        author = des_data.getString("author")
                        author = if (author!!.length >= 1) author else "No author"
                        title = des_data.getString("title")
                        title = if (title!!.length >= 1) title else "No title"
                        description = des_data.getString("description")
                        description = if (description!!.length >= 1) description else "No description"
                        url = des_data.getString("url")
                        url = if (url!!.length >= 1) url else "No url"
                        source = des_data.getString("source")
                        source = if (source!!.length >= 1) source else "No source"
                        image = des_data.getString("image")
                        image = if (image!!.length >= 1) image else "No image"
                        category = des_data.getString("category")
                        category = if (category!!.length >= 1) category else "No category"
                        language = des_data.getString("language")
                        language = if (language!!.length >= 1) language else "No language"
                        country = des_data.getString("country")
                        country = if (country!!.length >= 1) country else "No country"
                        published_at = des_data.getString("published_at")
                        published_at = if (published_at!!.length >= 1) published_at else "No published_at"
                        dt = Data(id!!, author!!, title!!, description!!, url!!,
                                source!!, image!!, category!!, language!!, country!!, published_at!!)
                        dataArrayList!!.add(dt)
                        dataAdapter = DataAdapter(activity!!, R.layout.card_data, dataArrayList)
                        dataAdapter!!.setOnItemClickListener(this@DataFragment)
                        lv_news!!.adapter = dataAdapter
                    }
                } else {
                    offset = lastOffeset + limit
                    Toast.makeText(context, "No data found", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(context, "Are you connected to wifi or do you have mobile data? ", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            offset = lastOffeset + limit
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
        if (operator != "00") {
            offset = if (operator == "+") offset + limit else offset
        }
    }

    override fun onItemClick(position: Int) {
        val data1 = dataArrayList!![position]
        Toast.makeText(context, data1!!.author + " || " + data1.category + " || " + data1.country,
                Toast.LENGTH_LONG).show()
    }

    fun loop() {
        if (handler != null) {
            handler!!.removeCallbacks(runnable!!)
        }
        page = 0
        handler = Handler()
        runnable = Runnable {
            Log.e("page", page.toString())
            offset = 0
            getNews(key_category, limit, "00", newDate)
            handler!!.postDelayed(runnable!!, 60000)
        }
        handler!!.post(runnable!!)
    }

    override fun onStop() {
        try {
            if (handler != null) {
                handler!!.removeCallbacks(runnable!!)
            }
        } catch (e: Exception) {
            Log.e("error", e.message!!)
        }
        super.onStop()
    }

    override fun onPause() {
        try {
            if (handler != null) {
                handler!!.removeCallbacks(runnable!!)
            }
        } catch (e: Exception) {
            Log.e("error", e.message!!)
        }
        super.onPause()
    }

    override fun onDestroy() {
        try {
            if (handler != null) {
                handler!!.removeCallbacks(runnable!!)
            }
        } catch (e: Exception) {
            Log.e("error", e.message!!)
        }
        super.onDestroy()
    }

    companion object {
        fun GDate(): String {
            val c = Calendar.getInstance()
            val df = SimpleDateFormat("yyyy-MM-dd")
            return df.format(c.time)
        }
    }
}