package com.ilikexy.biyesheji;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ilikexy.biyesheji.baseactivity.BaseActivity;
import com.ilikexy.biyesheji.constant.ConstantClass;
import com.ilikexy.biyesheji.constant.ToastAction;
import com.ilikexy.biyesheji.entity.TiAnswer;
import com.ilikexy.biyesheji.entity.TiWrong;
import com.ilikexy.biyesheji.entity.TiWrongSend;
import com.ilikexy.biyesheji.fragment.TiFragment;
import com.ilikexy.biyesheji.zidingyiview.DialogContainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/**
 *  计时器没做，做题记录里缺少这一时间参数0
 *  收藏没做0
 *  做完题，结果活动没有，有超越了多少人0
 *  扩展右上方图标，返回图标，0
 *  收藏本没做0
 *  做题记录本没做
 *  错题本没做0
 * */
public class TestActivity extends BaseActivity implements View.OnClickListener{
    private Button btnLast,btnNext,btnSubmit;
    private TextView tvOrder,tvType,tvTime;
    private CheckBox ivCollect;
    private ImageView ivBack,ivMenu;
    private FragmentManager manager;
    private List<TiFragment> listOfFragment;
    private List<TiAnswer> listofTiAnswer;
    private int nowWhich=0;
    private boolean[] collect = new boolean[]{false,false,false,false,false,false,false,false,false};
    //接下来是计时器的工作
    private Handler handler = new Handler();
    private int count = 0;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            count++;
            int minutes = count / 60;
            int seconds = count % 60;
            if (minutes < 10 && seconds < 10) {
                tvTime.setText("0" + minutes + ":0" + seconds);
            } else if (minutes < 10 && seconds >= 10) {
                tvTime.setText("0" + minutes + ":" + seconds);
            } else if (minutes > 10 && seconds < 10){
                tvTime.setText("" + minutes + ":0" + seconds);
            } else {
                tvTime.setText("" + minutes + ":" + seconds);
            }
            handler.postDelayed(this, 1000);
            if (count==120){
                // 停止计时器
                handler.removeCallbacks(runnable);
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        init();
        // 启动计时器
        handler.postDelayed(runnable, 1000);
    }
    public void init(){
        btnLast =(Button)findViewById(R.id.btn_last_tifragment);
        btnNext = (Button)findViewById(R.id.btn_next_tifragment);
        btnSubmit = (Button)findViewById(R.id.btn_submit_tifragment);
        tvOrder = (TextView)findViewById(R.id.tv_order_answerac);
        tvTime = (TextView)findViewById(R.id.tv_time_answerac);
        tvType = (TextView)findViewById(R.id.tv_type_ti_answerac);
        ivCollect = (CheckBox)findViewById(R.id.cb_collect_tifragment);
        ivBack = (ImageView)findViewById(R.id.image_back_answerac);
        ivMenu = (ImageView)findViewById(R.id.image_menu_answerac);
        listOfFragment = new ArrayList<TiFragment>();
        listofTiAnswer = new ArrayList<TiAnswer>();
        btnLast.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        ivCollect.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        ivMenu.setOnClickListener(this);
        manager = getSupportFragmentManager();
        getTiFromServer();
    }

    //获取题库的题目
    //            TiAnswer tiAnswer = new TiAnswer("ti000"+(i+1),"薪酬水平与风险成本调整后的经营业绩相适应( )。",
//                    "薪酬激励与银行竞争能力及银行持续能力建设相兼顾",
//                    "薪酬机制与银行公司治理要求统一",
//                    "下列属于薪酬机制的原则的是",
//                    "短期激励与长期激励相协调","C");
    public void getTiFromServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME+
                                "GetTi")
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure( Call call, IOException e) {
                        Looper.prepare();
                        ToastAction.startToast(TestActivity.this,"服务器连接失败！请检查网络");
                        Looper.loop();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String data = response.body().string();
                        Gson gson = new Gson();
                        listofTiAnswer = gson.fromJson(data,new TypeToken<List<TiAnswer>>(){}.getType());
                        for (int i=0;i<listofTiAnswer.size();i++){
                            TiFragment tiFragment = new TiFragment(listofTiAnswer.get(i));
                            listOfFragment.add(tiFragment);
                        }
                        FragmentTransaction fragmentTransaction = manager.beginTransaction();
                        //将fragment加入其中
                        for (int j=0;j<listOfFragment.size();j++){
                            fragmentTransaction.add(R.id.fl_ti_answer,listOfFragment.get(j));
                            if (j!=0){
                                fragmentTransaction.hide(listOfFragment.get(j));
                            }
                        }
                        fragmentTransaction.commit();
                    }
                });
            }
        }).start();
    }
    //获得用户自己的usename
    public  String getUsename(){
        SharedPreferences sp = getSharedPreferences(ConstantClass.STRING_SHAREPREFER_USE_PASS,MODE_PRIVATE);
        String getusenamer = sp.getString("usename","");
        return getusenamer;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_last_tifragment:
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.show(listOfFragment.get(nowWhich-1)).hide(listOfFragment.get(nowWhich));
                fragmentTransaction.commit();
                ivCollect.setChecked(collect[nowWhich-1]);
                nowWhich--;
                break;
            case R.id.btn_next_tifragment:
                FragmentTransaction fragmentTransaction1 = manager.beginTransaction();
                fragmentTransaction1.show(listOfFragment.get(nowWhich+1)).hide(listOfFragment.get(nowWhich));
                fragmentTransaction1.commit();
                ivCollect.setChecked(collect[nowWhich+1]);
                nowWhich++;
                break;
            case R.id.btn_submit_tifragment://点击提交时，需要遍历所有的碎片，看是否有还未选择的选项
                checkIsFinishTi();
                break;
            case R.id.cb_collect_tifragment:
                if(!ivCollect.isChecked()){
                    doCollectTi("false");
                    collect[nowWhich] = false;
                }else{
                    doCollectTi("true");
                    collect[nowWhich] = true;
                }
             break;
            case R.id.image_back_answerac://点击提交时，需要遍历所有的碎片，看是否有还未选择的选项
                onBackPressed();
                break;
            case R.id.image_menu_answerac://点击提交时，需要遍历所有的碎片，看是否有还未选择的选项
                ToastAction.startToast(TestActivity.this,"扩展功能，敬请期待！");
                break;
            default:
                break;
        }
        if (nowWhich==0){
            btnLast.setVisibility(View.GONE);
        }else{
            btnLast.setVisibility(View.VISIBLE);
        }
        if (nowWhich==8){
            btnNext.setVisibility(View.GONE);
            btnSubmit.setVisibility(View.VISIBLE);
        }else{
            btnSubmit.setVisibility(View.GONE);
            btnNext.setVisibility(View.VISIBLE);
        }
        tvOrder.setText(""+(nowWhich+1));
    }


    public void checkIsFinishTi(){//核查是否做完题目
        boolean isCanSubmit = true;
        for (int i=0;i<9;i++){
            boolean ischecked = listOfFragment.get(i).isCheckedIt;
            if (!ischecked){
                isCanSubmit = false;
                ToastAction.startToast(this,"第"+(i+1)+"题还没有完成呢！");
                break;
            }
        }
        if (isCanSubmit){//全部都做完了，开始核查答案，将错误的题记录，上传到服务器，同时给出成绩
            // 停止计时器
            handler.removeCallbacks(runnable);
            checkIsRightTi();

        }
    }
    public void checkIsRightTi(){//核查是否做对了题目
        int[] wrong = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1};//错题合集
        int j = 0;
        int theRightcount = 0;
        List<TiWrongSend> listtiwrongsend = new ArrayList<TiWrongSend>();
        for (int i=0;i<9;i++){
            String yourChoose = listOfFragment.get(i).theChosedIt;
            TiAnswer tiAnswerr = listOfFragment.get(i).tiAnswer;
            if (!yourChoose.equals(tiAnswerr.getmAnswer())){//做错题了，上传到服务器
                TiWrongSend tiWrongSend = new TiWrongSend(listOfFragment.get(i).tiAnswer.getTiUId(),
                        yourChoose);
                listtiwrongsend.add(tiWrongSend);
                wrong[j] = i;
                j++;
            }else{
                theRightcount++;
            }
        }//展示做题结果,跳转到新的活动,生成做题记录，用户ID，做题总数，做对题数,做题时间，做题耗时，存储至服务器
        String usetime = tvTime.getText().toString();
        sendDoTiRecord(getUsename(),9,theRightcount,ArticlerActivity.getTimeNow(),usetime);
        //错题本
        Gson gson = new Gson();
        String wrongdata = gson.toJson(listtiwrongsend);
        Log.d("aaaaaaaaaa",wrongdata);
        sendTheWrongRecord(getUsename(),wrongdata);
        //跳转到新的活动
        TiResultActivity.jumpToTiResult(TestActivity.this,9,j,wrong);
    }//做错题了，上传到服务器
    public void sendTheWrongRecord(final String usename, final String wrongdata){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                //第二层，指明服务表单的键名，文件名，文件体
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("usename",usename)
                        .addFormDataPart("wrongdata",wrongdata)
                        .build();
                Request request = new Request.Builder()
                        .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME+"uploadwrongti.action")
                        .post(requestBody)
                        .build();
                //发送请求
                Call call = client.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure( Call call, IOException e) {
                        Looper.prepare();
                        ToastAction.startToast(TestActivity.this,"服务器连接失败！请检查网络");
                        Looper.loop();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String uidd = response.body().string();
                        if (uidd.equals("success")){
                            //跳转到问题活动中
                            Looper.prepare();
                            ToastAction.startToast(TestActivity.this,"成绩上传成功！");
                            Looper.loop();
                        }else{
                            Looper.prepare();
                            ToastAction.startToast(TestActivity.this,"成绩上传失败！");
                            Looper.loop();
                        }
                    }
                });
            }
        }).start();

    }
    //展示做题结果,跳转到新活动,生成做题记录，用户ID，做题总数，做对题数,做题时间，存储至服务器
    public void sendDoTiRecord(final String usename, final int counts, final int right,
                               final String time,final String usetime){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME+
                                "SendDoTiRecord?"+"usename="+usename+"&counts="+counts+"&right="+right+"&time="+time+"&usetime="+usetime)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure( Call call, IOException e) {
                        Looper.prepare();
                        ToastAction.startToast(TestActivity.this,"服务器连接失败！请检查网络");
                        Looper.loop();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String uidd = response.body().string();
                        if (uidd.equals("success")){
                            //跳转到问题活动中
                            Looper.prepare();
                            ToastAction.startToast(TestActivity.this,"做题记录上传成功！");
                            Looper.loop();
                        }else{
                            Looper.prepare();
                            ToastAction.startToast(TestActivity.this,"做题记录上传失败！");
                            Looper.loop();
                        }
                    }
                });
            }
        }).start();
    }


    //收藏题目
    public void doCollectTi(final String isInsert){//先获得当前是第几个题目
         final TiAnswer tiAnswer = listOfFragment.get(nowWhich).tiAnswer;
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME+
                                "AddTiCollect?"+"usename="+getUsename()+"&tiuid="+tiAnswer.getTiUId()
                                +"&time="+ArticlerActivity.getTimeNow()+"&isInsert="+isInsert)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure( Call call, IOException e) {
                        Looper.prepare();
                        ToastAction.startToast(TestActivity.this,"服务器连接失败！请检查网络");
                        Looper.loop();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                       final String uidd = response.body().string();
                       Log.d("aaaaaaaaaaa",uidd);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                switch (uidd){
                                    case "收藏成功":
                                        ivCollect.setChecked(true);
                                        break;
                                    case "收藏失败":
                                        ivCollect.setChecked(false);
                                        break;
                                    case "取消收藏成功":
                                        ivCollect.setChecked(false);
                                        break;
                                    case "取消收藏失败":
                                        ivCollect.setChecked(true);
                                        break;
                                    case "已经收藏过了":
                                        ivCollect.setChecked(true);
                                        break;
                                    case "已经取消收藏过了":
                                        ivCollect.setChecked(false);
                                        break;
                                    default:
                                        break;
                                }
                                ToastAction.startToast(TestActivity.this,uidd);
                            }
                        });
                    }

                });
            }
        }).start();
    }
}