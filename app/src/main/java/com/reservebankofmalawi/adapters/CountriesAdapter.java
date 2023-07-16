package com.reservebankofmalawi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.reservebankofmalawi.R;

public class CountriesAdapter extends BaseAdapter {
    Context mContext;
    int[] countryFlags;
    String[] countryNames;
    String[] countrySymbols;
    LayoutInflater inflater;

    public CountriesAdapter(Context mContext, int[] countryFlags, String[] countryNames, String[] countrySymbols) {
        this.mContext = mContext;
        this.countryFlags = countryFlags;
        this.countryNames = countryNames;
        this.countrySymbols = countrySymbols;
        this.inflater = (LayoutInflater.from(mContext));
    }

    @Override
    public int getCount() {
        return countryNames.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_countries_list, null);

        ImageView country_flag = convertView.findViewById(R.id.country_flag);
        TextView country_name = convertView.findViewById(R.id.country_name);
        TextView country_symbol = convertView.findViewById(R.id.country_symbol);

        country_flag.setImageResource(countryFlags[position]);
        country_name.setText(countryNames[position]);
        country_symbol.setText(countrySymbols[position]);

        return convertView;
    }
}
