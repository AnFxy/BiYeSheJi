package com.ilikexy.biyesheji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ilikexy.biyesheji.R;
import com.ilikexy.biyesheji.entity.TiRecord;
import com.ilikexy.biyesheji.entity.TiResult;
import com.ilikexy.biyesheji.entity.TiWrong;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class TiRecordAdapter extends RecyclerView.Adapter<TiRecordAdapter.MyViewHolder> {
    private List<TiRecord> listofitem;
    Context contexter;
    class MyViewHolder extends RecyclerView.ViewHolder{
    private TextView tvCount,tvRight,tvTime,tvUsetime;//标题和4个选项
    public MyViewHolder(View view){
        super(view);
        tvCount = view.findViewById(R.id.tv_count_item_tirecord);
        tvRight = view.findViewById(R.id.tv_right_item_tirecord);
        tvTime = view.findViewById(R.id.tv_time_item_tirecord);
        tvUsetime = view.findViewById(R.id.tv_usetime_item_tirecord);
       }
    }
    //构造函数
    public TiRecordAdapter(List<TiRecord> cListofitem){
        listofitem = cListofitem;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
       // holder.setIsRecyclable(false);
        contexter = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TiRecord item = listofitem.get(position);
        holder.tvCount.setText(""+item.getmCounts());
        holder.tvRight.setText(""+item.getmRight());
        holder.tvTime.setText(item.getmTime());
        holder.tvUsetime.setText(item.getmUsetime());

    }

    @Override
    public int getItemCount() {
        return listofitem.size();
    }

}

