package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testing.adapter.CategoryAdapter;
import com.example.testing.model.Category;
import com.example.testing.sqllite.DatabaseHelper;
import com.example.testing.ui.fragments.categories.CategoriesFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    CategoryAdapter categoryAdapter;
    ArrayList arrayList;
    Category category;
    ArrayList ar;
    Integer counter = 0;
    boolean exist = false;
    private DatabaseHelper db;
    ArrayList <Category> categories;
    Button create_category, show_categories;

    AlertDialog.Builder buildDialog;
    AlertDialog dialogo = null;
    View vista = null;
    String name ="", key="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        create_category = (Button) findViewById(R.id.create_category);
        show_categories = (Button) findViewById(R.id.show_categories);

        create_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFormCreateCategory();
            }
        });

        show_categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoriesFragment fragment = new CategoriesFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_Activity, fragment);
                transaction.commit();
            }
        });

        db = new DatabaseHelper(getApplicationContext());
        categories = new ArrayList<>();
        categories = db.getCategories();

        /*if(categories.isEmpty())
        {
            db.insert("GENERAL","general");
            Toast.makeText(getApplicationContext(),"INSERT",Toast.LENGTH_LONG).show();
        }
        else
        {
            for (int kk = 0; kk<categories.size();kk++)
            {
                Category ckk = (Category) categories.get(kk);
                Log.e("GETCAT",ckk.getNombre()+" // "+ckk.getKey());
            }

            Toast.makeText(getApplicationContext(),"SELECT",Toast.LENGTH_LONG).show();
        }*/
        //callApi();
    }

    /*public void callApi()
    {
        arrayList = new ArrayList();
        String cat = "";
        //String parameters ="&keywords=Wall street -wolf&categories=-entertainment&sort=popularity";
        String parameters ="&categories=-general,business,entertainment,health,science,sports,technology";
        try {
            ar = new ArrayList();
            String response = Request.getResponse(parameters);

            JSONObject jsonObject = new JSONObject(response);
            JSONArray data = jsonObject.getJSONArray("data");
            if(data != null) {
                for(int i = 0 ; i < data.length() ; i++) {
                    JSONObject des_data = data.getJSONObject(i);
                    Toast.makeText(getApplicationContext(),String.valueOf(des_data.get("title")),Toast.LENGTH_LONG).show();
                    Log.e("MainActivity",String.valueOf(des_data.get("title")));
                    cat = String.valueOf(des_data.get("category"));
                    validateCategory(cat);
                    //if()
                }
            }

            for (int cats = 0; cats< ar.size(); cats++)
            {
                Category kk = (Category) ar.get(cats);
                Log.e("CATEGORY",kk.getNombre());
            }

            //String data = cor.getString("data");
            //Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    public void openFormCreateCategory()
    {
        EditText ed_name , ed_key ;

        Button cancel, save;

        buildDialog=new AlertDialog.Builder(MainActivity.this);
        vista=getLayoutInflater().inflate(R.layout.form_categoria,null);
        ed_name=(EditText) vista.findViewById(R.id.name_category);
        ed_key=(EditText) vista.findViewById(R.id.key_category);

        cancel=(Button) vista.findViewById(R.id.cancel);
        save=(Button) vista.findViewById(R.id.save);

        buildDialog.setView(vista);
        dialogo=buildDialog.create();
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name =  ed_name.getText().toString().toUpperCase();
                key = ed_key.getText().toString().toLowerCase();
                if(!name.isEmpty() && !key.isEmpty())
                {
                    try{
                        boolean a =
                                db.insert(name,key);
                        if(a)
                        {
                            Toast.makeText(getApplicationContext(),"Save successful",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Key exists 2",Toast.LENGTH_LONG).show();
                        }
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please, fill name and key category",Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    public ArrayList validateCategory(String category, String key){

        exist = false;
        if(ar.isEmpty())
        {
            addCategoryArray(counter ,category, key);
        }
        else
        {
            for (int ij = 0 ; ij< ar.size(); ij++)
            {
                Category cl =(Category) ar.get(ij);
                if(cl.getKey().equals(key))
                {
                    exist = true;
                }
            }
            if(exist == false)
            {
                addCategoryArray(counter ,category, key);
            }
        }
        return ar;
    }

    public void addCategoryArray(Integer id ,String category, String key)
    {
        counter = id + 1;
        Category c = new Category(counter,category, key);
        ar.add(c);
    }
}