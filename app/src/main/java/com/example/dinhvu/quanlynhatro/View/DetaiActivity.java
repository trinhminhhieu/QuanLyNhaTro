package com.example.dinhvu.quanlynhatro.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.dinhvu.quanlynhatro.R;
import com.example.dinhvu.quanlynhatro.model.Dsphong;

public class DetaiActivity extends AppCompatActivity {
    int flag;
    EditText sophong;
    EditText giaphong;
    String loaiphong;
    EditText vitri;
    String chutro;
    int idphong;
    RadioButton pkhepkin,pkhongkhepkin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detai);
        sophong= (EditText) findViewById(R.id.editTextsophong);
        giaphong= (EditText) findViewById(R.id.editTextgiaphong);
        vitri= (EditText) findViewById(R.id.editTextvitriphong);
        pkhepkin= (RadioButton) findViewById(R.id.rdkhepkin);
        pkhongkhepkin= (RadioButton) findViewById(R.id.rdngoai);
        pkhepkin.setChecked(true);
        flag=getIntent().getIntExtra("flag",0);
        if(flag==1){
            Dsphong ds= (Dsphong) getIntent().getSerializableExtra("DSphong");
            sophong.setText(ds.getSophong());
            if(ds.getLoaiphong().equals("khép kín"))
                pkhepkin.setChecked(true);
            else pkhongkhepkin.setChecked(true);
            giaphong.setText(ds.getGiaphong()+"");
            vitri.setText(ds.getVitriphong());
            chutro=ds.getChutro();
            idphong=ds.getIdphong();
        }
    }

    public void Xacnhan(View view) {
        if(TextUtils.isEmpty(sophong.getText()))
            sophong.setError("không được để trống!!!");
        else if(TextUtils.isEmpty(giaphong.getText()))
            giaphong.setError("không được để trống!!!");
        else if(TextUtils.isEmpty(vitri.getText()))
            vitri.setError("không được để trống!!!");
        else {
            if(pkhepkin.isChecked())
                loaiphong="Khép kín";
            if(pkhongkhepkin.isChecked())
                loaiphong="Không khép kín";
            Dsphong ds = new Dsphong(sophong.getText().toString(), loaiphong, vitri.getText().toString(), chutro, Double.parseDouble(giaphong.getText().toString()),idphong);
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("DSphong", ds);
            if (flag == 1) {
                setResult(1, i);
            } else {
                setResult(2, i);
            }
            finish();
        }
    }

    public void huy(View view) {
        finish();
    }

    //viết nút xác nhận và cancle
}
