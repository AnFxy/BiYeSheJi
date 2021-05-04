package com.ilikexy.biyesheji.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ilikexy.biyesheji.R;
import com.ilikexy.biyesheji.entity.RecyclerComments;
import com.ilikexy.biyesheji.zidingyiview.RoundPicture;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class TiResultRightWrongAdapter extends RecyclerView.Adapter<TiResultRightWrongAdapter.MyViewHolder>{
    private int[] wrong;
    private int allCount;
    private Context contexter;
    public TiResultRightWrongAdapter(int[] cwrong,int allcount){
        this.wrong = cwrong;
        this.allCount = allcount;
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvRightOrWrong;
        public MyViewHolder(View view){
            super(view);
            tvRightOrWrong = (TextView)view.findViewById(R.id.tv_right_wrong_item);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_right_wrong,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        contexter = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvRightOrWrong.setText(""+(position+1));
        for (int i=0;i<wrong.length;i++){
            if (position==wrong[i]){//做错了
                holder.tvRightOrWrong.setBackground(contexter.getDrawable(R.drawable.text_wrong));
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return allCount;
    }
}