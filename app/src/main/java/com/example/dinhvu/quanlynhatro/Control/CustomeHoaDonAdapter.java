package com.example.dinhvu.quanlynhatro.Control;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dinhvu.quanlynhatro.model.HoaDon;
import com.example.dinhvu.quanlynhatro.R;

import java.util.List;

/**
 * Created by DINHVU on 7/28/2017.
 */

public class CustomeHoaDonAdapter extends ArrayAdapter {

    public CustomeHoaDonAdapter(Context context, List objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HoaDon hd= (HoaDon) getItem(position);
        ViewHolder viewHolder;
        if(convertView==null){
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.hoadon_item,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.ngaythang= (TextView) convertView.findViewById(R.id.date);
            viewHolder.tongtien= (TextView) convertView.findViewById(R.id.tien);
            viewHolder.trangthai= (TextView) convertView.findViewById(R.id.trangthai);
            viewHolder.view= (LinearLayout) convertView.findViewById(R.id.view);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }


        viewHolder.ngaythang.setText("Ngày lập:"+hd.getNgaylap());
        viewHolder.tongtien.setText(hd.getTongTien()+" VNĐ");
        if(hd.getTrangthai()==1){
            viewHolder.trangthai.setText("Đã thanh toán");
            viewHolder.trangthai.setTextColor(Color.GREEN);
        }else{
            viewHolder.trangthai.setText("Chưa thanh toán");
            viewHolder.trangthai.setTextColor(Color.RED);
        }
        if(position%2==0)
            viewHolder.view.setBackgroundColor(Color.GRAY);
        return convertView;
    }
    class ViewHolder{
        TextView ngaythang,tongtien,trangthai;
        LinearLayout view;
        public ViewHolder(){

        }
    }
}
