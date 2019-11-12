/**
 * Created by Miguel Balderrama 11/11/2019
 * HomeFragment.java
 */

package com.example.ukartapp.Fragments;


import android.os.Bundle;

import ahmed.easyslider.EasySlider;
import ahmed.easyslider.SliderItem;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ukartapp.Adapters.ShoppingListAdapter;
import com.example.ukartapp.Models.Shopping;
import com.example.ukartapp.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private View view;

    private EasySlider easySlider;
    private List<SliderItem> sliderItems = new ArrayList<>();
    private ListView listView;

    private ShoppingListAdapter shoppingListAdapter;

    private List<Shopping> shoppingList = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        easySlider = view.findViewById(R.id.slider);
        listView = view.findViewById(R.id.listShops);

        setAllShopping();
        setImagesSlider();

        shoppingListAdapter = new ShoppingListAdapter(getContext(),shoppingList,R.layout.list_shopping_item);
        listView.setAdapter(shoppingListAdapter);

        return view;
    }

    /**
     * Generate static shopping
     * TODO: Make dynamic with Firebase
     */
    private void setAllShopping(){
        shoppingList.add(new Shopping("Miguel","11/11/2019","250"));
        shoppingList.add(new Shopping("Martín","11/11/2019","250"));
        shoppingList.add(new Shopping("Rubí","11/11/2019","250"));
        shoppingList.add(new Shopping("Karla","11/11/2019","250"));
    }

    private void setImagesSlider(){
        sliderItems.add(new SliderItem("Renueva membresia", R.drawable.slider1));
        sliderItems.add(new SliderItem("OpenHouse", R.drawable.slider2));
        sliderItems.add(new SliderItem("Vinos y Licores", R.drawable.slider3));

        easySlider.setPages(sliderItems);
    }

}
