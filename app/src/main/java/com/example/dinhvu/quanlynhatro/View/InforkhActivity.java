package com.example.dinhvu.quanlynhatro.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.dinhvu.quanlynhatro.R;
import com.example.dinhvu.quanlynhatro.model.KhachTro;

public class InforkhActivity extends AppCompatActivity {
    TextView ten,sdt,diachi,email,tien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inforkh);
        ten= (TextView) findViewById(R.id.txtinfoten);
        sdt= (TextView) findViewById(R.id.txtinfosdt);
        diachi= (TextView) findViewById(R.id.txtinfdiachi);
        email= (TextView) findViewById(R.id.txtinfemail);
        tien= (TextView) findViewById(R.id.txtinftien);
        KhachTro kh= (KhachTro) getIntent().getSerializableExtra("khachtro");
        ten.setText(kh.getHoten());
        sdt.setText(kh.getSodienthoai());
        diachi.setText(kh.getDiachi());
        email.setText(kh.getEmail());
        tien.setText(kh.getSophong());

    }

    public void dongcuaso(View view) {
        finish();
    }
}
