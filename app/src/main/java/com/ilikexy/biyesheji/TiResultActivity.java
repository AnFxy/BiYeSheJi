package com.ilikexy.biyesheji;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ilikexy.biyesheji.adapter.TiResultRightWrongAdapter;
import com.ilikexy.biyesheji.baseactivity.BaseActivity;
import com.ilikexy.biyesheji.constant.ConstantClass;
import com.ilikexy.biyesheji.constant.ToastAction;
import com.ilikexy.biyesheji.entity.ReceiveArticleList;
import com.ilikexy.biyesheji.entity.TiResult;
import com.ilikexy.biyesheji.entity.User;
import com.ilikexy.biyesheji.zidingyiview.RoundPicture;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class TiResultActivity extends BaseActivity {
    private int allCount,wrongCount;//总题数和做错数
    private int[] wrong;//错误序号
    private ImageView ivBack;
    private TextView tvFakename,tvRight,tvAllcount,tvBeyond,tvWrong;
    private RecyclerView rvIt;
    private RoundPicture roupicAuthor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ti_result);
        getDataFromIntent();
        init();
    }
    //获得用户自己的usename
    public  String getUsename(){
        SharedPreferences sp = getSharedPreferences(ConstantClass.STRING_SHAREPREFER_USE_PASS,MODE_PRIVATE);
        String getusenamer = sp.getString("usename","");
        return getusenamer;
    }
    //从碎片中获取数据
    public void getDataFromIntent(){
        Intent intent = getIntent();
        wrong = intent.getIntArrayExtra("wrong");
        allCount = intent.getIntExtra("allcount",0);
        wrongCount = intent.getIntExtra("wrongcount",0);
    }
    //设置从碎片中传递回来数据的intent
    public static void jumpToTiResult(Context context,int allCount,int wrongCount,int[] wrong){
        Intent intent = new Intent(context, TiResultActivity.class);
        intent.putExtra("allcount",allCount);
        intent.putExtra("wrongcount",wrongCount);
        intent.putExtra("wrong",wrong);
        context.startActivity(intent);
    }
    //重写返回方法：

    @Override
    public void onBackPressed() {
        BaseActivity.jump(TiResultActivity.this,MainFunctionActivity.class);
    }

    //初始化控件方法
    public void init(){
        tvFakename = (TextView)findViewById(R.id.tv_fakename_tiresultac);
        tvRight =  (TextView)findViewById(R.id.tv_right_tiresultac);
        tvAllcount = (TextView)findViewById(R.id.tv_allcount_tiresultac);
        tvBeyond = (TextView)findViewById(R.id.tv_beyong_tiresultac);
        tvWrong = (TextView)findViewById(R.id.tv_wrong_tiresultac);
        rvIt = (RecyclerView)findViewById(R.id.rv_tiresultac);
        ivBack = (ImageView)findViewById(R.id.iv_back_itresultac);
        roupicAuthor = (RoundPicture)findViewById(R.id.roupic_author_tiresultac);
        //把做对题数，和总题数，超越率给赋值
        tvRight.setText(""+(allCount-wrongCount));
        tvAllcount.setText("/"+allCount);
        float lv = (float) (allCount-wrongCount)/allCount;
        if (lv>0.5f&&lv<0.88f){
            lv = lv+0.1245f;
        }else if(lv<0.5f&&lv>0.1245f){
            lv = lv-0.1245f;
        }
        DecimalFormat decimalFormat=new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        tvBeyond.setText(""+decimalFormat.format(lv*100)+"%");
        tvWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.jump(TiResultActivity.this,WrongActivity.class);
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //recycleview显示哪道题做对了，哪道题做错了
        RecyclerView.LayoutManager manager = new GridLayoutManager(this,3);
        TiResultRightWrongAdapter adapter = new TiResultRightWrongAdapter(wrong,allCount);
        rvIt.setLayoutManager(manager);
        rvIt.setAdapter(adapter);
        //网络请求获得个人信息头像和名字
        getPicAndName();
    }
    public void getPicAndName(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME
                                +"GetInformation?usename="+getUsename())
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure( Call call, IOException e) {
                        Looper.prepare();
                        ToastAction.startToast(TiResultActivity.this,"网络故障！");
                        Looper.loop();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String data = response.body().string();
                        if (data.equals("failure")){
                            Looper.prepare();
                            ToastAction.startToast(TiResultActivity.this,"服务器故障！");
                            Looper.loop();
                        }else{
                            Gson gson = new Gson();
                            final User user = gson.fromJson(data,new TypeToken<User>(){}.getType());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvFakename.setText(user.getFakename());
                                }
                            });
                            //获得头像
                            getPic(user.getPicuid());
                        }
                    }
                });
            }
        }).start();
    }
    //获得头像
    public void getPic(final String uidd){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                final Request request = new Request.Builder()
                        .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME+
                                "downloadServlet.do?picuid="+uidd)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure( Call call, IOException e) {
                        Looper.prepare();
                        ToastAction.startToast(TiResultActivity.this,"网络故障！");
                        Looper.loop();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                roupicAuthor.setmMBitmap(bitmap);
                            }
                        });
                    }
                });
            }
        }).start();
    }
}