package com.example.testing.http;

import android.os.StrictMode;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Request {
    public static final String HOST = "http://api.mediastack.com/v1/news?";
    public static final String KEY = "5f0187f7e8bf4d61548d43f3045faf05";

    public static final String getResponse(String parameters) throws Exception
    {
        String all_par = "access_key="+KEY+parameters;

        StrictMode.ThreadPolicy policy = new StrictMode.
                ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StringBuilder response = new StringBuilder();
        URL url = new URL(HOST + all_par);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            int code = connection.getResponseCode();
            if (code == 200)
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;

                while ((line = reader.readLine()) != null)
                    response.append(line);
            }
            else
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String line;
                line=reader.readLine();
                response.append(line);
            }

        connection.disconnect();
        return response.toString();
    }

}
