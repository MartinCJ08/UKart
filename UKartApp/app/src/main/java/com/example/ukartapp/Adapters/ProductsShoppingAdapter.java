package com.example.ukartapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ukartapp.Models.Products;
import com.example.ukartapp.R;
import com.example.ukartapp.Utils.Constants;

import java.util.List;

public class ProductsShoppingAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Products> productsList;

    private ImageView imgIcon;
    private TextView txtName, txtDesc, txtPrice;
    private Bitmap bImage;

    public ProductsShoppingAdapter(){}

    public ProductsShoppingAdapter(Context context, int layout, List<Products> productsList){
        this.context = context;
        this.layout = layout;
        this.productsList = productsList;
    }

    @Override
    public int getCount() {
        return productsList.size();
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

        if(convertView == null){
            LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
            convertView = layoutInflater.inflate(layout, parent, false);
        }

        imgIcon = convertView.findViewById(R.id.imgIconMyShopping);
        txtName = convertView.findViewById(R.id.txtNameMyShopping);
        txtDesc = convertView.findViewById(R.id.txtDescMyShopping);
        txtPrice = convertView.findViewById(R.id.txtPriceMyShopping);

        txtName.setText(productsList.get(position).getName());
        imgIcon.setImageResource(R.mipmap.ic_snack);
        txtDesc.setText(productsList.get(position).getDescription());
        txtPrice.setText("$"+productsList.get(position).getPrice());

//        Handler handler = new Handler();
//        Runnable runnable = new Runnable(){
//            @Override
//            public void run() {
//                imgIcon.setImageBitmap(bImage);
//            }
//        };
//
//        Thread thread = new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                bImage = Constants.downloadImage(productsList.get(position).getIcon());
//                handler.post(runnable);
//            }
//        };
//
//        thread.start();

        return convertView;
    }
}
