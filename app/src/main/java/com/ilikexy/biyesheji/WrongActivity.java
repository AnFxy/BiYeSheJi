package com.ilikexy.biyesheji;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ilikexy.biyesheji.adapter.TiWrongAdapter;
import com.ilikexy.biyesheji.baseactivity.BaseActivity;
import com.ilikexy.biyesheji.constant.ConstantClass;
import com.ilikexy.biyesheji.constant.ToastAction;
import com.ilikexy.biyesheji.entity.TiAnswer;
import com.ilikexy.biyesheji.entity.TiWrong;
import com.ilikexy.biyesheji.fragment.TiFragment;

import java.io.IOException;
import java.util.List;

public class WrongActivity extends BaseActivity {
    private RecyclerView rvWrongac;
    private TiWrongAdapter adapter;
    private ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong);
        init();
        getDataFromServer();
    }
    //获得用户自己的usename
    public  String getUsename(){
        SharedPreferences sp = getSharedPreferences(ConstantClass.STRING_SHAREPREFER_USE_PASS,MODE_PRIVATE);
        String getusenamer = sp.getString("usename","");
        return getusenamer;
    }
    public void init(){
        rvWrongac = (RecyclerView)findViewById(R.id.rv_wrongac);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        ivBack = (ImageView)findViewById(R.id.iv_back_wrongac);
        rvWrongac.setLayoutManager(manager);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    //获得服务器数据
    public void getDataFromServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME+
                                "GetTiWrong?usename="+getUsename())
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure( Call call, IOException e) {
                        Looper.prepare();
                        ToastAction.startToast(WrongActivity.this,"服务器连接失败！请检查网络");
                        Looper.loop();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String data = response.body().string();
                        Gson gson = new Gson();
                        List<TiWrong> lister = gson.fromJson(data,new TypeToken<List<TiWrong>>(){}.getType());
                        adapter = new TiWrongAdapter(lister);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                rvWrongac.setAdapter(adapter);
                            }
                        });

                    }
                });
            }
        }).start();
    }
}