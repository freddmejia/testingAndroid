package com.example.testing.ui.fragments.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.testing.R;
import com.example.testing.adapter.CategoryAdapter;
import com.example.testing.model.Category;
import com.example.testing.sqllite.DatabaseHelper;
import com.example.testing.ui.fragments.data.DataFragment;

import java.util.ArrayList;

public class CategoriesFragment  extends Fragment implements CategoryAdapter.OnItemClickListener{
    View rootView;
    ListView lv_categories;
    private DatabaseHelper db;
    ArrayList<Category> categories;
    CategoryAdapter categoryAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.listview_categories, container, false);
        lv_categories = (ListView) rootView.findViewById(R.id.lv_categories);
        getCategories();
        return rootView;
    }
    public void getCategories()
    {
        db = new DatabaseHelper(getContext());
        categories = new ArrayList<>();
        categories = db.getCategories();
        if(categories.size()>0)
        {
            categoryAdapter = new CategoryAdapter(getActivity(), R.layout.card_category, categories);
            categoryAdapter.setOnItemClickListener(CategoriesFragment.this);
            lv_categories.setAdapter(categoryAdapter);
        }
        else
        {
            Toast.makeText(getContext(),"No found categories",Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onItemClick(int position) {
        Category category =(Category) categories.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("key_category", category.getKey());
        bundle.putString("name_category", category.getNombre());

        DataFragment fragment = new DataFragment();
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.listview_categories, fragment);
        transaction.commit();

        //Toast.makeText(getContext(),category.getNombre()+" || "+category.getKey()+" || "+String.valueOf(category.getId()),
        //        Toast.LENGTH_LONG).show();
    }
}
