package com.example.testing.http

import android.os.StrictMode
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class Request {
    val HOST = "http://api.mediastack.com/v1/news?"
    val KEY = "5f0187f7e8bf4d61548d43f3045faf05"

    //@Throws(Exception::class)
    fun getResponse(parameters: String): String {
        val all_par = "access_key=$KEY$parameters"
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val response = StringBuilder()
        val url = URL(HOST + all_par)
        val connection = url.openConnection() as HttpURLConnection
        val code = connection.responseCode
        if (code == 200) {
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            var line: String?

            while (reader.readLine().also { line = it } != null) response.append(line)
/*
* while ((line = reader.readLine()) != null)
                    response.append(line);
* */

        } else {
            val reader = BufferedReader(InputStreamReader(connection.errorStream))
            val line: String
            line = reader.readLine()
            response.append(line)
        }
        connection.disconnect()
        return response.toString()
    }

}
