package com.example.dinhvu.quanlynhatro.Control;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dinhvu.quanlynhatro.model.KhachTro;
import com.example.dinhvu.quanlynhatro.R;

import java.util.List;

/**
 * Created by DINHVU on 7/29/2017.
 */

public class CustomeSearch extends ArrayAdapter {
    public CustomeSearch(Context context,List objects) {
        super(context, 0, objects);
    }
    TextView ten,sophong;
    LinearLayout view;
    @NonNull
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.list_kt_item,parent,false);
        }

        ten= (TextView) convertView.findViewById(R.id.txtkt);
        sophong= (TextView) convertView.findViewById(R.id.txtsophong);
        view= (LinearLayout) convertView.findViewById(R.id.views);
        KhachTro kt= (KhachTro) getItem(position);
        ten.setText(kt.getHoten());
        sophong.setText("Phòng: "+kt.getSophong());
        if(position%2==0)
            view.setBackgroundColor(Color.GRAY);
        return convertView;
    }
}
