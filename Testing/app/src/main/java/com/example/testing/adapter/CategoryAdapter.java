package com.example.testing.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.testing.R;
import com.example.testing.model.Category;

import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter<Category> {
    private Context mContext;
    private int mResource;
    private OnItemClickListener mlistener;

    private static class ViewHolder {

        TextView cd_category;
        CardView cdv_category;

    }

    public CategoryAdapter(Context context, int resource, ArrayList<Category> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @SuppressLint("WrongViewCast")
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        /*Integer id, String author,  String title, String description, String url,
                String source, String image, String category, String language, String country, String published_at*/


        int id = getItem(position).getId();
        String nombre = getItem(position).getNombre();
        String key = getItem(position).getKey();

        final Category category = new Category(id,nombre,key);

        ViewHolder holder;
        if(convertView == null){


            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);


            holder= new ViewHolder();
            holder.cd_category=(TextView)convertView.findViewById(R.id.cd_category);
            holder.cdv_category=(CardView) convertView.findViewById(R.id.cdv_category);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.cd_category.setText(category.getNombre());

        holder.cdv_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mlistener!=null) {
                    int pos=getPosition(category);
                    mlistener.onItemClick(position);
                }

            }
        });

        return convertView;
    }


    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mlistener=listener;
    }

}
