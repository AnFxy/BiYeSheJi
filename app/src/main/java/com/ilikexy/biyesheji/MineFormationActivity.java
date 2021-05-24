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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ilikexy.biyesheji.adapter.FormaRecyclerAdapter;
import com.ilikexy.biyesheji.baseactivity.BaseActivity;
import com.ilikexy.biyesheji.constant.ConstantClass;
import com.ilikexy.biyesheji.constant.ToastAction;
import com.ilikexy.biyesheji.entity.MyFormation;
import com.ilikexy.biyesheji.entity.User;
import com.ilikexy.biyesheji.fragment.SetFragment;
import com.ilikexy.biyesheji.zidingyiview.RoundedSquare;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MineFormationActivity extends BaseActivity implements View.OnClickListener{

    private TextView mTextBack;
    private RoundedSquare mRoundedSquare;
    private Bitmap mbitmap;//头像图片
    private LinearLayout mLinear;
    private RecyclerView mRecycler;
    //全局属性变量如下
    private String mfakenamer,msex,mage,mSchool,mClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_formation);
        init();
    }
    public void init(){
        //全局变量初始化
        mfakenamer="昵称";
        msex = "男";
        mage = "0";
        mSchool = "未填写";
        mClass = "未填写";
        mbitmap = BitmapFactory.decodeResource(getResources(),R.drawable.graytouxiang);//默认头像图片
        mTextBack = (TextView)findViewById(R.id.text_back_forma);
        mRoundedSquare = (RoundedSquare)findViewById(R.id.imag_formation_activity);
        mLinear = (LinearLayout)findViewById(R.id.linear_formation_activity);
        mRecycler = (RecyclerView)findViewById(R.id.recycler_formation_activity);
        FormaRecyclerAdapter adapter = new FormaRecyclerAdapter(getFormation());
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(adapter);
        mTextBack.setOnClickListener(this);
        mLinear.setOnClickListener(this);
        setDataView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_back_forma:
                onBackPressed();
            default:break;
        }
    }
    public List<MyFormation> getFormation(){
        List<MyFormation> list = new ArrayList<>();
        list.add(new MyFormation("昵称",mfakenamer,false,true));
        list.add(new MyFormation("性别",msex,true,false));
        list.add(new MyFormation("年龄",mage,true,false));
        list.add(new MyFormation("学校",mSchool,true,false));
        list.add(new MyFormation("班级",mClass,false,true));
        return list;
    }

    @Override
    protected void onResume() {
        SharedPreferences sp = getSharedPreferences(ConstantClass.STRING_SHAREPREFER_USE_PASS,MODE_PRIVATE);
        mfakenamer = sp.getString("fakename","未知");
        msex = sp.getString("sex","男");
        mage = sp.getString("age","0");
        mSchool = sp.getString("school","未知");
        mClass = sp.getString("class","未知");
        List<MyFormation> list = new ArrayList<>();
        list.add(new MyFormation("昵称",mfakenamer,false,true));
        list.add(new MyFormation("性别",msex,true,false));
        list.add(new MyFormation("年龄",mage,true,false));
        list.add(new MyFormation("学校",mSchool,true,false));
        list.add(new MyFormation("班级",mClass,false,true));
        FormaRecyclerAdapter adapter = new FormaRecyclerAdapter(list);
        mRecycler.setAdapter(adapter);
        super.onResume();
    }
    public void setDataView(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME+
                                        "GetInformation?usename="+ SetFragment.mUsename)
                                .build();
                        Call call = client.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure( Call call, IOException e) {
                                Looper.prepare();
                                ToastAction.startToast(MineFormationActivity.this,"数据请求失败！");
                                Looper.loop();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                Gson gson = new Gson();
                                //Log.d("hehe",response.body().string());
                                final User user = gson.fromJson(response.body().string(),new TypeToken<User>(){}.getType());
                                //更新头像
                                updatePicture(user.getPicuid());
                            }
                        });
                    }
                }).start();
            }
        }).start();
    }

    public void updatePicture(final String uid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url(ConstantClass.STRING_SERVICE_URL + ConstantClass.STRING_SERVICE_PROJECTNAME +
                                        "downloadServlet.do?picuid=" + uid)
                                .build();
                        Call call = client.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Looper.prepare();
                                ToastAction.startToast(MineFormationActivity.this, "数据请求失败！");
                                Looper.loop();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mRoundedSquare.setMbitmap(bitmap);
                                    }
                                });
                            }
                        });
                    }
                }).start();
            }
        }).start();
    }
}