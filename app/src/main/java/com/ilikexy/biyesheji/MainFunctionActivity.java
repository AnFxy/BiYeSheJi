package com.ilikexy.biyesheji;

import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ilikexy.biyesheji.baseactivity.BaseActivity;
import com.ilikexy.biyesheji.constant.ConstantClass;
import com.ilikexy.biyesheji.constant.ToastAction;
import com.ilikexy.biyesheji.constant.ValueAnimator;
import com.ilikexy.biyesheji.entity.ArticleList;
import com.ilikexy.biyesheji.entity.DaTiJieGuo;
import com.ilikexy.biyesheji.entity.QuestionItem;
import com.ilikexy.biyesheji.entity.ReceiveArticleList;
import com.ilikexy.biyesheji.entity.TiRank;
import com.ilikexy.biyesheji.fragment.FoundFragment;
import com.ilikexy.biyesheji.fragment.MessageFragment;
import com.ilikexy.biyesheji.fragment.QuestionFragment;
import com.ilikexy.biyesheji.fragment.SetFragment;
import com.ilikexy.biyesheji.fragment.VideosFragment;
import com.ilikexy.biyesheji.model.VideoTestData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainFunctionActivity extends BaseActivity implements View.OnClickListener{
    //底部按钮控件声明
    private LinearLayout mLinearLayout;
    private TextView mMessageTextView,mVideosTextView,mFoundTextView,mQuestionTextView,mSetTextView;
    private TextView mTextTitle;
    //FragmentManager, FragmentTransation
    private FragmentManager mManager;
    private FragmentTransaction mTransaction;
    //资讯fragment数据
    List<String> listofstring;
    List<ReceiveArticleList> receivearticlelist;
    //问题question数据
    List<QuestionItem> listofquestion;
    //排行榜数据
    List<TiRank> listofranklist;
    //提高fragment切换的性能
    MessageFragment messFrag;
    VideosFragment videosFrag;
    FoundFragment foundFrag;
    QuestionFragment questionFrag;
    SetFragment setFrag;

    //handle更新ui
    private Handler handler = new Handler(){
       public void handleMessage(Message msg){
           switch (msg.what){
               case 1://资讯列表
                   messFrag = new MessageFragment(MainFunctionActivity.this,listofstring,getListofarticlelist());
                   mTransaction.add(R.id.myfragment_mainfa,messFrag);//碎片初始全部添加
                   mTransaction.hide(videosFrag).hide(setFrag).hide(questionFrag).commit();
                   break;
               case 3:
                   foundFrag = new FoundFragment(listofranklist);
                   mTransaction.add(R.id.myfragment_mainfa,foundFrag);
                   mTransaction.hide(foundFrag);
                   break;
               default:
                   break;
           }
       }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_function);
        init();
        initFragment();
        getListArticle();
        getRankList();
    }
    public void init(){
        getListTitle();
        mLinearLayout =  (LinearLayout)findViewById(R.id.buttomChange_linear);
        mMessageTextView = (TextView)findViewById(R.id.text_message_mainactivity);
        mVideosTextView = (TextView)findViewById(R.id.text_video_mainactivity);
        mFoundTextView = (TextView)findViewById(R.id.text_found_mainactivity);
        mQuestionTextView = (TextView)findViewById(R.id.text_question_mainactivity);
        mSetTextView = (TextView)findViewById(R.id.text_set_mainactivity);
        mTextTitle = (TextView)findViewById(R.id.text_title_mainfa);
        mMessageTextView.setOnClickListener(this);
        mVideosTextView.setOnClickListener(this);
        mFoundTextView.setOnClickListener(this);
        mQuestionTextView.setOnClickListener(this);
        mSetTextView.setOnClickListener(this);
        mManager = getSupportFragmentManager();
    }
    //初始状态下，把4个Fragment都添加进布局中，然后设置其他的隐身
    public void initFragment(){
        //messFrag = new MessageFragment(MainFunctionActivity.this,listofstring,getListArticler());
        videosFrag = new VideosFragment(MainFunctionActivity.this);
        questionFrag = new QuestionFragment();
        setFrag = new SetFragment(getUsename());
       // foundFrag = new FoundFragment();

        mTransaction = mManager.beginTransaction();
        //mTransaction.add(R.id.myfragment_mainfa,foundFrag);
        //mTransaction.add(R.id.myfragment_mainfa,messFrag);//碎片初始全部添加
        mTransaction.add(R.id.myfragment_mainfa,videosFrag);
        mTransaction.add(R.id.myfragment_mainfa,questionFrag);
        //mTransaction.add(R.id.myfragment_mainfa,questionFrag);
        mTransaction.add(R.id.myfragment_mainfa,setFrag);
        //隐藏不需要显示的碎片
       // mTransaction.hide(videosFrag).hide(foundFrag).hide(questionFrag).hide(setFrag).commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.text_message_mainactivity:
                clickEventDo(1);//图标变换
                mTextTitle.setText("资讯");
                mLinearLayout.setBackground(getDrawable(R.drawable.r1));
                break;
            case R.id.text_video_mainactivity:
                clickEventDo(2);
                mTextTitle.setText("视频");
                mLinearLayout.setBackground(getDrawable(R.drawable.r2));
                break;
            case R.id.text_found_mainactivity:
                clickEventDo(3);
                mTextTitle.setText("发现");
                mLinearLayout.setBackground(getDrawable(R.drawable.r3));
                break;
            case R.id.text_question_mainactivity:
                clickEventDo(4);
                mTextTitle.setText("问答");
                mLinearLayout.setBackground(getDrawable(R.drawable.r4));
                break;
            case R.id.text_set_mainactivity:
                clickEventDo(5);
                mTextTitle.setText("我的");
                mLinearLayout.setBackground(getDrawable(R.drawable.r5));
                break;
            default:
                break;
        }
    }
    //点击事件处理
    public void clickEventDo(int position){
        mTransaction = mManager.beginTransaction();

        switch (position){
            case 1:
                mTransaction.show(messFrag).hide(videosFrag).hide(foundFrag).hide(questionFrag)
                        .hide(setFrag).commit();
                break;
            case 2:
               // videosFrag = new VideosFragment(VideoTestData.getVideoItem(MainFunctionActivity.this));
                mTransaction.show(videosFrag).hide(messFrag).hide(foundFrag).hide(questionFrag)
                        .hide(setFrag).commit();

                break;
            case 3:

                mTransaction.show(foundFrag).hide(videosFrag).hide(messFrag).hide(questionFrag)
                        .hide(setFrag).commit();
                getDataFromServer();
                break;
            case 4:
                mTransaction.show(questionFrag).hide(videosFrag).hide(foundFrag).hide(messFrag)
                        .hide(setFrag).commit();
                break;
            case 5:
                mTransaction.show(setFrag).hide(videosFrag).hide(foundFrag).hide(questionFrag)
                        .hide(messFrag).commit();
            default:
                break;
        }

    }
    //资讯fragment标题数据
    public void getListTitle(){
        listofstring = new ArrayList<>();
        listofstring.add("空气");
        listofstring.add("水源");
        listofstring.add("土壤");
        listofstring.add("生物");
        listofstring.add("工业");
        listofstring.add("城市");
        listofstring.add("农村");
        listofstring.add("垃圾");
        listofstring.add("森林");
    }
    //获得用户自己的usename
    public  String getUsename(){
        SharedPreferences sp = getSharedPreferences(ConstantClass.STRING_SHAREPREFER_USE_PASS,MODE_PRIVATE);
        String getusenamer = sp.getString("usename","");
        return getusenamer;
    }
    //获取答题数正确率
    public void getDataFromServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME+
                                "GetJieGuo?&usename="+getUsename())
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure( Call call, IOException e) {
                        Looper.prepare();
                        ToastAction.startToast(MainFunctionActivity.this,"数据请求失败！");
                        Looper.loop();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Gson gson = new Gson();
                        String aa = response.body().string();
                        Log.d("hehe",aa);
                        final DaTiJieGuo daTiJieGuo = gson.fromJson(aa,new TypeToken<DaTiJieGuo>(){}.getType());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ValueAnimator.catoonShow2(foundFrag.qqStep,daTiJieGuo.getAllCount(),
                                        0,daTiJieGuo.getLvRight(),2000);

                            }
                        });

                    }
                });
            }
        }).start();
    }
    //通过网络请求，接收到排行榜数据
    public void getRankList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME+"GetRankList")
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure( Call call, IOException e) {
                        Looper.prepare();
                        ToastAction.startToast(MainFunctionActivity.this,"排行榜数据请求失败！");
                        Looper.loop();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Gson gson = new Gson();
                        //Log.d("hehe",response.body().string());
                        listofranklist = gson.fromJson(response.body().string(),new TypeToken<List<TiRank>>(){}.getType());
                        // 通知活动数据已请求到，可以更新ui了
                        Message message = new Message();
                        message.what = 3;
                        handler.sendMessage(message);
                    }
                });
            }
        }).start();
    }
    //资讯文章内容数据
    public void getListArticle(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME+"GetArticle")
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure( Call call, IOException e) {
                        Looper.prepare();
                        ToastAction.startToast(MainFunctionActivity.this,"资讯数据请求失败！");
                        Looper.loop();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Gson gson = new Gson();
                        //Log.d("hehe",response.body().string());
                        receivearticlelist = gson.fromJson(response.body().string(),new TypeToken<List<ReceiveArticleList>>(){}.getType());
                       // 通知活动数据已请求到，可以更新ui了
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                });
            }
        }).start();
    }

    //接收到的数据和视图数据进行互相传递String uid, String title, String name, String time,
    //                       String comcounts, String type, String picuid, Bitmap bitmap

    public List<ArticleList> getListofarticlelist(){
        List<ArticleList> listofart = new ArrayList<>();
        for (int i=0;i<receivearticlelist.size();i++){
            ReceiveArticleList relister = receivearticlelist.get(i);
            ArticleList lister = new ArticleList(relister.getArticleUId(),relister.getArticleTitle(),
                    relister.getAuthorName(),relister.getArticleTime(),relister.getArticleComcounts(),
                    relister.getArticleType(),relister.getArticlePicUid(),null);
            listofart.add(lister);
        }
        return listofart;
    }

}