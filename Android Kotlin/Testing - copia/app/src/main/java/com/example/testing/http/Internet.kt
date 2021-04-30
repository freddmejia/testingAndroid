package com.example.testing.http

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class Internet {
    var connected = false
    fun checkInternet(cgh: Context, activity: Activity?): Boolean {
        val connectivityManager = cgh.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connected = if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.state == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!.state == NetworkInfo.State.CONNECTED) {
            true
        } else {
            false
        }
        return connected
    }

    init {
        connected = false
    }
}