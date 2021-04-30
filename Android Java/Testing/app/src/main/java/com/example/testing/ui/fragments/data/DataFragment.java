package com.example.testing.ui.fragments.data;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.testing.R;
import com.example.testing.adapter.CategoryAdapter;
import com.example.testing.adapter.DataAdapter;
import com.example.testing.http.Internet;
import com.example.testing.http.Request;
import com.example.testing.model.Category;
import com.example.testing.model.Data;
import com.example.testing.picker.DatePickerFragment;
import com.example.testing.sqllite.DatabaseHelper;
import com.example.testing.ui.fragments.categories.CategoriesFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DataFragment extends Fragment implements DataAdapter.OnItemClickListener{
    View rootView;
    ListView lv_news;
    ArrayList<Data> dataArrayList;
    DataAdapter dataAdapter;
    Data dt;
    TextView name_category;
    EditText datei;
    Integer id;
    String author,  title, description, url, source, image, category, language, country, published_at;

    Button btn_previous,btn_next,btn_update;
    Spinner sp_limit;
    Integer totalDataG = 0,  limit = 0, offset = 0,  lastOffeset = 0;
    ArrayList al1 ;
    ArrayAdapter ad1;

    String key_category,nam_category,newDate;
    FragmentManager al=null;
    Internet internet ;

    Integer page=0;

    Handler handler ;
    Runnable runnable;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.listview_data, container, false);
        lv_news = (ListView) rootView.findViewById(R.id.lv_news);
        name_category = (TextView) rootView.findViewById(R.id.name_category);
        btn_previous = (Button) rootView.findViewById(R.id.btn_previous);
        btn_next = (Button) rootView.findViewById(R.id.btn_next);
        btn_update = (Button) rootView.findViewById(R.id.btn_update);
        sp_limit = (Spinner) rootView.findViewById(R.id.sp_limit);
        datei = (EditText) rootView.findViewById(R.id.datei);
        newDate = "";

        al1=new ArrayList();
        al1.add(25);
        al1.add(50);
        al1.add(100);
        al1.add(350);
        al1.add(650);
        key_category = "";
        nam_category = "";
        key_category = getArguments().getString("key_category");
        nam_category = getArguments().getString("name_category");
        name_category.setText(nam_category);

        newDate = GDate();
        datei.setText(newDate);


        ad1=new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,al1);
        sp_limit.setAdapter(ad1);

        sp_limit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                limit = Integer.parseInt(sp_limit.getItemAtPosition(position).toString());
                getNews(key_category,limit,"+",newDate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNews(key_category,limit,"+",newDate);
            }
        });

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offset = offset - limit;
                getNews(key_category,limit, "-",newDate);
            }
        });
        al=getActivity().getSupportFragmentManager();

        datei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerFragment.showDatePickerDialog(al,datei);
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDate = datei.getText().toString();
                if(!newDate.isEmpty())
                {
                    offset = 0;
                    loop();
                    //getNews(key_category,limit,"00",newDate);
                }
                else
                {
                    Toast.makeText(getContext(),"Enter date",Toast.LENGTH_LONG).show();
                }

            }
        });




        return rootView;
    }

    public static String GDate()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String fech=df.format(c.getTime());

        return fech;
    }


    public void getNews(String categ, Integer li, String operator, String date)
    {

        id = 0;
        author = "";
        title = "";
        description = "";
        url = "";
        source = "";
        image = "";
        category = "";
        language = "";
        country = "";
        published_at = "";

        String parameters ="&categories="+categ+"&offset="+String.valueOf(offset)+
                "&limit="+String.valueOf(li)+"&date="+date;
        Log.e("PARAME",parameters);
        try {
            internet = new Internet();

            if(internet.checkInternet(getContext(),getActivity())==true) {
                dataArrayList = new ArrayList();
                String response = Request.getResponse(parameters);

                JSONObject jsonObject = new JSONObject(response);
                JSONArray data_g = jsonObject.getJSONArray("data");
                JSONObject pagination = new JSONObject(jsonObject.getString("pagination"));
                if (data_g != null) {
                    lastOffeset = offset;
                    totalDataG = Integer.parseInt(pagination.getString("total").toString());
                    for (int i = 0; i < data_g.length(); i++) {
                        JSONObject des_data = data_g.getJSONObject(i);
                        id = id + 1;
                        author = des_data.getString("author");
                        author = !author.isEmpty() ? author : "No author";

                        title = des_data.getString("title");
                        title = !title.isEmpty() ? title : "No title";

                        description = des_data.getString("description");
                        description = !description.isEmpty() ? description : "No description";

                        url = des_data.getString("url");
                        url = !url.isEmpty() ? url : "No url";

                        source = des_data.getString("source");
                        source = !source.isEmpty() ? source : "No source";

                        image = des_data.getString("image");
                        image = !image.isEmpty() ? image : "No image";

                        category = des_data.getString("category");
                        category = !category.isEmpty() ? category : "No category";

                        language = des_data.getString("language");
                        language = !language.isEmpty() ? language : "No language";

                        country = des_data.getString("country");
                        country = !country.isEmpty() ? country : "No country";

                        published_at = des_data.getString("published_at");
                        published_at = !published_at.isEmpty() ? published_at : "No published_at";
                        dt = new Data(id, author, title, description, url,
                                source, image, category, language, country, published_at);
                        dataArrayList.add(dt);

                        dataAdapter = new DataAdapter(getActivity(), R.layout.card_data, dataArrayList);
                        dataAdapter.setOnItemClickListener(DataFragment.this);
                        lv_news.setAdapter(dataAdapter);
                    }
                } else {
                    offset = lastOffeset + limit;
                    Toast.makeText(getContext(), "No data found", Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(getContext(), "Are you connected to wifi or do you have mobile data? ", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            offset = lastOffeset + limit;
            Toast.makeText(getContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        if(!operator.equals("00")) {
            offset = operator.equals("+") ? (offset + limit) : offset;
        }

    }

    @Override
    public void onItemClick(int position) {
        Data data1 =(Data) dataArrayList.get(position);
        Toast.makeText(getContext(),data1.getAuthor()+" || "+data1.getCategory()+" || "+String.valueOf(data1.getCountry()),
                Toast.LENGTH_LONG).show();
    }

    public void loop()
    {
        if(handler !=null) {
            handler.removeCallbacks(runnable);
        }
        page=0;
        handler = new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {

                Log.e("page",String.valueOf(page));
                offset = 0;
                getNews(key_category,limit,"00",newDate);
                handler.postDelayed(runnable,60000);
            }
        };
        handler.post(runnable);
    }



    @Override
    public void onStop() {

        try {
            if (handler != null) {
                handler.removeCallbacks(runnable);
            }
        }
        catch (Exception e)
        {
            Log.e("error",e.getMessage());
        }
        super.onStop();
    }

    @Override
    public void onPause() {

        try {
            if (handler != null) {
                handler.removeCallbacks(runnable);
            }
        }
        catch (Exception e)
        {
            Log.e("error",e.getMessage());
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {

        try {
            if (handler != null) {
                handler.removeCallbacks(runnable);
            }
        }
        catch (Exception e)
        {
            Log.e("error",e.getMessage());
        }
        super.onDestroy();
    }

}
