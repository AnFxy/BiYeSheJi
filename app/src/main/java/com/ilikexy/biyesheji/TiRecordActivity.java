package com.ilikexy.biyesheji;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ilikexy.biyesheji.adapter.TiRecordAdapter;
import com.ilikexy.biyesheji.adapter.TiWrongAdapter;
import com.ilikexy.biyesheji.baseactivity.BaseActivity;
import com.ilikexy.biyesheji.constant.ConstantClass;
import com.ilikexy.biyesheji.constant.ToastAction;
import com.ilikexy.biyesheji.entity.TiRecord;
import com.ilikexy.biyesheji.entity.TiWrong;

import java.io.IOException;
import java.util.List;

public class TiRecordActivity extends BaseActivity {
    private RecyclerView mrecyclerview;
    private TiRecordAdapter adapter;
    private ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ti_record);
        init();
    }
    public void init(){
       ivBack = (ImageView)findViewById(R.id.iv_back_tirecordac);
       mrecyclerview = (RecyclerView)findViewById(R.id.rv_tirecordac);
       ivBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onBackPressed();
           }
       });
       RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
       mrecyclerview.setLayoutManager(manager);
       getDataFromServer();
    }
    //获得用户自己的usename
    public  String getUsename(){
        SharedPreferences sp = getSharedPreferences(ConstantClass.STRING_SHAREPREFER_USE_PASS,MODE_PRIVATE);
        String getusenamer = sp.getString("usename","");
        return getusenamer;
    }
    public void getDataFromServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME+
                                "GetTiRecord?usename="+getUsename())
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure( Call call, IOException e) {
                        Looper.prepare();
                        ToastAction.startToast(TiRecordActivity.this,"服务器连接失败！请检查网络");
                        Looper.loop();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String data = response.body().string();
                        Gson gson = new Gson();
                        List<TiRecord> lister = gson.fromJson(data,new TypeToken<List<TiRecord>>(){}.getType());
                        adapter = new TiRecordAdapter(lister);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mrecyclerview.setAdapter(adapter);
                            }
                        });

                    }
                });
            }
        }).start();
    }
}