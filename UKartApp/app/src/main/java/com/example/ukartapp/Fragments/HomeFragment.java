/**
 * Created by Miguel Balderrama 11/11/2019
 * HomeFragment.java
 */

package com.example.ukartapp.Fragments;


import android.content.Intent;
import android.os.Bundle;

import ahmed.easyslider.EasySlider;
import ahmed.easyslider.SliderItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ukartapp.Activities.MainActivity;
import com.example.ukartapp.Activities.ShoppingDescriptionActivity;
import com.example.ukartapp.Adapters.ShoppingListAdapter;
import com.example.ukartapp.Models.Shopping;
import com.example.ukartapp.R;
import com.example.ukartapp.Utils.Constants;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements ListView.OnItemClickListener{

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private View view;

    private EasySlider easySlider;
    private List<SliderItem> sliderItems = new ArrayList<>();
    private ListView listView;

    private ShoppingListAdapter shoppingListAdapter;

    private List<Shopping> shoppingList = new ArrayList<>();

    private Intent inDescription;

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

        listView.setOnItemClickListener(this);

        return view;
    }

    /**
     * Generate dynamic shopping
     */
    private void setAllShopping(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Constants.SHOPPING_PATH);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Shopping currentShopping = dataSnapshot.getValue(Shopping.class);
                currentShopping.setId(dataSnapshot.getKey());

                if(currentShopping.getCustomer().equals(MainActivity.idAuthUser)){
                    shoppingList.add(currentShopping);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Create Slider
     */
    private void setImagesSlider(){
        sliderItems.add(new SliderItem("Renueva membresia", R.drawable.slider1));
        sliderItems.add(new SliderItem("OpenHouse", R.drawable.slider2));
        sliderItems.add(new SliderItem("Vinos y Licores", R.drawable.slider3));

        easySlider.setPages(sliderItems);
    }

    /**
     *
     * @param adapterView
     * @param view
     * @param i Item position
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Bundle bundle = new Bundle();
        bundle.putString("ID",shoppingList.get(i).getId());
        inDescription = new Intent(getContext(), ShoppingDescriptionActivity.class);
        inDescription.putExtras(bundle);
        startActivity(inDescription);
    }
}
