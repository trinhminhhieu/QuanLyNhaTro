package com.example.dinhvu.quanlynhatro.View;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
import com.example.dinhvu.quanlynhatro.model.NhaTro;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    EditText hoten,sodienthoai,diachi,tenxomtro,matkhau,matkhauagain,dongiadien, dongianuoc;
    NhaTro nt;
    Button signup;
    int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        hoten= (EditText) findViewById(R.id.editTexthoten);
        sodienthoai= (EditText) findViewById(R.id.editTextSoDienThoai);
        diachi= (EditText) findViewById(R.id.editTextDiaChi);
        tenxomtro= (EditText) findViewById(R.id.editTexttennhatro);
        matkhau= (EditText) findViewById(R.id.editTextPassword);
        matkhauagain= (EditText) findViewById(R.id.editTextPasswordAgain);
        dongiadien= (EditText) findViewById(R.id.editTextDIEN);
        dongianuoc= (EditText) findViewById(R.id.editTextNUOC);
        signup= (Button) findViewById(R.id.buttonsignup);
        nt= (NhaTro) getIntent().getSerializableExtra("nhatro");
        flag=getIntent().getIntExtra("flag",0);
        if(flag==1){
            hoten.setText(nt.getHoten());
            sodienthoai.setText(nt.getSodienthoai());
            diachi.setText(nt.getDiachi());
            tenxomtro.setText(nt.getTenxomtro());
            tenxomtro.setEnabled(false);
            matkhau.setEnabled(false);
            matkhauagain.setEnabled(false);
            dongiadien.setText(nt.getDongiadien()+"");
            dongianuoc.setText(nt.getDongianuoc()+"");
            signup.setText("Cập nhật");
        }

    }


    public void buttonsignup(View view) {
        if(flag==1){
            updateinfor();
        }else {
            final String name = hoten.getText().toString();
            final String phone = sodienthoai.getText().toString();
            final String add = diachi.getText().toString();
            final String user = tenxomtro.getText().toString();
            final String pass = matkhau.getText().toString();
            final String passagain = matkhauagain.getText().toString();
            final String nuoc = dongianuoc.getText().toString();
            final String dien = dongiadien.getText().toString();
            if (TextUtils.isEmpty(name))
                hoten.setError("không được để trống!!!");
            else if (TextUtils.isEmpty(phone))
                sodienthoai.setError("không được để trống!!!");
            else if (TextUtils.isEmpty(add))
                diachi.setError("không được để trống!!!");
            else if (TextUtils.isEmpty(user))
                tenxomtro.setError("không được để trống!!!");
            else if (TextUtils.isEmpty(pass))
                matkhau.setError("không được để trống!!!");
            else if (TextUtils.isEmpty(passagain))
                matkhauagain.setError("không được để trống!!!");
            else if (TextUtils.isEmpty(nuoc))
                dongianuoc.setError("không được để trống!!!");
            else if (TextUtils.isEmpty(dien))
                dongiadien.setError("không được để trống!!!");
            else if (!pass.equals(passagain)) {
                matkhau.setError("");
                matkhauagain.setError("mật khâu không khớp");
            } else {
                final ProgressDialog progressDialog= ProgressDialog.show(this,"Đang xử lý","vui lòng chờ.....",true,false);
                final String URL = Config.URL + "signup.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("users successfully created.")) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Đăng ký thành công, mời đăng nhập lại", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "có lỗi với dữ liệu của bạn!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Thất bại, kiểm tra lại kết nối internet", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("hoten", name);
                        params.put("sodienthoai", phone);
                        params.put("diachi", add);
                        params.put("tenxomtro", user);
                        params.put("matkhau", pass);
                        params.put("dongiadien", dien);
                        params.put("dongianuoc", nuoc);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);

            }
        }
    }

    private void updateinfor() {
        final String name = hoten.getText().toString();
        final String phone = sodienthoai.getText().toString();
        final String add = diachi.getText().toString();
        final String nuoc = dongianuoc.getText().toString();
        final String dien = dongiadien.getText().toString();
        final String user = tenxomtro.getText().toString();
        if (TextUtils.isEmpty(name))
            hoten.setError("không được để trống!!!");
        else if (TextUtils.isEmpty(phone))
            sodienthoai.setError("không được để trống!!!");
        else if (TextUtils.isEmpty(add))
            diachi.setError("không được để trống!!!");
        else if (TextUtils.isEmpty(nuoc))
            dongianuoc.setError("không được để trống!!!");
        else if (TextUtils.isEmpty(dien))
            dongiadien.setError("không được để trống!!!");
        else{
            final ProgressDialog progressDialog= ProgressDialog.show(this,"Đang xử lý","vui lòng chờ.....",true,false);
            final String URL = Config.URL + "update_nhatro.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.contains("successfully update.")) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "cập nhật thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "có lỗi với dữ liệu của bạn!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Thất bại, kiểm tra lại kết nối internet", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("hoten", name);
                    params.put("sodienthoai", phone);
                    params.put("diachi", add);
                    params.put("dongiadien", dien);
                    params.put("dongianuoc", nuoc);
                    params.put("tenxomtro", user);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }
}
