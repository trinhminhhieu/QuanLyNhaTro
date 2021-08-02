package com.example.dinhvu.quanlynhatro.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.dinhvu.quanlynhatro.Control.CustomeSearch;
import com.example.dinhvu.quanlynhatro.R;
import com.example.dinhvu.quanlynhatro.model.KhachTro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    EditText ten;
    ListView lst;
    ArrayList<KhachTro> arr;
    CustomeSearch adapter;
    String chutro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ten= (EditText) findViewById(R.id.editsearch);
        lst= (ListView) findViewById(R.id.lst_search);
        chutro=getIntent().getStringExtra("chutro");
        init();
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(SearchActivity.this,InforkhActivity.class);
                KhachTro kh=arr.get(i);
                intent.putExtra("khachtro",kh);
                startActivityForResult(intent,0);
            }
        });
    }

    private void init() {
        ten.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                timkiem();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void timkiem() {
        arr=new ArrayList<>();
        adapter=new CustomeSearch(this,arr);
        lst.setAdapter(adapter);
        final String URL = Config.URL + "search_khach.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                        arr.add(ds);

                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("DiNHVU",response);

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
                params.put("name", ten.getText().toString());
                params.put("chutro", chutro);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
