/**
 * Created by Miguel Balderrama 11/11/2019
 * HomeFragment.java
 */

package com.example.ukartapp.Fragments;


import android.os.Bundle;

import ahmed.easyslider.EasySlider;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ukartapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private View view;

    private EasySlider easySlider;
    private ListView listView;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        easySlider = view.findViewById(R.id.slider);
        listView = view.findViewById(R.id.listShops);

        return view;
    }

}
