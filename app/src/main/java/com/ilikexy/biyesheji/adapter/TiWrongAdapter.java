package com.ilikexy.biyesheji.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ilikexy.biyesheji.QuestionActivity;
import com.ilikexy.biyesheji.R;
import com.ilikexy.biyesheji.constant.ConstantClass;
import com.ilikexy.biyesheji.entity.QuestionItem;
import com.ilikexy.biyesheji.entity.TiWrong;
import com.ilikexy.biyesheji.zidingyiview.RoundPicture;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TiWrongAdapter extends RecyclerView.Adapter<TiWrongAdapter.MyViewHolder> {
    private List<TiWrong> listofitem;
    Context contexter;
    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle,tvContentA,tvContentB,tvContentC,tvContentD;//标题和4个选项
        private CheckBox cbA,cbB,cbC,cbD;//4个选项点击处
        public MyViewHolder(View view){
            super(view);
            tvTitle = view.findViewById(R.id.tv_title_tifragment);
            tvContentA = view.findViewById(R.id.tv_ti_content_a_tifragment);
            tvContentB = view.findViewById(R.id.tv_ti_content_b_tifragment);
            tvContentC = view.findViewById(R.id.tv_ti_content_c_tifragment);
            tvContentD = view.findViewById(R.id.tv_ti_content_d_tifragment);
            cbA = view.findViewById(R.id.tv_ti_xuanxiang_a_tifragment);
            cbB = view.findViewById(R.id.tv_ti_xuanxiang_b_tifragment);
            cbC = view.findViewById(R.id.tv_ti_xuanxiang_c_tifragment);
            cbD = view.findViewById(R.id.tv_ti_xuanxiang_d_tifragment);
        }
    }
    //构造函数
    public TiWrongAdapter(List<TiWrong> cListofitem){
        listofitem = cListofitem;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_ti_answerac,parent,false);
         MyViewHolder holder = new MyViewHolder(view);
         holder.setIsRecyclable(false);
         contexter = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TiWrong item = listofitem.get(holder.getAdapterPosition());
        holder.tvTitle.setText(item.getmTitle());
        holder.tvContentA.setText(item.getmContentA());
        holder.tvContentB.setText(item.getmContentB());
        holder.tvContentC.setText(item.getmContentC());
        holder.tvContentD.setText(item.getmContentD());
        switch (item.getmAnswer()){
            case "A":
                holder.cbA.setBackgroundResource(R.drawable.text_right);
                //holder.cbA.setEnabled(false);
                break;
            case "B":
                holder.cbB.setBackgroundResource(R.drawable.text_right);
                //holder.cbB.setEnabled(false);
                break;
            case "C":
                holder.cbC.setBackgroundResource(R.drawable.text_right);
                //holder.cbC.setEnabled(false);
                break;
            case "D":
                holder.cbD.setBackgroundResource(R.drawable.text_right);
               // holder.cbD.setEnabled(false);
                break;
            default:
                break;
        }
        switch (item.getmWrong()){
            case "A":
                holder.cbA.setBackgroundResource(R.drawable.text_wrong);
                holder.cbA.setEnabled(false);
                break;
            case "B":
                holder.cbB.setBackgroundResource(R.drawable.text_wrong);
                holder.cbB.setEnabled(false);
                break;
            case "C":
                holder.cbC.setBackgroundResource(R.drawable.text_wrong);
                holder.cbC.setEnabled(false);
                break;
            case "D":
                holder.cbD.setBackgroundResource(R.drawable.text_wrong);
                holder.cbD.setEnabled(false);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listofitem.size();
    }

}
