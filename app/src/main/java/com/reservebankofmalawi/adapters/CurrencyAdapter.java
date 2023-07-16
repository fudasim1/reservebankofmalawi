package com.reservebankofmalawi.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.makeramen.roundedimageview.RoundedImageView;
import com.reservebankofmalawi.R;
import com.reservebankofmalawi.models.CurrencyModel;

import java.util.List;

public class CurrencyAdapter extends BaseAdapter {
    public Context mContext;
    private List<CurrencyModel> mList;

    public CurrencyAdapter(Context mContext, List<CurrencyModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class ViewHolder {

        ImageView currency_placeholder;
        RoundedImageView currency_photo;
        TextView currency_name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view = convertView;
        final ViewHolder holder;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_currency, parent, false);
            holder = new ViewHolder();

            holder.currency_placeholder = view.findViewById(R.id.currency_placeholder);
            holder.currency_photo = view.findViewById(R.id.currency_photo);
            holder.currency_name = view.findViewById(R.id.currency_name);
            view.setTag(holder);


        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        final CurrencyModel model = mList.get(position);


        Glide.clear(holder.currency_photo);
        Glide.with(mContext).load(model.getCurrencyPosition()).asBitmap().thumbnail(0.1f).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                holder.currency_photo.setImageBitmap(resource);
                holder.currency_placeholder.setVisibility(View.GONE);
            }
        });
        holder.currency_name.setText(model.getCurrencyName());

        return view;
    }


}
