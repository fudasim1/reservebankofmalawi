package com.reservebankofmalawi;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class FragmentCurrency extends Fragment {

    Context mContext;

    public FragmentCurrency() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View customView = inflater.inflate(R.layout.fragment_currency, container, false);

        mContext = getActivity();

        return customView;
    }

}
