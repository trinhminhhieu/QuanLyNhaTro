package com.example.dinhvu.quanlynhatro.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
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
import com.example.dinhvu.quanlynhatro.Control.CustomeAdapter;
import com.example.dinhvu.quanlynhatro.R;
import com.example.dinhvu.quanlynhatro.model.Dsphong;
import com.example.dinhvu.quanlynhatro.model.NhaTro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    int countback=0;
    String tenxomtro;
    ListView lst;
    ArrayList<Dsphong> arr;
    CustomeAdapter adapter;
    NhaTro nt=new NhaTro();
    int id;
    int millis;
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //them phong
                Intent i=new Intent(MainActivity.this,DetaiActivity.class);
                startActivityForResult(i,0);

            }
        });
        //lay du leu tu loginactivity
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        tenxomtro = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");
        init();
        initnhatro();
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(MainActivity.this,KhachtroActivity.class);
                Dsphong mh=arr.get(i);
                intent.putExtra("DSphong",mh);
                intent.putExtra("nhatro",nt);
                startActivityForResult(intent,0);


            }
        });
        registerForContextMenu(lst);

    }
    //
    private class Writime extends TimerTask{

        @Override
        public void run() {
            if(millis%2==0){
                checkdangnhap();
            }
            millis++;
        }
    }
    //

    private void checkdangnhap() {
        final String URL = Config.URL + "check_dangxuat.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("DINHVU123",response);
                if(!response.equals("3")) {
                    //Getting out sharedpreferences
                    SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    //Getting editor
                    SharedPreferences.Editor editor = preferences.edit();

                    //Puting the value false for loggedin
                    editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                    //Putting blank value to email
                    editor.putString(Config.EMAIL_SHARED_PREF, "");
                    //Saving the sharedpreferences
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);

                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), "Kiểm tra lại kết nối internet", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tenxomtro",tenxomtro);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        requestQueue.add(stringRequest);
    }

    private void initnhatro() {
        final String URL = Config.URL + "get_nhatro.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("DINHVU123",response);
                try {
                    JSONObject root=new JSONObject(response);
                    JSONArray dsphong=root.getJSONArray("nhatro");
                        JSONObject phong=dsphong.getJSONObject(0);
                        nt.setHoten(phong.getString("hoten"));
                        nt.setSodienthoai(phong.getString("sodienthoai"));
                        nt.setDiachi(phong.getString("diachi"));
                        nt.setTenxomtro(phong.getString("tenxomtro"));
                        nt.setDongiadien(phong.getInt("dongiadien"));
                        nt.setDongianuoc(phong.getInt("dongianuoc"));
                } catch (JSONException e) {
                    e.printStackTrace();
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
                params.put("tenxomtro", tenxomtro);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void init() {
        final ProgressDialog progressDialog= ProgressDialog.show(this,"Đang xử lý","vui lòng chờ.....",true,false);
        final String URL = Config.URL + "get_phong.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d("DINHVU123",response);
                try {
                    JSONObject root=new JSONObject(response);
                    JSONArray dsphong=root.getJSONArray("danhsachphong");
                    for(int i=0;i<dsphong.length();i++) {
                        JSONObject phong=dsphong.getJSONObject(i);
                        Dsphong ds=new Dsphong();
                        ds.setSophong(phong.getString("sophong"));
                        ds.setSonguoi(phong.getInt("soluongsv"));
                        ds.setChutro(phong.getString("chutro"));
                        ds.setGiaphong(phong.optDouble("giaphong"));
                        ds.setLoaiphong(phong.getString("loaiphong"));
                        ds.setVitriphong(phong.getString("vitriphong"));
                        ds.setIdphong(phong.getInt("idphong"));
                        arr.add(ds);
                    }
                    adapter.notifyDataSetChanged();
                    Log.d("dinhvu123",arr.get(0).getSophong()+"");
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
                params.put("chutro", tenxomtro);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        lst= (ListView) findViewById(R.id.lst);
        arr=new ArrayList<>();
        adapter=new CustomeAdapter(this,arr);
        lst.setAdapter(adapter);

    }


    @Override
    protected void onResume() {
        super.onResume();
        //
        millis=0;
        timer=new Timer();
        timer.schedule(new Writime(),0,1000);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        init();
        initnhatro();
    }

    @Override
    public void onBackPressed() {
        countback++;
        if (countback == 1) {
            Toast.makeText(getApplicationContext(),
                    "Nhấn back lần nữa để thoát", Toast.LENGTH_SHORT).show();
        } else if (countback == 2) {
            finish();
        }
    }
    //Logout function
    private void logout(){
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Bạn có chắc chắn muốn đăng xuất?")
                .setPositiveButton(R.string.Ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                //Getting out sharedpreferences
                                SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                //Getting editor
                                SharedPreferences.Editor editor = preferences.edit();

                                //Puting the value false for loggedin
                                editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                                //Putting blank value to email
                                editor.putString(Config.EMAIL_SHARED_PREF, "");

                                //Saving the sharedpreferences
                                editor.commit();
                                //Starting login activity
                                danhdaudangxuat();
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

        alertDialogBuilder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog=alertDialogBuilder.create();
        alertDialog.show();

    }

    private void danhdaudangxuat() {
        final String URL = Config.URL + "flag_dangxuat.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("successfully update.")) {

                }else{

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
                params.put("tenxomtro",tenxomtro);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuLogout) {
            logout();
        }
        if(id==R.id.menuupdate){
            updateinfor();
        }
        if (id==R.id.menuseach){
            search();
        }
        if(id==R.id.menuupchangepass){
            Intent i=new Intent(MainActivity.this,DoimatkhauActivity.class);
            i.putExtra("tenxomtro",tenxomtro);
            startActivityForResult(i,0);
        }

        return super.onOptionsItemSelected(item);
    }

    private void search() {
        Intent i=new Intent(MainActivity.this,SearchActivity.class);
        i.putExtra("chutro",tenxomtro);
        startActivity(i);
    }

    private void updateinfor() {
        Intent i=new Intent(MainActivity.this,SignupActivity.class);
        i.putExtra("flag",1);
        i.putExtra("nhatro",nt);
        startActivityForResult(i,0);
    }

    //sua xoa phong
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
            builder.setTitle("bạn có chắc chắn muốn xóa phòng này không???")
                    .setNegativeButton(R.string.Ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //xoa tren database
                            final String URL = Config.URL + "delete_phong.php";

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.contains("successfully delete.")) {
                                        Toast.makeText(getApplicationContext(), "Xóa thành công!!", Toast.LENGTH_SHORT).show();
                                        arr.remove(info.position);
                                        adapter.notifyDataSetChanged();
                                        Log.i("dinhvu123",arr.get(0).getSophong());
                                    }else{
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
                                    params.put("sophong",arr.get(info.position).getSophong());
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
            Intent i=new Intent(MainActivity.this,DetaiActivity.class);
            Dsphong mh=arr.get(info.position);
            id=info.position;
            //gui mon hk can sua
            i.putExtra("DSphong", mh);
            //ddanh dau la sua
            i.putExtra("flag",1);
            startActivityForResult(i,0);

        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==2){
            final Dsphong ds=(Dsphong) data.getSerializableExtra("DSphong");
            //them database
            final String URL = Config.URL + "create_phong.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.contains("successfully created.")) {
                        Toast.makeText(getApplicationContext(), "thêm thành công!!", Toast.LENGTH_SHORT).show();
                        arr.add(ds);
                        adapter.notifyDataSetChanged();
                        Log.i("DINHVU",ds.getSophong());
                    }else{
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
                    params.put("sophong",ds.getSophong().toString());
                    params.put("loaiphong",ds.getLoaiphong().toString());
                    params.put("vitriphong",ds.getVitriphong().toString());
                    params.put("giaphong",ds.getGiaphong()+"");
                    params.put("chutro",tenxomtro);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
            requestQueue.add(stringRequest);
            //

        }
        if(resultCode==1){
            final Dsphong ds= (Dsphong) data.getSerializableExtra("DSphong");
            //update database
            final String URL = Config.URL + "update_phong.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.contains("successfully update.")) {
                        Toast.makeText(getApplicationContext(), "sửa thành công!!", Toast.LENGTH_SHORT).show();
                        arr.set(id,ds);
                        adapter.notifyDataSetChanged();
                    }else{
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
                    params.put("sophong",ds.getSophong().toString());
                    params.put("loaiphong",ds.getLoaiphong().toString());
                    params.put("vitriphong",ds.getVitriphong().toString());
                    params.put("giaphong",ds.getGiaphong()+"");
                    params.put("chutro",tenxomtro);
                    params.put("idphong",ds.getIdphong()+"");
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
            requestQueue.add(stringRequest);
            //

        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }
}
