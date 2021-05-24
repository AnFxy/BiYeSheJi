package com.ilikexy.biyesheji;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ilikexy.biyesheji.adapter.ArticleComRecyAdapter;
import com.ilikexy.biyesheji.baseactivity.BaseActivity;
import com.ilikexy.biyesheji.constant.ConstantClass;
import com.ilikexy.biyesheji.constant.ToastAction;
import com.ilikexy.biyesheji.entity.ArticleComments;
import com.ilikexy.biyesheji.entity.ArticleContent;
import com.ilikexy.biyesheji.entity.QuestionItem;
import com.ilikexy.biyesheji.entity.RecyclerComments;
import com.ilikexy.biyesheji.zidingyiview.RoundPicture;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuestionActivity extends BaseActivity implements View.OnClickListener {
    private String mUid;
    //控件声明
    private TextView mTextTitleArticle,mTextAuthorName,mTextArticleTime,mTextArticleCon,mTextComments,
            mTextPans;
    private ImageView mImagBack,mImagMenu,mImagPan;
    private RoundPicture mImagAuthor;
    private Button mButtonLoadComments,mButtonSendComments;
    private RecyclerView mRecyclerViewComment;
    private EditText mEditComment;
    private LinearLayout mLinearComPanColl,mLinearComs,mLinearPans;
    //全局接收评论数据
    List<RecyclerComments> listOfRecyclerComments;
    //是否可发送评论
    boolean isCanLogin = false;
    boolean isPaned = false;
    //评论数，点赞数，收藏数
    int comments = 0;
    int pans = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        init();
        upDateArticler();
    }
    //从碎片中获取数据
    public String getUidFromIntent(){
        Intent intent = getIntent();
        String uidd = intent.getStringExtra("uid");
        return uidd;
    }
    //设置从碎片中传递回来数据的intent
    public static void jumpToQuestion(Context context, String uid){
        Intent intent = new Intent(context,QuestionActivity.class);
        intent.putExtra("uid",uid);
        context.startActivity(intent);
    }
    //获得用户自己的usename
    public  String getUsename(){
        SharedPreferences sp = getSharedPreferences(ConstantClass.STRING_SHAREPREFER_USE_PASS,MODE_PRIVATE);
        String getusenamer = sp.getString("usename","");
        return getusenamer;
    }
    //获得系统时间
    public static String getTimeNow(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
    //初始化方法
    public void init(){
        mUid = getUidFromIntent();//获得uid
        //接下来就是漫长的控件初始方法
        mTextTitleArticle = (TextView)findViewById(R.id.text_title_questionac);
        mTextAuthorName = (TextView)findViewById(R.id.text_authorname_questionac);
        mTextArticleTime = (TextView)findViewById(R.id.text_authortime_questionac);
        mTextArticleCon = (TextView)findViewById(R.id.text_questioncon_questionac);
        mTextComments = (TextView)findViewById(R.id.text_comcounts_questionac);
        mTextPans = (TextView)findViewById(R.id.text_panscounts_questionac);
        mImagBack = (ImageView)findViewById(R.id.image_back_questionac);
        mImagMenu = (ImageView)findViewById(R.id.image_menu_questionac);
        mImagPan = (ImageView)findViewById(R.id.image_pan_questionac);
        mImagAuthor = (RoundPicture) findViewById(R.id.roupic_author_questionac);
        mButtonLoadComments = (Button)findViewById(R.id.btn_loadcomment_questionac);
        mButtonSendComments = (Button)findViewById(R.id.btn_sendcom_questionac);
        mRecyclerViewComment = (RecyclerView) findViewById(R.id.recycler_comment_questionac);
        mEditComment = (EditText)findViewById(R.id.edit_comment_questionac);
        mLinearComPanColl = (LinearLayout)findViewById(R.id.linear_compancol_questionac);
        mLinearComs = (LinearLayout)findViewById(R.id.linear_coms_questionac);
        mLinearPans = (LinearLayout)findViewById(R.id.linear_pans_questionac);
        mButtonLoadComments.setOnClickListener(this);
        mButtonSendComments.setOnClickListener(this);
        mEditComment.setOnClickListener(this);
        mImagBack.setOnClickListener(this);
        mImagMenu.setOnClickListener(this);
        mLinearPans.setOnClickListener(this);
        mLinearComs.setOnClickListener(this);
        //edit输入内容时，发送按钮才会变颜色
        mEditComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textChangeShow();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        listOfRecyclerComments = new ArrayList<>();

    }
    //可编辑文本发生变化时，按钮的背景以及透明度
    public void textChangeShow(){
        String getComments = mEditComment.getText().toString().trim();
        if (!getComments.equals("")){//edittext有数据时
            isCanLogin = true;
            mButtonSendComments.setAlpha(1);
        }else{
            isCanLogin = false;
            mButtonSendComments.setAlpha(0.5f);
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_back_questionac:
                onBackPressed();
                break;
            case R.id.image_menu_questionac:
                ToastAction.startToast(QuestionActivity.this,"拓展功能还在开发中");
                break;
            case R.id.btn_loadcomment_questionac:
                mButtonLoadComments.setVisibility(View.GONE);
                getArticleComment();
                break;
            case R.id.edit_comment_questionac:
                mLinearComPanColl.setVisibility(View.GONE);
                mButtonSendComments.setVisibility(View.VISIBLE);
                mEditComment.setFocusable(true);
                mEditComment.setFocusableInTouchMode(true);
                mEditComment.requestFocus();
                mEditComment.requestFocusFromTouch();
                InputMethodManager inputManager = (InputMethodManager)
                        mEditComment.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(mEditComment, 0);
                break;
            case R.id.btn_sendcom_questionac:
                sendArticleComment();
                break;
            case R.id.linear_coms_questionac:
                mButtonLoadComments.setVisibility(View.GONE);
                getArticleComment();
                break;
            case R.id.linear_pans_questionac:
                if (isPaned){
                    updatePanAndColl(2);
                }else{
                    updatePanAndColl(1);
                }
                break;
            default:
                break;
        }
    }
    //根据uid请求获取文章全部内容
    public void upDateArticler(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //网络操作方法
                getArticlerData();
            }
        }).start();
    }
    //根据uid和usename 获取是否点赞，是否收藏
    public void selectIsPanAndColl(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //网络操作方法
                selectIsPanAndColls();
            }
        }).start();
    }
    //更新点赞，收藏，有4种情况，点赞的，取消点赞的，收藏的，取消收藏的
    public void updatePanAndColl(final int what){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //网络操作方法
                updatePanAndColls(what);
            }
        }).start();
    }
    public void updatePanAndColls(int what){
        String loginer = "";
        String isinsert = "";
        switch (what){
            case 1:
                loginer = "UpdateQuestionPan";
                isinsert = "true";
                break;
            case 2:
                loginer = "UpdateQuestionPan";
                isinsert = "false";
                break;
            default:
                break;
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME
                        +loginer+"?questionuid="+mUid+"&usename="+getUsename()+"&isInsert="+isinsert)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure( Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(QuestionActivity.this,"无法连接服务器，请检查网络",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                final String data = response.body().string();
                Looper.prepare();
                ToastAction.startToast(QuestionActivity.this,data);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (data.equals("点赞成功！")){
                            isPaned = true;
                            pans++;
                            mImagPan.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.panorange));
                            mTextPans.setText(""+pans);
                        }else if (data.equals("取消点赞成功！")){
                            isPaned = false;
                            pans--;
                            mImagPan.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.pangray));
                            mTextPans.setText(""+pans);
                        }
                    }
                });

            }
        });
    }
    //网络操作方法
    public void selectIsPanAndColls(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME
                        +"SelectQuestionIsPan?questionuid="+mUid+"&usename="+getUsename())
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure( Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(QuestionActivity.this,"无法连接服务器，请检查网络",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            @Override
            public void onResponse( Call call, Response response) throws IOException {
                final String data = response.body().string();
                Log.d("heihei",data);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (data.equals("true")) {
                            isPaned = true;
                            mImagPan.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.panorange));

                        }else if(data.equals("false")){
                            isPaned = false;

                            mImagPan.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.pangray));

                        }else{

                            Toast.makeText(QuestionActivity.this,"请求是否点赞数据失败",Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });
    }
    //网络操作方法
    public void getArticlerData(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME
                        +"GetQuestionContent?questionuid="+mUid)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure( Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(QuestionActivity.this,"无法连接服务器，请检查网络",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            @Override
            public void onResponse( Call call, Response response) throws IOException {
                String data = response.body().string();
                if (!data.equals("")) {
                    Gson gson = new Gson();
                    QuestionItem articleContent = gson.fromJson(data,new TypeToken<QuestionItem>(){}.getType());
                    setControlData(articleContent);//给文章的text控件赋予服务器的数据
                    getPictureFromServer(null,articleContent.getPictureUid(),0);//给文章的作者头像，文章的图片获取
                }else{
                    Looper.prepare();
                    Toast.makeText(QuestionActivity.this,"请求问题的数据失败",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        });
    }
    //给文章各个控件赋予从服务器中获取的数据
    public void setControlData(final QuestionItem articleContent){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextTitleArticle.setText(articleContent.getQuestionTitle());
                mTextAuthorName.setText(articleContent.getAuthorName());
                mTextArticleTime.setText(articleContent.getQuestionTime());
                mTextArticleCon.setText(articleContent.getQuestionContent());
                comments = Integer.valueOf(articleContent.getQuestionAnswer());
                pans = Integer.valueOf(articleContent.getQuestionAgree());
                mTextPans.setText(articleContent.getQuestionAgree());
                mTextComments.setText(articleContent.getQuestionAnswer());
            }
        });
    }
    //点击发送评论的按钮，将会隐藏 评论，点赞和收藏
    public void sendArticleComment(){
        if (isCanLogin){//是否能够发送评论
            final String getDataCommentContent = mEditComment.getText().toString();//获得评论内容,网络请求发送评论
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sendArticleComments(getDataCommentContent);
                }
            }).start();
        }
    }
    //通过网络请求发送评论到服务器中
    public void sendArticleComments(String texter){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME
                        +"UpdateQuestionComment?questionuid="+mUid+"&usename="+getUsename()+"&commentcontent="+texter+
                        "&commenttime="+getTimeNow()+"&isInsert=true")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure( Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(QuestionActivity.this,"无法连接服务器，请检查网络",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            @Override
            public void onResponse( Call call, Response response) throws IOException {
                String data = response.body().string();
                if (data.equals("success")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mEditComment.setText("");//内容置为空
                            mButtonSendComments.setVisibility(View.GONE);//发布按钮隐藏
                            mLinearComPanColl.setVisibility(View.VISIBLE);//三剑客出来
                            comments++;
                            mTextComments.setText(""+comments);
                            //更新评论呗
                            getArticleComment();
                        }
                    });
                    Looper.prepare();
                    Toast.makeText(QuestionActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
                    Looper.loop();

                }else{
                    Looper.prepare();
                    Toast.makeText(QuestionActivity.this,"发布评论失败",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        });
    }
    //获取文章作者的头像，以及文章的图片
    public void getPictureFromServer(final ArticleComments arComments, String picuid, final int whereWhat){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME
                        +"downloadServlet.do?picuid="+picuid)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure( Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(QuestionActivity.this,"无法连接服务器，请检查网络",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            @Override
            public void onResponse( Call call, Response response) throws IOException {
                InputStream in = response.body().byteStream();
                final Bitmap bitmap = BitmapFactory.decodeStream(in);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (whereWhat){
                            case 0:
                                mImagAuthor.setmMBitmap(bitmap);
                                break;
                            case 2:
                                RecyclerComments recyclerComments = new RecyclerComments(arComments.getAuthorName(),bitmap,
                                        arComments.getComContent(),arComments.getComTime());
                                listOfRecyclerComments.add(recyclerComments);
                                //更新评论图片ui
                                upDateUIComments();
                                break;
                            default:
                                break;
                        }

                    }
                });

            }
        });
    }
    //根据uid获取文章的全部评论
    public void getArticleComment(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //网络操作方法
                getArticleComments();
            }
        }).start();
    }
    //根据uid获取文章的全部评论
    public void getArticleComments(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME
                        +"GetQuestionComment?questionuid="+mUid)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure( Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(QuestionActivity.this,"无法连接服务器，请检查网络",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            @Override
            public void onResponse( Call call, Response response) throws IOException {
                String data = response.body().string();
                if (!data.equals("")) {
                    listOfRecyclerComments.clear();
                    Gson gson = new Gson();
                    Log.d("gsoner",data);
                    List<ArticleComments> listofcomments = gson.fromJson(data,new TypeToken<List<ArticleComments>>(){}.getType());
                    for (int i=0;i<listofcomments.size();i++){//根据评论的图片id，获取图片
                        ArticleComments articleComments = listofcomments.get(i);
                        getPictureFromServer(articleComments,articleComments.getPictureUid(),2);
                    }

                }else{
                    Looper.prepare();
                    Toast.makeText(QuestionActivity.this,"获取文章评论失败",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        });
    }
    //更新评论图片ui
    public void upDateUIComments(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager manager = new LinearLayoutManager(QuestionActivity.this);
                mRecyclerViewComment.setLayoutManager(manager);
                ArticleComRecyAdapter adapter = new ArticleComRecyAdapter(listOfRecyclerComments);
                mRecyclerViewComment.setAdapter(adapter);
            }
        });

    }
    @Override
    public void onBackPressed() {
        BaseActivity.jump(QuestionActivity.this,MainFunctionActivity.class);
    }
}