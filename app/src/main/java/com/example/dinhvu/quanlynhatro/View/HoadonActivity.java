package com.example.dinhvu.quanlynhatro.View;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dinhvu.quanlynhatro.Config;
import com.example.dinhvu.quanlynhatro.Control.CustomeHoaDonAdapter;
import com.example.dinhvu.quanlynhatro.R;
import com.example.dinhvu.quanlynhatro.model.HoaDon;
import com.example.dinhvu.quanlynhatro.model.NhaTro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HoadonActivity extends AppCompatActivity {
    NhaTro nt;
    int idphong;
    ListView lst;
    EditText tungay,denngay;
    ArrayList<HoaDon> arr;
    CustomeHoaDonAdapter adapter;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoadon);
        nt= (NhaTro) getIntent().getSerializableExtra("nhatro");
        idphong= getIntent().getIntExtra("idphong",0);
        lst= (ListView) findViewById(R.id.lst_hoadon);
        tungay= (EditText) findViewById(R.id.editTexttkngaybatdau);
        denngay= (EditText) findViewById(R.id.editTexttkngayketthuc);
        tungay.setInputType(InputType.TYPE_NULL);
        denngay.setInputType(InputType.TYPE_NULL);
        //
        dateFormatter = new SimpleDateFormat("yyy/MM/dd", Locale.US);
        tungay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDatePickerDialog.show();
            }
        });
        denngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toDatePickerDialog.show();
            }
        });
        setDateTimeField();
        //
        init();
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(HoadonActivity.this,InforhoadonActivity.class);
                intent.putExtra("hoadon",arr.get(i));
                intent.putExtra("nhatro",nt);
                intent.putExtra("idphong",idphong);
                startActivityForResult(intent,0);
            }
        });
        registerForContextMenu(lst);

    }
    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tungay.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                denngay.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        init();
    }

    private void init() {
        final ProgressDialog progressDialog= ProgressDialog.show(this,"Đang xử lý","vui lòng chờ.....",true,false);
        arr=new ArrayList<>();
        adapter=new CustomeHoaDonAdapter(this,arr);
        lst.setAdapter(adapter);
        final Calendar date=Calendar.getInstance();
        final int thang=date.get(Calendar.MONTH)+1;
        final int nam=date.get(Calendar.YEAR);
        tungay.setText(nam+"/"+thang+"/1");
        denngay.setText(nam+"/"+(thang+1)+"/1");
        final String URL = Config.URL+ "get_hoadon.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject root=new JSONObject(response);
                    JSONArray dsphong=root.getJSONArray("hoadon");
                    for(int i=0;i<dsphong.length();i++) {
                        JSONObject phong=dsphong.getJSONObject(i);
                        HoaDon ds=new HoaDon();
                        ds.setId(phong.getInt("idhoadon"));
                        ds.setTienPhong(phong.getDouble("giaphong"));
                        ds.setDongaidien(phong.getDouble("dongiadien"));
                        ds.setDongianuoc(phong.getDouble("dongianuoc"));
                        ds.setSodien(phong.getInt("sodien"));
                        ds.setSonuoc(phong.getInt("sonuoc"));
                        ds.setInternet(phong.getDouble("internet"));
                        ds.setNgaylap(phong.getString("ngaylap"));
                        ds.setNgaythanhtoan(phong.optString("ngaythanhtoan"));
                        ds.setTrangthai(phong.getInt("trangthai"));
                        ds.setSophong(phong.getString("sophong"));
                        arr.add(ds);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
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
                params.put("idphong", idphong+"");
                params.put("thang", thang+"");
                params.put("nam", nam+"");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void thongke(View view) {
        arr=new ArrayList<>();
        adapter=new CustomeHoaDonAdapter(this,arr);
        lst.setAdapter(adapter);
        if(TextUtils.isEmpty(tungay.getText()))
            tungay.setError("không được để trống");
        else if(TextUtils.isEmpty(denngay.getText()))
            denngay.setError("không được để trống");
        else if(new Date(tungay.getText().toString()).after(new Date(denngay.getText().toString())))
            denngay.setError("ngày không hợp lệ");
        else {
            final ProgressDialog progressDialog= ProgressDialog.show(this,"Đang xử lý","vui lòng chờ.....",true,false);
            final String URL = Config.URL+"saoke_hoadon.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        JSONObject root = new JSONObject(response);
                        JSONArray dsphong = root.getJSONArray("hoadon");
                        for (int i = 0; i < dsphong.length(); i++) {
                            JSONObject phong = dsphong.getJSONObject(i);
                            HoaDon ds = new HoaDon();
                            ds.setId(phong.getInt("idhoadon"));
                            ds.setTienPhong(phong.getDouble("giaphong"));
                            ds.setDongaidien(phong.getDouble("dongiadien"));
                            ds.setDongianuoc(phong.getDouble("dongianuoc"));
                            ds.setSodien(phong.getInt("sodien"));
                            ds.setSonuoc(phong.getInt("sonuoc"));
                            ds.setInternet(phong.getDouble("internet"));
                            ds.setNgaylap(phong.getString("ngaylap"));
                            ds.setNgaythanhtoan(phong.optString("ngaythanhtoan"));
                            ds.setTrangthai(phong.getInt("trangthai"));
                            ds.setSophong(phong.getString("sophong"));
                            arr.add(ds);
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
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
                    params.put("idphong", idphong + "");
                    params.put("dau", tungay.getText().toString());
                    params.put("cuoi", denngay.getText().toString());
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        if (item.getItemId()==R.id.menu_delete){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("bạn có chắc chắn muốn xóa hóa đơn này không???")
                    .setNegativeButton(R.string.Ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //xoa tren database
                            final String URL = Config.URL+"delete_hoadon.php";
                            final ProgressDialog progressDialog= ProgressDialog.show(HoadonActivity.this,"Đang xử lý","vui lòng chờ.....",true,false);
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressDialog.dismiss();
                                    if(response.contains("successfully delete.")) {
                                        Toast.makeText(getApplicationContext(), "Xóa thành công!!", Toast.LENGTH_SHORT).show();
                                        arr.remove(info.position);
                                        adapter.notifyDataSetChanged();
                                    }else{
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "có lỗi với dữ liệu của bạn!!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "Kiểm tra lại kết nối internet", Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("idhoadon",arr.get(info.position).getId()+"");
                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
                            requestQueue.add(stringRequest);
                            //

                        }
                    })
                    .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
        else{
            Intent i=new Intent(HoadonActivity.this,Edithoadon.class);
            HoaDon hd=arr.get(info.position);
            if(hd.getTrangthai()==1){
                Toast.makeText(getApplicationContext(),"Hóa đơn đã thanh toán không thể sửa!!!",Toast.LENGTH_SHORT).show();
            }else {
                i.putExtra("hoadon", hd);
                startActivityForResult(i, 0);
                id = info.position;
            }

        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            adapter.notifyDataSetChanged();
    }
}
