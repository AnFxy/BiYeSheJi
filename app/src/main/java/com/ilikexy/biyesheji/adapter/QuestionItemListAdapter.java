package com.ilikexy.biyesheji.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ilikexy.biyesheji.ArticlerActivity;
import com.ilikexy.biyesheji.QuestionActivity;
import com.ilikexy.biyesheji.R;
import com.ilikexy.biyesheji.baseactivity.BaseActivity;
import com.ilikexy.biyesheji.constant.ConstantClass;
import com.ilikexy.biyesheji.entity.ArticleList;
import com.ilikexy.biyesheji.entity.QuestionItem;
import com.ilikexy.biyesheji.zidingyiview.RoundPicture;
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

public class QuestionItemListAdapter extends RecyclerView.Adapter<QuestionItemListAdapter.MyViewHolder> {
    private List<QuestionItem> listofitem;
    Context contexter;
    Activity activityer;
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mTextTitle,mTextAuthor,mTextTime,mTextContent,mTextAgree,mTextAnswer;
        RoundPicture mRoundPicture;//圆角图片
        View mView;//整体控件
        public MyViewHolder(View view){
            super(view);
            mView = view;
            mTextTitle = (TextView)view.findViewById(R.id.text_title_quesfragitem);
            mTextAuthor = (TextView)view.findViewById(R.id.text_usename_quesfragitem);
            mTextContent = (TextView)view.findViewById(R.id.text_questioncon_quesfragitem);
            mTextTime = (TextView)view.findViewById(R.id.text_questiontime_quesfragitem);
            mTextAgree = (TextView)view.findViewById(R.id.text_questionagree_quesfragitem);
            mTextAnswer = (TextView)view.findViewById(R.id.text_questionanswer_quesfragitem);
            mRoundPicture = (RoundPicture)view.findViewById(R.id.roupic_author_quesfragitem);
        }
    }
    //构造函数
    public QuestionItemListAdapter(List<QuestionItem> cListofitem,Context context,Activity cactivity){
         listofitem = cListofitem;
         contexter = context;
         activityer = cactivity;
    }

    @Override
    public QuestionItemListAdapter.MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_quesfrag,parent,false);
         final MyViewHolder holder = new MyViewHolder(view);
         //点击时间处理
         holder.mView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 int position = holder.getAdapterPosition();
                 QuestionItem item = listofitem.get(position);
                 QuestionActivity.jumpToQuestion(parent.getContext(),item.getQuestionUid());
             }
         });
         return holder;
    }

    @Override
    public void onBindViewHolder(QuestionItemListAdapter.MyViewHolder holder, int position) {
         QuestionItem item = listofitem.get(position);
         holder.mTextTitle.setText(item.getQuestionTitle());
         holder.mTextAuthor.setText(item.getAuthorName());
         holder.mTextTime.setText(item.getQuestionTime());
         holder.mTextContent.setText(item.getQuestionContent());
         holder.mTextAnswer.setText(item.getQuestionAnswer()+"回答");
         holder.mTextAgree.setText(item.getQuestionAgree()+"赞同 - ");
        if (item.getAuthorPic()==null){//图片为空，说明第一次加载
            upDataPicture(holder,item);

        }else{
            holder.mRoundPicture.setmMBitmap(item.getAuthorPic());
        }
    }

    @Override
    public int getItemCount() {
        return listofitem.size();
    }
    //图片更新
    public void upDataPicture(final QuestionItemListAdapter.MyViewHolder holder, final QuestionItem item){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client  = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME
                                +"downloadServlet.do?picuid="+item.getPictureUid())
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure( Call call, IOException e) {
                        Log.d("theresult","网络请求失败在图片");
                    }
                    @Override
                    public void onResponse( Call call, Response response) throws IOException {
                        InputStream in = response.body().byteStream();
                        final Bitmap bitmap = BitmapFactory.decodeStream(in);
                        activityer.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                item.setAuthorPic(bitmap);
                                holder.mRoundPicture.setmMBitmap(bitmap);
                            }
                        });
                    }
                });
            }
        }).start();
    }
}

