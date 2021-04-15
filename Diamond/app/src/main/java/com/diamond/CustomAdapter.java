package com.diamond;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context c;
    ArrayList<Spacecraft> spacecrafts;
    LayoutInflater inflater;

    public CustomAdapter(Context c, ArrayList<Spacecraft> spacecrafts) {
        this.c = c;
        this.spacecrafts = spacecrafts;

        inflater=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return spacecrafts.size();
    }

    @Override
    public Object getItem(int position) {
        return spacecrafts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView=inflater.inflate(R.layout.model,parent,false);
        }

        ImageView img=(ImageView) convertView.findViewById(R.id.movieImage);

        Spacecraft spacecraft=spacecrafts.get(position);

        PicassoClient.downloadImage(c,spacecraft.getImageUrl(),img);

        return convertView;
    }
}
