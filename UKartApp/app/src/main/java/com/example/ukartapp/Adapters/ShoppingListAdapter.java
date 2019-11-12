package com.example.ukartapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ukartapp.Models.Shopping;
import com.example.ukartapp.R;

import java.util.List;

public class ShoppingListAdapter extends BaseAdapter {

    private Context context;
    private List<Shopping> shoppingList;
    private int layout;

    public ShoppingListAdapter(){}

    public ShoppingListAdapter(Context context, List<Shopping> shoppingList, int layout){
        this.context = context;
        this.shoppingList = shoppingList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return shoppingList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imgIcon;
        TextView txtDate, txtQuantity, txtPrice;

        Shopping currentShopping = this.shoppingList.get(position);

        if(convertView == null){ //NO EXISTE LA FILA, HAY QUE CREARLA
            LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
            convertView = layoutInflater.inflate(layout, parent, false);
        }

        imgIcon = convertView.findViewById(R.id.imgIconList);
        txtDate = convertView.findViewById(R.id.txtDateList);
        txtQuantity = convertView.findViewById(R.id.txtQuantityList);
        txtPrice = convertView.findViewById(R.id.txtPriceList);

//        imgIcon.setImageResource(currentShopping.getIcon());
        txtDate.setText(currentShopping.getDate());
        txtQuantity.setText(currentShopping.getQuantityProducts());
        txtPrice.setText(currentShopping.getPrice());

        return convertView;
    }
}
