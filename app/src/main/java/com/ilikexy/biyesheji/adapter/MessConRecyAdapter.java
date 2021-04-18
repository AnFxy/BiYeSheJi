package com.ilikexy.biyesheji.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ilikexy.biyesheji.ArticlerActivity;
import com.ilikexy.biyesheji.R;
import com.ilikexy.biyesheji.baseactivity.BaseActivity;
import com.ilikexy.biyesheji.constant.ConstantClass;
import com.ilikexy.biyesheji.constant.ToastAction;
import com.ilikexy.biyesheji.entity.ArticleList;
import com.ilikexy.biyesheji.zidingyiview.RoundedSquare;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MessConRecyAdapter extends RecyclerView.Adapter<MessConRecyAdapter.MyViewHolder> {
    private List<ArticleList> mListArticle;//文章内容列表
    Context contexter;
    Activity activityer;
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mTextTitle,mTextAuthor,mTextTime,mTextComment;//文章标题，作者，时间，评论
        RoundedSquare mRoundPicture;//圆角图片
        View mView;//整体控件
        public MyViewHolder(View view){
            super(view);
            mView = view;
            mTextTitle = (TextView)view.findViewById(R.id.text_title_messconrecy);
            mTextAuthor = (TextView)view.findViewById(R.id.text_author_messconrecy);
            mTextTime = (TextView)view.findViewById(R.id.text_time_messconrecy);
            mTextComment = (TextView)view.findViewById(R.id.text_comment_messconrecy);
            mRoundPicture = (RoundedSquare)view.findViewById(R.id.image_articler_messconrecy);
        }
    }
    //构造函数
    public MessConRecyAdapter(List<ArticleList> cListArticle, Context context,Activity cactivity){
        mListArticle = cListArticle;
        contexter = context;
        activityer = cactivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_messcon,parent,false);
        final MyViewHolder holder = new MyViewHolder(view);
        //点击事件绑定，跳转到文章活动
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将uid数据传递进文章活动中
                int position = holder.getAdapterPosition();
                ArticleList articleClass = mListArticle.get(position);
                ArticlerActivity.jumpToArticler(parent.getContext(),articleClass.getArticleUId());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        //数据绑定
        ArticleList articleClass = mListArticle.get(position);//获得实体
        holder.mTextTitle.setText(articleClass.getArticleTitle());
        holder.mTextAuthor.setText(articleClass.getAuthorName());
        holder.mTextTime.setText(articleClass.getArticleTime());
        holder.mTextComment.setText(articleClass.getArticleComcounts());

        if (articleClass.getArticlePicture()==null){//图片为空，说明第一次加载
            upDataPicture(holder,articleClass);

        }else{
            holder.mRoundPicture.setMbitmap(articleClass.getArticlePicture());
        }
    }

    @Override
    public int getItemCount() {
        return mListArticle.size();
    }
    //图片更新
    public void upDataPicture(final MyViewHolder holder, final ArticleList articleClass){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client  = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME
                                +"downloadServlet.do?picuid="+articleClass.getArticlePicUid())
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure( Call call, IOException e) {
                        Log.d("theresult","网络请求失败在图片");
                    }
                    @Override
                    public void onResponse( Call call, Response response) throws IOException {
                        //判断图片是否查询到
//                        if (response.body().string().equals("error")){
//                            Looper.prepare();
//                            ToastAction.startToast(contexter,"图片加载失败");
//                            Looper.loop();
//                        }else{
//                            //把接收到的流转化为bitmap
                            InputStream in = response.body().byteStream();
                            final Bitmap bitmap = BitmapFactory.decodeStream(in);
                            activityer.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    articleClass.setArticlePicture(bitmap);
                                    holder.mRoundPicture.setMbitmap(bitmap);
                                }
                            });

  //                      }
                    }
                });
            }
        }).start();
    }
}
