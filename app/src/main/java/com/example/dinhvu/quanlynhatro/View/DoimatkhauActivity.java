package com.example.dinhvu.quanlynhatro.View;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class DoimatkhauActivity extends AppCompatActivity {
    EditText mkcu,mkmoi,xacnhanmkmoi;
    String tenxomtro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doimatkhau);
        mkcu= (EditText) findViewById(R.id.dmkMKcu);
        mkmoi= (EditText) findViewById(R.id.dmkMKmoi);
        xacnhanmkmoi= (EditText) findViewById(R.id.dmkXNMK);
        tenxomtro=getIntent().getStringExtra("tenxomtro");

    }

    public void Xacnhan(View view) {
        final String old=mkcu.getText().toString();
        final String newpass=mkmoi.getText().toString();
        final String XN=xacnhanmkmoi.getText().toString();
        if(TextUtils.isEmpty(old))
            mkcu.setError("không được để trỗng!!");
        else if(TextUtils.isEmpty(newpass))
            mkmoi.setError("không được để trỗng!!");
        else if(TextUtils.isEmpty(XN))
            xacnhanmkmoi.setError("không được để trỗng!!");
        else if(!newpass.equals(XN)){
            mkmoi.setError("mật khẩu không khớp");
            xacnhanmkmoi.setError("mật khẩu không khớp");
            mkmoi.setText("");
            mkcu.setText("");
            xacnhanmkmoi.setText("");
        }else {
            final ProgressDialog progressDialog= ProgressDialog.show(this,"Đang xử lý","vui lòng chờ.....",true,false);

            //update database
            final String URL = Config.URL+ "update_matkhau.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.contains("success")) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "đổi mật khẩu thành công thành công!!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        progressDialog.dismiss();
                        mkmoi.setText("");
                        mkcu.setText("");
                        xacnhanmkmoi.setText("");
                        Toast.makeText(getApplicationContext(), "sai mật khẩu!!!", Toast.LENGTH_SHORT).show();
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
                    params.put("matkhaucu",old);
                    params.put("matkhaumoi", newpass);
                    params.put("tenxomtro", tenxomtro);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
            requestQueue.add(stringRequest);
        }

    }

    public void huy(View view) {
        finish();
    }
}
