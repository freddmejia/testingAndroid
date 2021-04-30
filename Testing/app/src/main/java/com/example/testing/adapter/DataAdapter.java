package com.example.testing.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.testing.model.Data;
import com.example.testing.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.text.format.DateFormat;

public class DataAdapter extends ArrayAdapter<Data> {
    private Context mContext;
    private int mResource;
    private OnItemClickListener mlistener;

    private static class ViewHolder {

        TextView cd_source, cd_date, cd_tittle, cd_description;
        CardView cd_card;

    }

    public DataAdapter(Context context, int resource, ArrayList<Data> objects) {
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
        String author = getItem(position).getAuthor();
        String title = getItem(position).getTitle();
        String description = getItem(position).getDescription();
        String url = getItem(position).getUrl();
        String source = getItem(position).getSource();
        String image = getItem(position).getImage();
        String category = getItem(position).getCategory();
        String language = getItem(position).getLanguage();
        String country = getItem(position).getCountry();
        String published_at = getItem(position).getPublished_at();

        final Data card = new Data(id, author, title, description, url, source, image,
                category, language, country, published_at);

        ViewHolder holder;
        if(convertView == null){


            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);


            holder= new ViewHolder();
            holder.cd_source=(TextView)convertView.findViewById(R.id.cd_source);
            holder.cd_date=(TextView)convertView.findViewById(R.id.cd_date);
            holder.cd_tittle=(TextView)convertView.findViewById(R.id.cd_tittle);
            holder.cd_description=(TextView)convertView.findViewById(R.id.cd_description);

            holder.cd_card=(CardView) convertView.findViewById(R.id.cd_card);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        String dtStart = card.getPublished_at(); // "2010-10-15T09:27:37Z";
        //"2021-01-01T08:43:43+00:00"
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ", Locale.US);
        try {
            Date date = format.parse(dtStart);
            String dayOfTheWeek = (String) DateFormat.format("EEEE", date); // Thursday
            String day          = (String) DateFormat.format("dd",   date); // 20
            String monthString  = (String) DateFormat.format("MMM",  date); // Jun
            String monthNumber  = (String) DateFormat.format("MM",   date); // 06
            String year         = (String) DateFormat.format("yyyy", date); // 2013


            holder.cd_date.setText(monthString+ " "+day+", "+year);
            System.out.println(date);
        } catch (ParseException e) {
            Log.e("getError",String.valueOf(e.getMessage()));
            e.printStackTrace();
        }


  /*      String day          = (String) DateFormat.format("dd",   Long.parseLong(card.getPublished_at())); // 20
        String monthString  = (String) DateFormat.format("MMM",  Long.parseLong(card.getPublished_at())); // Jun
        String monthNumber  = (String) DateFormat.format("MM",   Long.parseLong(card.getPublished_at())); // 06
        String year         = (String) DateFormat.format("yyyy", Long.parseLong(card.getPublished_at())); // 2013
*/
        holder.cd_source.setText(card.getSource());

        holder.cd_tittle.setText(card.getTitle());
        holder.cd_description.setText(card.getDescription());

        holder.cd_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mlistener!=null) {
                    int pos=getPosition(card);
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
