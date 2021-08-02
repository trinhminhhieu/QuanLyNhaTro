package com.example.dinhvu.quanlynhatro.View;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dinhvu.quanlynhatro.Config;
import com.example.dinhvu.quanlynhatro.R;
import com.example.dinhvu.quanlynhatro.model.HoaDon;
import com.example.dinhvu.quanlynhatro.model.NhaTro;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class InforhoadonActivity extends AppCompatActivity {
    TextView donGiadien,donGiaNuoc,donGiaInternet,SLdien,SLnuoc,SLinternet,TTdien,TTnuoc,TTinternet,TongTien,ngaylap,ngaythanhtoan,hotenchu,sodienthoai,diachi,sophong,tienphong,infor;
    EditText edttien,edtkhach,edttralai;
    Button buttonTT,buttonClose;
    Dialog dialog;
    HoaDon hd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inforhoadon);
        hd= (HoaDon) getIntent().getSerializableExtra("hoadon");
        NhaTro nt= (NhaTro) getIntent().getSerializableExtra("nhatro");
        String sop=getIntent().getStringExtra("sophong");
        donGiadien= (TextView) findViewById(R.id.dongaidien);
        donGiaNuoc= (TextView) findViewById(R.id.dongianuoc);
        donGiaInternet= (TextView) findViewById(R.id.dongiainternet);
        SLdien= (TextView) findViewById(R.id.soluongdien);
        SLnuoc= (TextView) findViewById(R.id.soluongnuoc);
        SLinternet= (TextView) findViewById(R.id.soluonginternet);
        TTdien= (TextView) findViewById(R.id.thanhtiendien);
        TTnuoc= (TextView) findViewById(R.id.thanhtiennuoc);
        TTinternet= (TextView) findViewById(R.id.thanhtieninternet);
        TongTien= (TextView) findViewById(R.id.tongcong);
        ngaylap= (TextView) findViewById(R.id.ngaylap);
        ngaythanhtoan= (TextView) findViewById(R.id.ngaythanhtoan);
        hotenchu= (TextView) findViewById(R.id.chutro);
        sodienthoai= (TextView) findViewById(R.id.sodienthoai);
        diachi= (TextView) findViewById(R.id.diachi);
        sophong= (TextView) findViewById(R.id.sophong);
        tienphong= (TextView) findViewById(R.id.tienphong);
        infor= (TextView) findViewById(R.id.info);

        hotenchu.setText("Họ và tên chủ trọ: "+nt.getHoten());
        sodienthoai.setText("Số điện thoại: "+nt.getSodienthoai());
        diachi.setText("Địa chỉ: "+nt.getDiachi());
        donGiadien.setText(hd.getDongaidien()+"");
        donGiaNuoc.setText(hd.getDongianuoc()+"");
        donGiaInternet.setText(hd.getInternet()+"");
        SLdien.setText(hd.getSodien()+"");
        SLnuoc.setText(hd.getSonuoc()+"");
        SLinternet.setText("1");
        TTdien.setText((hd.getSodien()*hd.getDongaidien())+"");
        TTnuoc.setText((hd.getSonuoc()*hd.getDongianuoc())+"");
        TTinternet.setText(hd.getInternet()+"");
        TongTien.setText(hd.getTongTien()+"");
        ngaylap.setText("Ngày lập: "+hd.getNgaylap());
        ngaythanhtoan.setText("Ngày thanh toán: "+hd.getNgaythanhtoan());
        sophong.setText("Phòng Số: "+hd.getSophong());
        tienphong.setText(hd.getTienPhong()+"");
        Calendar date=Calendar.getInstance();
        infor.setText("phòng "+hd.getSophong()+" tháng "+hd.getNgaylap().substring(0,7));


    }

    public void thanhtoanhoadon(View view) {
        if(hd.getTrangthai()==1)
            thongbaothanhcong("Hóa đơn đã được thanh toán trước đó");
        else {
            dialog = new Dialog(InforhoadonActivity.this);
            dialog.setContentView(R.layout.dialog_thanhtoan);
            dialog.setTitle("Đang giao dịch!!!");
            edttien = (EditText) dialog.findViewById(R.id.edttongtien);
            edttralai = (EditText) dialog.findViewById(R.id.edttralai);
            edtkhach = (EditText) dialog.findViewById(R.id.edtkhachdua);
            buttonTT = (Button) dialog.findViewById(R.id.buttontt);
            buttonClose = (Button) dialog.findViewById(R.id.buttonclose);
            edttien.setEnabled(false);
            edttralai.setEnabled(false);
            edttien.setText(TongTien.getText());
            edtkhach.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    edttralai.setText((Double.parseDouble(edtkhach.getText().toString())) - Double.parseDouble(TongTien.getText().toString()) + "");
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            buttonClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                }
            });
            buttonTT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (hd.getTrangthai() == 1) {
                        thongbaothanhcong("Hóa đơn đã được thanh toán trước đó");
                    } else {
                        final ProgressDialog progressDialog= ProgressDialog.show(InforhoadonActivity.this,"Đang xử lý","vui lòng chờ.....",true,false);
                        final String URL = Config.URL+ "update_hoadon.php";

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.contains("successfully update.")) {
                                    progressDialog.dismiss();
                                    thongbaothanhcong("Thanh toán thành công");
                                    dialog.cancel();
                                    finish();
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "có lỗi với dữ liệu của bạn!!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Kiểm tra lại kết nối internet", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("id", hd.getId() + "");
                                return params;
                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
                        requestQueue.add(stringRequest);
                    }
                }
            });
            dialog.show();
        }

    }
    private void thongbaothanhcong(String notif) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(notif)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void donghoadon(View view) {
        finish();
    }
}
