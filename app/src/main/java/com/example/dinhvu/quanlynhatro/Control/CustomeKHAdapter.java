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
 * Created by DINHVU on 7/26/2017.
 */

public class CustomeKHAdapter extends ArrayAdapter {
    public CustomeKHAdapter(Context context, List objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        KhachTro kh= (KhachTro) getItem(position);
        Viewholder viewholder;
        if(convertView==null){
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.list_khach_item,parent,false);
            viewholder=new Viewholder();
            viewholder.txt_hoten=(TextView)convertView.findViewById(R.id.txttenkhach);
            viewholder.view= (LinearLayout) convertView.findViewById(R.id.vieww);
            viewholder.sdt= (TextView) convertView.findViewById(R.id.txtsdt);
            viewholder.dc= (TextView) convertView.findViewById(R.id.txtdiachi);
            viewholder.email= (TextView) convertView.findViewById(R.id.txtemail);
            convertView.setTag(viewholder);
        }else{
            viewholder= (Viewholder) convertView.getTag();
        }


        viewholder.txt_hoten.setText(kh.getHoten().toString());
        viewholder.sdt.setText(kh.getSodienthoai());
        viewholder.dc.setText(kh.getDiachi());
        viewholder.email.setText(kh.getEmail());
        if(position%2==0)
            viewholder.view.setBackgroundColor(Color.GRAY);

        return convertView;
    }
    class Viewholder{
        TextView txt_hoten,sdt,dc,email;
        LinearLayout view;
        public Viewholder(){

        }
    }
}
