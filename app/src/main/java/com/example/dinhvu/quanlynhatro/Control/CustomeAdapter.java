package com.example.dinhvu.quanlynhatro.Control;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dinhvu.quanlynhatro.model.Dsphong;
import com.example.dinhvu.quanlynhatro.R;

import java.util.List;


/**
 * Created by DINHVU on 7/26/2017.
 */

public class CustomeAdapter  extends ArrayAdapter {

    public CustomeAdapter(Context context, List objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        Dsphong ds= (Dsphong) getItem(position);
        ViewHolder viewHolder;
        if(convertView==null){
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.list_phong_item,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.txt_sophong=(TextView)convertView.findViewById(R.id.txt_phong);
            viewHolder.soluong= (TextView) convertView.findViewById(R.id.slsv);
            viewHolder.loaiphong= (TextView) convertView.findViewById(R.id.loaiphong);
            viewHolder.giaphong= (TextView) convertView.findViewById(R.id.giaphong);
            viewHolder.view=convertView.findViewById(R.id.view);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }


        viewHolder.txt_sophong.setText("Phòng số: "+ds.getSophong());
        viewHolder.soluong.setText("Sô lượng khách ở: "+ds.getSonguoi());
        viewHolder.loaiphong.setText("Loại: "+ds.getLoaiphong());
        viewHolder.giaphong.setText("Giá: "+ds.getGiaphong()+"");
        if(ds.getSonguoi()>0)
            viewHolder.view.setBackgroundColor(Color.RED);
        else
            viewHolder.view.setBackgroundColor(Color.GREEN);
        return convertView;
    }
    class ViewHolder{
        TextView txt_sophong,soluong,loaiphong,giaphong;
        View view;
        public ViewHolder(){

        }
    }
}
