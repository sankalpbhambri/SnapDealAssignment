package com.example.sankalp.snapdealassignment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sankalp on 08-04-2015.
 */
public class ListViewAdapter extends BaseAdapter {
    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    ImageLoader       imageLoader;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public ListViewAdapter(Context context,
                           ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
        imageLoader = new ImageLoader(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView name;
        ImageView placeImage;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.row_layout, parent, false);
        resultp = data.get(position);

        name = (TextView) itemView.findViewById(R.id.placeName);
        placeImage = (ImageView) itemView.findViewById(R.id.placeImage);

        name.setText(resultp.get(SearchActivity.TAG_NAME));
        imageLoader.DisplayImage(resultp.get(SearchActivity.TAG_PHOTO_REFERENCE), placeImage);
        // Capture ListView item click
        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                resultp = data.get(position);
                Intent intent = new Intent(context, SingleItemView.class);
             //   intent.putExtra(SearchActivity.TAG_NAME, resultp.get(SearchActivity.TAG_NAME));
                intent.putExtra(SearchActivity.TAG_PHOTO_REFERENCE, resultp.get(SearchActivity.TAG_PHOTO_REFERENCE));
                context.startActivity(intent);

            }
        });
        return itemView;
    }

}
