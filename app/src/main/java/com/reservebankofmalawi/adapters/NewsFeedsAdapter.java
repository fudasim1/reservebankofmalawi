package com.reservebankofmalawi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.reservebankofmalawi.R;
import com.reservebankofmalawi.models.NewsFeedsModel;

import java.util.List;

public class NewsFeedsAdapter extends BaseAdapter {
    public Context mContext;
    private List<NewsFeedsModel> mList;

    public NewsFeedsAdapter(Context mContext, List<NewsFeedsModel> mList) {
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

        RoundedImageView post_placeholder;
        RoundedImageView post_photo;
        TextView post_message;
        TextView post_time;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view = convertView;
        final ViewHolder holder;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_news, parent, false);
            holder = new ViewHolder();

            holder.post_placeholder = (RoundedImageView) view.findViewById(R.id.post_placeholder);
            holder.post_photo = (RoundedImageView) view.findViewById(R.id.post_photo);
            holder.post_message = (TextView) view.findViewById(R.id.post_message);
            holder.post_time = (TextView) view.findViewById(R.id.post_time);

            view.setTag(holder);


        } else {

            holder = (ViewHolder) convertView.getTag();
        }


        final NewsFeedsModel model = mList.get(position);


        Glide.clear(holder.post_photo);
        holder.post_photo.setImageDrawable(null);

        if (model.getPostPhoto() == null) {
            holder.post_placeholder.setVisibility(View.GONE);
            holder.post_photo.setVisibility(View.GONE);
        } else {
            holder.post_placeholder.setVisibility(View.VISIBLE);
            holder.post_photo.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(model.getPostPhoto()).asBitmap().thumbnail(0.1f).into(holder.post_photo);
        }

        if(model.getPostMessage() == null){
            holder.post_message.setVisibility(View.GONE);
        }else{
            holder.post_message.setVisibility(View.VISIBLE);
            holder.post_message.setText(model.getPostMessage());
        }

        holder.post_time.setText(model.getPostTime());

        return view;
    }


}
