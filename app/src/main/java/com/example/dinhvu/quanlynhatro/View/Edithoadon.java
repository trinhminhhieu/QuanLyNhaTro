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
import com.example.dinhvu.quanlynhatro.model.HoaDon;

import java.util.HashMap;
import java.util.Map;

public class Edithoadon extends AppCompatActivity {
    EditText suasodien,suasonuoc,suainternet;
    HoaDon hd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edithoadon);

        suasodien= (EditText) findViewById(R.id.suasodien);
        suasonuoc= (EditText) findViewById(R.id.suasonuoc);
        suainternet= (EditText) findViewById(R.id.suasointernet);
        hd= (HoaDon) getIntent().getSerializableExtra("hoadon");

        suasodien.setText(hd.getSodien()+"");
        suasonuoc.setText(hd.getSonuoc()+"");
        suainternet.setText(hd.getInternet()+"");


    }

    public void suahoadon(View view) {
        if(TextUtils.isEmpty(suasodien.getText()))
            suasodien.setError("dữ liệu không được trống");
        else if(TextUtils.isEmpty(suasonuoc.getText()))
            suasonuoc.setError("dữ liệu không được trống");
        else if(TextUtils.isEmpty(suainternet.getText()))
            suainternet.setError("dữ liệu không được trống");
        else{
            //HoaDon ehd=new HoaDon(Double.parseDouble(suainternet.getText().toString()),Integer.parseInt(suasodien.getText().toString()),Integer.parseInt(suasonuoc.getText().toString()));
            final String URL = Config.URL+ "sua_hoadon.php";
            final ProgressDialog progressDialog= ProgressDialog.show(this,"Đang xử lý","vui lòng chờ.....",true,false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.contains("successfully update.")) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "sửa thành công!!", Toast.LENGTH_SHORT).show();
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
                    params.put("sodien", suasodien.getText().toString());
                    params.put("sonuoc", suasonuoc.getText().toString());
                    params.put("internet", suainternet.getText().toString());
                    params.put("idhoadon", hd.getId()+"");
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
            requestQueue.add(stringRequest);
            finish();
        }
    }

    public void huysua(View view) {
        finish();
    }
}
