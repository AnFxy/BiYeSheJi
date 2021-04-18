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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
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
import com.ilikexy.biyesheji.entity.RecyclerComments;
import com.ilikexy.biyesheji.zidingyiview.DialogContainer;
import com.ilikexy.biyesheji.zidingyiview.RoundPicture;
import com.ilikexy.biyesheji.zidingyiview.RoundedSquare;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ArticlerActivity extends BaseActivity implements View.OnClickListener {
    private String mUid;//文章的uid,通过uid可以像服务器发出请求获取文章数据。
    //控件声明
    private TextView mTextTitleArticle,mTextAuthorName,mTextArticleTime,mTextArticleCon,mTextComments,
    mTextPans,mTextCollects;
    private ImageView mImagBack,mImagMenu,mImagArticler;
    private RoundPicture mImagAuthor;
    private Button mButtonLoadComments;
    private RecyclerView mRecyclerViewComment;
    private EditText mEditComment;
    private LinearLayout mLinearComPanColl,mLinearComs,mLinearPans,mLinearColls;
    //全局接收评论数据
    List<RecyclerComments> listOfRecyclerComments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articler);
        init();
        upDateArticler();
    }
    //初始化方法
    public void init(){
        mUid = getUidFromIntent();//获得uid
        //接下来就是漫长的控件初始方法
        mTextTitleArticle = (TextView)findViewById(R.id.text_title_articleac);
        mTextAuthorName = (TextView)findViewById(R.id.text_authorname_articlerac);
        mTextArticleTime = (TextView)findViewById(R.id.text_articletime_articlerac);
        mTextArticleCon = (TextView)findViewById(R.id.text_articlecon_articlerac);
        mTextComments = (TextView)findViewById(R.id.text_comcounts_articlerac);
        mTextPans = (TextView)findViewById(R.id.text_panscounts_articlerac);
        mTextCollects = (TextView)findViewById(R.id.text_collcounts_articlerac);
        mImagBack = (ImageView)findViewById(R.id.image_back_articleac);
        mImagMenu = (ImageView)findViewById(R.id.image_menu_articleac);
        mImagAuthor = (RoundPicture) findViewById(R.id.roupic_author_articleac);
        mImagArticler = (ImageView)findViewById(R.id.rousq_articlepic_articlerac);
        mButtonLoadComments = (Button)findViewById(R.id.btn_loadcomment_articleac);
        mRecyclerViewComment = (RecyclerView) findViewById(R.id.recycler_comment_articlerac);
        mEditComment = (EditText)findViewById(R.id.edit_comment_articlerac);
        mLinearComPanColl = (LinearLayout)findViewById(R.id.linear_compancol_articlerac);
        mLinearComs = (LinearLayout)findViewById(R.id.linear_coms_articlerac);
        mLinearPans = (LinearLayout)findViewById(R.id.linear_pans_articlerac);
        mLinearColls = (LinearLayout)findViewById(R.id.linear_collect_articlerac);
        mButtonLoadComments.setOnClickListener(this);
        mImagBack.setOnClickListener(this);
        mImagMenu.setOnClickListener(this);
        mLinearPans.setOnClickListener(this);
        mLinearColls.setOnClickListener(this);
        mLinearComs.setOnClickListener(this);
        listOfRecyclerComments = new ArrayList<>();
    }
    //从碎片中获取数据
    public String getUidFromIntent(){
        Intent intent = getIntent();
        String uidd = intent.getStringExtra("uid");
        return uidd;
    }
    //设置从碎片中传递回来数据的intent
    public static void jumpToArticler(Context context,String uid){
        Intent intent = new Intent(context,ArticlerActivity.class);
        intent.putExtra("uid",uid);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_back_articleac:
                onBackPressed();
                break;
            case R.id.image_menu_articleac:
                ToastAction.startToast(ArticlerActivity.this,"拓展功能还在开发中");
                break;
            case R.id.btn_loadcomment_articleac:
                mButtonLoadComments.setVisibility(View.GONE);
                getArticleComment();
                break;
            case R.id.linear_coms_articlerac:
                break;
            case R.id.linear_pans_articlerac:
                break;
            case R.id.linear_collect_articlerac:
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
    //网络操作方法
    public void getArticlerData(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME
                        +"GetArticleContent?articleuid="+mUid)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure( Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(ArticlerActivity.this,"无法连接服务器，请检查网络",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            @Override
            public void onResponse( Call call, Response response) throws IOException {
                String data = response.body().string();
                if (!data.equals("")) {
                    Gson gson = new Gson();
                    ArticleContent articleContent = gson.fromJson(data,new TypeToken<ArticleContent>(){}.getType());
                    setControlData(articleContent);//给文章的text控件赋予服务器的数据
                    getPictureFromServer(null,articleContent.getAuthorPicUid(),0);//给文章的作者头像，文章的图片获取
                    getPictureFromServer(null,articleContent.getArticlePicUid(),1);
                }else{
                    Looper.prepare();
                    Toast.makeText(ArticlerActivity.this,"请求文章数据失败",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        });
    }
    //给文章各个控件赋予从服务器中获取的数据
    public void setControlData(final ArticleContent articleContent){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextTitleArticle.setText(articleContent.getArticleTitle());
                mTextAuthorName.setText(articleContent.getAuthorName());
                mTextArticleTime.setText(articleContent.getArticleTime());
                mTextArticleCon.setText(articleContent.getArticleContent());
                mTextPans.setText(articleContent.getArticlePancounts());
                mTextComments.setText(articleContent.getArticleComcounts());
                mTextCollects.setText(articleContent.getArticleCollectcounts());
            }
        });
    }
    //获取文章作者的头像，以及文章的图片
    public void getPictureFromServer(final ArticleComments arComments,String picuid, final int whereWhat){
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
                Toast.makeText(ArticlerActivity.this,"无法连接服务器，请检查网络",Toast.LENGTH_SHORT).show();
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
                            case 1:
                                mImagArticler.setImageBitmap(bitmap);
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
                        +"GetArticleComment?articleuid="+mUid)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure( Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(ArticlerActivity.this,"无法连接服务器，请检查网络",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            @Override
            public void onResponse( Call call, Response response) throws IOException {
                String data = response.body().string();
                if (!data.equals("")) {
                    Gson gson = new Gson();
                    Log.d("gsoner",data);
                    List<ArticleComments> listofcomments = gson.fromJson(data,new TypeToken<List<ArticleComments>>(){}.getType());
                    for (int i=0;i<listofcomments.size();i++){//根据评论的图片id，获取图片
                        ArticleComments articleComments = listofcomments.get(i);
                        getPictureFromServer(articleComments,articleComments.getPictureUid(),2);
                    }

                }else{
                    Looper.prepare();
                    Toast.makeText(ArticlerActivity.this,"获取文章评论失败",Toast.LENGTH_SHORT).show();
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
                LinearLayoutManager manager = new LinearLayoutManager(ArticlerActivity.this);
                mRecyclerViewComment.setLayoutManager(manager);
                ArticleComRecyAdapter adapter = new ArticleComRecyAdapter(listOfRecyclerComments);
                mRecyclerViewComment.setAdapter(adapter);
            }
        });

    }

}