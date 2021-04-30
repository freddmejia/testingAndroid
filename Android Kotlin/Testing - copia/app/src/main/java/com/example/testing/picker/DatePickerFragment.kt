package com.example.testing.picker

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import java.util.*

class DatePickerFragment : DialogFragment() {
    private var listener: OnDateSetListener? = null
    fun setListener(listener: OnDateSetListener?) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c[Calendar.YEAR]
        val month = c[Calendar.MONTH]
        val day = c[Calendar.DAY_OF_MONTH]
        return DatePickerDialog(activity!!, listener, year, month, day)
    }

    companion object {
        fun newInstance(listener: OnDateSetListener?): DatePickerFragment {
            val fragment = DatePickerFragment()
            fragment.setListener(listener)
            return fragment
        }

       // @kotlin.jvm.JvmStatic
        fun showDatePickerDialog(activity: FragmentManager?, tv: TextView) {
            val selectedDate = arrayOf("")
            val newFragment = newInstance { datePicker, year, month, day ->
                var me = ""
                me = (month + 1).toString()
                if (month + 1 < 10) {
                    me = "0" + (month + 1)
                }
                selectedDate[0] = "$year-$me-$day"
                tv.text = selectedDate[0]
            }
            newFragment.show(activity!!, "datePicker")
        }
    }
}