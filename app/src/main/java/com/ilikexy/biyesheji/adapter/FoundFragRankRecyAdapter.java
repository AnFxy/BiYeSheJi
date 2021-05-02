package com.ilikexy.biyesheji.adapter;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ilikexy.biyesheji.R;
import com.ilikexy.biyesheji.constant.ConstantClass;
import com.ilikexy.biyesheji.entity.QuestionItem;
import com.ilikexy.biyesheji.entity.RankItem;
import com.ilikexy.biyesheji.zidingyiview.RoundPicture;
import com.ilikexy.biyesheji.zidingyiview.RoundedSquare;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FoundFragRankRecyAdapter extends RecyclerView.Adapter<FoundFragRankRecyAdapter.MyViewHolder> {
    private List<RankItem> listofitem;
    Activity activityer;
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvOrder,tvFakename,tvRigntNumber;
        RoundedSquare rqUserPic;
        View mView;//整体控件
        public MyViewHolder(View view){
            super(view);
            mView = view;
            tvOrder = (TextView)view.findViewById(R.id.tv_order_item_foundfrag);
            tvFakename = (TextView)view.findViewById(R.id.tv_fakename_item_foundfrag);
            tvRigntNumber = (TextView)view.findViewById(R.id.tv_right_number_item_foundfrag);
            rqUserPic = (RoundedSquare)view.findViewById(R.id.roupic_usepic_item_foundfrag);
        }
    }
    //构造函数
    public FoundFragRankRecyAdapter(List<RankItem> cListofitem,Activity cactivity){
        listofitem = cListofitem;
        activityer = cactivity;
    }

    @Override
    public FoundFragRankRecyAdapter.MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foundfrag_rank_list,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

   @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RankItem item = listofitem.get(position);
        holder.tvOrder.setText(item.getOrderNumber());
        holder.tvFakename.setText(item.getFakeName());
        holder.tvRigntNumber.setText(item.getRightNumber());
        if (item.getmBitmaper()==null){//图片为空，说明第一次加载
            upDataPicture(holder,item);
        }else{
            holder.rqUserPic.setMbitmap(item.getmBitmaper());
        }
    }

    @Override
    public int getItemCount() {
        return listofitem.size();
    }
    //图片更新
    public void upDataPicture(final MyViewHolder holder, final RankItem item){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client  = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME
                                +"downloadServlet.do?picuid="+item.getmPicUid())
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
                                item.setmBitmaper(bitmap);
                                holder.rqUserPic.setMbitmap(bitmap);
                            }
                        });
                    }
                });
            }
        }).start();
    }
}
