package com.example.dinhvu.quanlynhatro.View;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import com.example.dinhvu.quanlynhatro.Control.CustomeKHAdapter;
import com.example.dinhvu.quanlynhatro.R;
import com.example.dinhvu.quanlynhatro.model.Dsphong;
import com.example.dinhvu.quanlynhatro.model.KhachTro;
import com.example.dinhvu.quanlynhatro.model.NhaTro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class KhachtroActivity extends AppCompatActivity {
    String sophong;
    CustomeKHAdapter adapter;
    ArrayList<KhachTro> arr;
    ListView lst;
    int id,idphong;
    TextView giaphong,dongiadien,dongianuoc,infor;
    EditText sodien,sonuoc,internet;
    Button create;
    NhaTro nt;
    Boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khachtro);
        Dsphong ds= (Dsphong) getIntent().getSerializableExtra("DSphong");
        nt= (NhaTro) getIntent().getSerializableExtra("nhatro");
        sophong=ds.getSophong();
        idphong=ds.getIdphong();
        giaphong= (TextView) findViewById(R.id.txttienphong);
        dongiadien= (TextView) findViewById(R.id.txtdongiadien);
        dongianuoc= (TextView) findViewById(R.id.txtdongianuoc);
        infor= (TextView) findViewById(R.id.txtinfop);
        sodien= (EditText) findViewById(R.id.editTexsodien);
        sonuoc= (EditText) findViewById(R.id.editTextsonuoc);
        internet= (EditText) findViewById(R.id.editTextinternet);
        create= (Button) findViewById(R.id.buttoncreatehd);
        giaphong.setText(ds.getGiaphong()+"");
        dongiadien.setText(nt.getDongiadien()+"");
        dongianuoc.setText(nt.getDongianuoc()+"");
        infor.setText("Th??ng tin chi ti???t ph??ng "+sophong);
        Log.i("DINHVU",idphong+"");

        arr=new ArrayList<>();
        adapter=new CustomeKHAdapter(this,arr);
        lst= (ListView) findViewById(R.id.lst_sv);
        lst.setAdapter(adapter);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(KhachtroActivity.this,InforkhActivity.class);
                KhachTro kh=arr.get(i);
                intent.putExtra("khachtro",kh);
                startActivityForResult(intent,0);
            }
        });
        registerForContextMenu(lst);
        init();
        kiemtra();
    }

    @Override
    protected void onResume() {
        super.onResume();
        kiemtra();
    }

    private void kiemtra() {
        final Calendar date=Calendar.getInstance();
        String strDateFormat = "yyy/MM/dd";
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strDateFormat);
        final String dauthang=date.get(Calendar.YEAR)+"/"+(date.get(Calendar.MONTH)+1)+"/01";
        final String cuoithang=date.get(Calendar.YEAR)+"/"+(date.get(Calendar.MONTH)+2)+"/01";
        final String URL = Config.URL+ "check_hoadon.php";
        Log.i("NGAYTHANG",dauthang+"+"+cuoithang);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("success")) {
                    sodien.setEnabled(false);
                    sonuoc.setEnabled(false);
                    internet.setEnabled(false);
                    create.setText("Xem h??a ????n");
                    flag=true;
                }else{
                    sodien.setEnabled(true);
                    sonuoc.setEnabled(true);
                    internet.setEnabled(true);
                    create.setText("T???o h??a ????n");
                    flag=false;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Ki???m tra l???i k???t n???i internet", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idphong",idphong+"");
                params.put("dauthang",dauthang);
                params.put("cuoithang",cuoithang);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        requestQueue.add(stringRequest);
        //
    }

    private void init() {
        final ProgressDialog progressDialog= ProgressDialog.show(this,"??ang x??? l??","vui l??ng ch???.....",true,false);
        final String URL = Config.URL+"get_khach.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject root=new JSONObject(response);
                    JSONArray dskhach=root.getJSONArray("sinhvien");
                    for(int i=0;i<dskhach.length();i++) {
                        JSONObject khach=dskhach.getJSONObject(i);
                        KhachTro ds=new KhachTro();
                        ds.setSophong(khach.getString("sophong"));
                        ds.setId(khach.getInt("idsinhvien"));
                        ds.setHoten(khach.getString("hoten"));
                        ds.setSodienthoai(khach.getString("sodienthoai"));
                        ds.setDiachi(khach.getString("diachi"));
                        ds.setEmail(khach.getString("email"));
                        ds.setNgaythue(khach.getString("ngaythue"));
                        ds.setIdphong(khach.getInt("idphong"));
                        arr.add(ds);
                    }
                    adapter.notifyDataSetChanged();
                    Log.d("dinhvu",arr.get(0).getSophong()+"");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Ki???m tra l???i k???t n???i internet", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idphong", idphong+"");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void addkh(View view) {
        Intent i=new Intent(KhachtroActivity.this,DetaikhachtroActivity.class);
        startActivityForResult(i,0);
    }

    public void huybo(View view) {
        finish();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menukhach,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        if (item.getItemId()==R.id.menu_deletekh){
            //xoa khach
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("b???n c?? ch???c ch???n mu???n x??a kh??ch h??ng n??y kh??ng???")
                    .setNegativeButton(R.string.Ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //xoa tren database
                            final String URL = Config.URL + "delete_khach.php";

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.contains("successfully delete.")) {
                                        Toast.makeText(getApplicationContext(), "X??a th??nh c??ng!!", Toast.LENGTH_SHORT).show();
                                        arr.remove(info.position);
                                        adapter.notifyDataSetChanged();
                                        //Log.i("dinhvu123",arr.get(0).getSophong());
                                    }else{
                                        Toast.makeText(getApplicationContext(), "c?? l???i v???i d??? li???u c???a b???n!!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "Ki???m tra l???i k???t n???i internet", Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("idsinhvien",arr.get(info.position).getId()+"");
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
        }else if(item.getItemId()==R.id.menu_editkh){
            Intent i=new Intent(this,DetaikhachtroActivity.class);
            KhachTro kh=arr.get(info.position);
            id=info.position;
            //gui mon hk can sua
            i.putExtra("khachtro", kh);
            //ddanh dau la sua
            i.putExtra("flag",1);
            startActivityForResult(i,0);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==2){
            final Date date=new Date();
            String strDateFormat = "yyy/MM/dd";
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strDateFormat);
            Log.d("DATETIME",simpleDateFormat.format(date));
            final KhachTro ds=(KhachTro) data.getSerializableExtra("khachtro");
            //them database
            final String URL = Config.URL + "create_khach.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.contains("successfully created.")) {
                        Toast.makeText(getApplicationContext(), "th??m th??nh c??ng!!", Toast.LENGTH_SHORT).show();
                        arr.add(ds);
                        adapter.notifyDataSetChanged();
                        Log.i("DINHVU",ds.getSodienthoai());
                    }else{
                        Toast.makeText(getApplicationContext(), "c??? l???i x???y ra", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "ki???m tra l???i k???t n???i internet", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("idphong",idphong+"");
                    params.put("hoten",ds.getHoten().toString());
                    params.put("sodienthoai",ds.getSodienthoai().toString());
                    params.put("diachi",ds.getDiachi());
                    params.put("email",ds.getEmail().toString());
                    params.put("ngaythue",simpleDateFormat.format(date));
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
            requestQueue.add(stringRequest);
            //

        }
        if(resultCode==1){
            final KhachTro ds= (KhachTro) data.getSerializableExtra("khachtro");
            //update database
            final String URL = Config.URL + "update_khach.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.contains("successfully update.")) {
                        Toast.makeText(getApplicationContext(), "s???a th??nh c??ng!!", Toast.LENGTH_SHORT).show();
                        arr.set(id,ds);
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(getApplicationContext(), "c?? l???i x???y ra!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Ki???m tra l???i k???t n???i internet", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("hoten",ds.getHoten().toString());
                    params.put("sodienthoai",ds.getSodienthoai().toString());
                    params.put("diachi",ds.getDiachi());
                    params.put("email",ds.getEmail());
                    params.put("idsinhvien",ds.getId()+"");
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
            requestQueue.add(stringRequest);
            //

        }
    }

    public void taohoadon(View view) {
        if(flag)
            xemhoadon();
        else themhoadon();
        //
    }

    void themhoadon(){
        if(TextUtils.isEmpty(sodien.getText()))
            sodien.setError("kh??ng ???????c ????? tr???ng");
        else if(TextUtils.isEmpty(sonuoc.getText()))
            sonuoc.setError("kh??ng ???????c ????? tr???ng");
        else if(TextUtils.isEmpty(internet.getText()))
            internet.setError("kh??ng ???????c ????? tr???ng");
        else {
            final String URL = Config.URL + "create_hoadon.php";
            final ProgressDialog progressDialog = ProgressDialog.show(this, "??ang x??? l??", "vui l??ng ch???.....", true, false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    if (response.contains("successfully created.")) {
                        thongbaothanhcong("t???o h??a ????n th??nh c??ng, v??o ch???c n??ng xem h??a ????n");
                    } else {
                        Toast.makeText(getApplicationContext(), "c?? l???i v???i d??? li???u c???a b???n!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Ki???m tra l???i k???t n???i internet", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("idphong", idphong + "");
                    params.put("sodien", sodien.getText().toString());
                    params.put("sonuoc", sonuoc.getText().toString());
                    params.put("internet", internet.getText().toString());
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
            requestQueue.add(stringRequest);
        }
    }

    private void thongbaothanhcong(String notif) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage(notif)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        xemhoadon();
                    }
                })
        .setNegativeButton("H???y", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                kiemtra();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    public void xemhoadon() {
        Intent intent=new Intent(KhachtroActivity.this,HoadonActivity.class);
        intent.putExtra("idphong",idphong);
        intent.putExtra("nhatro",nt);
        startActivityForResult(intent,0);
    }
}
