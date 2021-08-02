package com.example.dinhvu.quanlynhatro.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.dinhvu.quanlynhatro.R;
import com.example.dinhvu.quanlynhatro.model.KhachTro;

public class DetaikhachtroActivity extends AppCompatActivity {
    EditText hoten,sodienthoai,email,diachi;
    int flag;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaikhachtro);
        hoten= (EditText) findViewById(R.id.editTexttenkhach);
        sodienthoai= (EditText) findViewById(R.id.editTextsodtkhach);
        email= (EditText) findViewById(R.id.editTextemailkhach);
        diachi= (EditText) findViewById(R.id.editTextdiachikhach);
        flag=getIntent().getIntExtra("flag",0);
        if(flag==1){
            KhachTro kh= (KhachTro) getIntent().getSerializableExtra("khachtro");
            hoten.setText(kh.getHoten());
            sodienthoai.setText(kh.getSodienthoai());
            email.setText(kh.getEmail());
            diachi.setText(kh.getDiachi());
            id=kh.getId();
        }
    }

    public void xacnhankhach(View view) {
        if(TextUtils.isEmpty(hoten.getText()))
            hoten.setError("không được để trống!!!");
        else if(TextUtils.isEmpty(sodienthoai.getText()))
            sodienthoai.setError("không được để trống!!!");
        else if(TextUtils.isEmpty(diachi.getText()))
            diachi.setError("không được để trống!!!");
        else {
            KhachTro kh = new KhachTro(hoten.getText().toString(), sodienthoai.getText().toString(), diachi.getText().toString(), email.getText().toString(),id);
            Intent i = new Intent(this, KhachtroActivity.class);
            i.putExtra("khachtro", kh);
            if (flag == 1) {
                setResult(1, i);
            } else {
                setResult(2, i);
            }
            finish();
        }
    }

    public void huykhach(View view) {
        finish();
    }
}
