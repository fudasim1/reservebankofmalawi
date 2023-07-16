package com.reservebankofmalawi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.reservebankofmalawi.ViewNewsPostActivity;
import com.reservebankofmalawi.adapters.NewsFeedsAdapter;
import com.reservebankofmalawi.models.NewsFeedsModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.supercharge.shimmerlayout.ShimmerLayout;

public class FragmentNews extends Fragment {

    Context mContext;
    ShimmerLayout shimmerLayout;

    SwipeRefreshLayout swipeRefreshLayout;
    ListView listView;
    List<NewsFeedsModel> mList;
    NewsFeedsAdapter mAdapter;

    LinearLayout noFeedsLayout;

    public FragmentNews() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View customView = inflater.inflate(R.layout.fragment_news, container, false);

        mContext = getActivity();

        listView = (ListView) customView.findViewById(R.id.listView);
        shimmerLayout = (ShimmerLayout) customView.findViewById(R.id.shimmerLayout);

        noFeedsLayout = (LinearLayout) customView.findViewById(R.id.noFeedsLayout);
        noFeedsLayout.setVisibility(View.GONE);

        mList = new ArrayList<>();

        mAdapter = new NewsFeedsAdapter(mContext, mList);

        listView.setAdapter(mAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) customView.findViewById(R.id.swipeRefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(mContext, "Refreshing News Feeds", Toast.LENGTH_SHORT).show();

                swipeRefreshLayout.setRefreshing(true);
                getPageUpdates();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                NewsFeedsModel model =  (NewsFeedsModel) parent.getItemAtPosition(position);

                if(model.getPostPhoto() == null){
                    Intent intent = new Intent(getActivity(), ViewNewsPostActivity.class);
                    intent.putExtra("post_photo", model.getPostPhoto());
                    intent.putExtra("post_bitmap", (Parcelable[]) null);
                    intent.putExtra("post_message", model.getPostMessage());
                    intent.putExtra("post_time", model.getPostTime());
                    startActivity(intent);

                }else{
                    RoundedImageView post_photo = (RoundedImageView) view.findViewById(R.id.post_photo);

                    post_photo.setDrawingCacheEnabled(true);
                    post_photo.buildDrawingCache();

                    Intent intent = new Intent(getActivity(), ViewNewsPostActivity.class);
                    intent.putExtra("post_photo", model.getPostPhoto());
                    intent.putExtra("post_bitmap",(Parcelable[]) null);
                    intent.putExtra("post_message", model.getPostMessage());
                    intent.putExtra("post_time", model.getPostTime());
                    startActivity(intent);
                }
            }

        });

        getPageUpdates();

        return customView;
    }

    private void getPageUpdates() {
        if (mAdapter.getCount() == 0) {
            shimmerLayout.startShimmerAnimation();
            shimmerLayout.setVisibility(View.VISIBLE);
            noFeedsLayout.setVisibility(View.GONE);
        } else {
            shimmerLayout.setVisibility(View.GONE);
            noFeedsLayout.setVisibility(View.GONE);
        }

        String URL = "https://graph.facebook.com/v17.0/105078415986109/feed?fields=message,full_picture,created_time&access_token=EAAxOxD9YS4cBAPKWWlj2dVrWA0AaXZBBhXZCO3zoFr61trYZAf1QQTOc4xKA9dYbndY9YeBHM10j1tf08m5qfEzSBJqmhj3GaZANexfxxSe85MNZCgKil8vvqxZB2l2V39NQVBIDZBBWcCxZBW8ZAMvDfTh27YqGqeT3HBp9JiPwZBqQstzQx7prgQ&limit=20";

        AndroidNetworking.get(URL)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jObject) {
                        Log.e("Response ", jObject.toString());

                        swipeRefreshLayout.setRefreshing(false);
                        shimmerLayout.setVisibility(View.GONE);
                        shimmerLayout.stopShimmerAnimation();

                        try {
                            mList.clear();

                            JSONArray c = jObject.getJSONArray("data");
                            for (int i = 0; i < c.length(); i++) {

                                JSONObject obj = c.getJSONObject(i);

                                String postMessage = null;
                                String postPhoto = null;

                                if (obj.has("message")) {
                                    postMessage = obj.getString("message");
                                }

                                if (obj.has("full_picture")) {
                                    postPhoto = obj.getString("full_picture");
                                }

                                String postTime = obj.getString("created_time");

                                mList.add(new NewsFeedsModel(postMessage, postPhoto, postTime));

                            }
                            mAdapter.notifyDataSetChanged();


                        } catch (Exception e) {
                            Log.e("Error ", e.toString());

                        }

                    }


                    @Override
                    public void onError(ANError error) {
                        Log.e("Error ", error.toString());
                        Toast.makeText(mContext, "Sorry, Network Is Unavailable", Toast.LENGTH_SHORT).show();

                        swipeRefreshLayout.setRefreshing(false);
                        shimmerLayout.setVisibility(View.GONE);
                        shimmerLayout.stopShimmerAnimation();

                        if (mAdapter.getCount() == 0) {
                            noFeedsLayout.setVisibility(View.VISIBLE);

                        } else {
                            noFeedsLayout.setVisibility(View.GONE);
                        }
                    }
                });


    }

}
