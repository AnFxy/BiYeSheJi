package com.ilikexy.biyesheji.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import com.ilikexy.biyesheji.R;
import com.ilikexy.biyesheji.VideoActivity;
import com.ilikexy.biyesheji.entity.RecyclerComments;
import com.ilikexy.biyesheji.entity.VideoItem;
import com.ilikexy.biyesheji.zidingyiview.RoundPicture;
import com.ilikexy.biyesheji.zidingyiview.RoundedSquare;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.MyViewHolder>{
    private List<VideoItem> listofVideoItems;
    public VideoListAdapter(List<VideoItem> lister){
        this.listofVideoItems = lister;
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        View v;
        RoundedSquare roundedSquare;
        TextView textTime,textBoFang,textComment,textTitle;
        public MyViewHolder(View view){
            super(view);
            v = view;
            roundedSquare = (RoundedSquare) view.findViewById(R.id.rsq_video_item);
            textTitle = (TextView)view.findViewById(R.id.tv_title_videofrag);
            textTime = (TextView)view.findViewById(R.id.tv_time_video_item);
            textBoFang = (TextView)view.findViewById(R.id.tv_bo_fang_count);
            textComment = (TextView)view.findViewById(R.id.tv_comments);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video,parent,false);
        final MyViewHolder holder = new MyViewHolder(view);
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                VideoItem item = listofVideoItems.get(position);
                VideoActivity.jumpToVideoAc(parent.getContext(),item.getVideoId(),"video"+(position+1)
                ,item.getBoFang());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        VideoItem videoItem = listofVideoItems.get(position);
        holder.roundedSquare.setMbitmap(videoItem.getBitmap());
        holder.textTime.setText(videoItem.getTime());
        holder.textTitle.setText(videoItem.getTitle());
        holder.textBoFang.setText(""+videoItem.getBoFang());
        holder.textComment.setText(""+videoItem.getComments());

    }

    @Override
    public int getItemCount() {
        return listofVideoItems.size();
    }
}
