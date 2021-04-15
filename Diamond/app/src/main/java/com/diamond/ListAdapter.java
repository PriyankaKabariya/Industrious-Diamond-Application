package com.diamond;

import android.content.Context;

import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    Context context;
    private final String [] values;
    private final String [] price;
    private final String [] carat;
    private final String [] cut;
    private final String [] clarity;
    private final String [] measurement;
    private final int [] images;

    public ListAdapter(Context context, String [] values, String [] price, String [] carat, String [] cut, String [] clarity, String [] measurement, int [] images){
        //super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        this.values = values;
        this.price = price;
        this.carat = carat;
        this.cut = cut;
        this.clarity = clarity;
        this.measurement = measurement;
        this.images = images;
    }

    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.single_list_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.aNametxt);
            viewHolder.txtprice = (TextView) convertView.findViewById(R.id.txtprice);
            viewHolder.txtcarat = (TextView) convertView.findViewById(R.id.txtcarat);
            viewHolder.txtcut = (TextView) convertView.findViewById(R.id.txtcut);
            viewHolder.txtclarity = (TextView) convertView.findViewById(R.id.txtclarity);
            viewHolder.txtmeasurement = (TextView) convertView.findViewById(R.id.txtmeasurement);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.appIconIV);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.txtName.setText(Html.fromHtml("Polish: <b>"+values[position]+"</b>"));
        viewHolder.txtprice.setText(Html.fromHtml("Price: <b>"+price[position]+"</b>"));
        viewHolder.txtcarat.setText(Html.fromHtml("Carat: <b>"+carat[position]+"</b>"));
        viewHolder.txtcut.setText(Html.fromHtml("Cut: <b>"+cut[position]+"</b>"));
        viewHolder.txtclarity.setText(Html.fromHtml("Clarity: <b>"+clarity[position]+"</b>"));
        viewHolder.txtmeasurement.setText(Html.fromHtml("<b>"+measurement[position]+"</b>"));
        viewHolder.icon.setImageResource(images[position]);

        return convertView;
    }

    private static class ViewHolder {

        TextView txtName;
        TextView txtprice;
        TextView txtcarat;
        TextView txtcut;
        TextView txtclarity;
        TextView txtmeasurement;
        ImageView icon;

    }

}

