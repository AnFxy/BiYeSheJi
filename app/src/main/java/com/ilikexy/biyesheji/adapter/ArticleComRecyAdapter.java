package com.ilikexy.biyesheji.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ilikexy.biyesheji.R;
import com.ilikexy.biyesheji.entity.RecyclerComments;
import com.ilikexy.biyesheji.zidingyiview.RoundPicture;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleComRecyAdapter extends RecyclerView.Adapter<ArticleComRecyAdapter.MyViewHolder>{
    private List<RecyclerComments> listofRecyclerComments;
    public ArticleComRecyAdapter(List<RecyclerComments> lister){
        this.listofRecyclerComments = lister;
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        RoundPicture roundPicture;
        TextView textName,textTime,textContent;
        public MyViewHolder(View view){
            super(view);
            roundPicture = (RoundPicture)view.findViewById(R.id.roupic_authorcom_recycler);
            textName = (TextView)view.findViewById(R.id.text_authornamecom_recycler);
            textTime = (TextView)view.findViewById(R.id.text_authortimecom_recycler);
            textContent = (TextView)view.findViewById(R.id.text_authorconcom_recycler);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_articlecom,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        RecyclerComments recyclerComments = listofRecyclerComments.get(position);
        holder.roundPicture.setmMBitmap(recyclerComments.getmBitmap());
        holder.textName.setText(recyclerComments.getAuthorName());
        holder.textTime.setText(recyclerComments.getComTime());
        holder.textContent.setText(recyclerComments.getComContent());
    }

    @Override
    public int getItemCount() {
        return listofRecyclerComments.size();
    }
}
