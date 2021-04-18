package com.ilikexy.biyesheji.adapter;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ilikexy.biyesheji.R;
import com.ilikexy.biyesheji.constant.CenterLayoutManager;
import com.ilikexy.biyesheji.entity.MessageTitlerClass;
import com.ilikexy.biyesheji.zidingyiview.LoryMessageTitle;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class MessageTitleAdatper extends RecyclerView.Adapter<MessageTitleAdatper.MyViewHolder> {
    private List<MessageTitlerClass> mListMessTitle;//存数据
    public static List<MyViewHolder> mListViewHolder;//存 holder
  //  private static  int chushi = 0;
    private static  int positionChosed=0;
    Context contexter;
    ViewPager viewPagerer;

    class MyViewHolder extends RecyclerView.ViewHolder{
        //就只有变色字体一个控件
        LoryMessageTitle loryMessageTitle;
        public MyViewHolder(View view){
            super(view);
            loryMessageTitle = (LoryMessageTitle) view.findViewById(R.id.lorymessagetitle);
        }
    }
    public  MessageTitleAdatper(List<MessageTitlerClass> listoflory, Context context, ViewPager viewPager){
        mListMessTitle = listoflory;
        contexter = context;
        viewPagerer = viewPager;
        mListViewHolder = new ArrayList<>();
    }
    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_messagetitle,parent,false);
        final MyViewHolder holder = new MyViewHolder(view);

        //绑定点击事件
        holder.loryMessageTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position = holder.getAdapterPosition();
                //此时这个item就是选中的
                positionChosed = position;

                //同时，这个标题的progress要变为1，其余的变为0
                positonItemFlash(position);

                //viewPagerer.setCurrentItem(position);
                viewPagerer.setCurrentItem(position,false);// 点击后跳转到制定fragment
                //而滑动到制定的fragment也会触发viewpager的滑动方法，导致上方的item跟着滑动

            }
        });
        return holder;
    }
    //sp  to  px  不多说，就是 sp转化为 px,要是控件使用者用dp怎么办，那就让他自己添加个dp转sp的函数，就你事多
    public int sp2px(int sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, this.contexter.getResources().getDisplayMetrics());
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //去除重复的listholder
        Log.d("hhhh",""+position);
        if (position>(mListViewHolder.size()-1)){
            mListViewHolder.add(holder);
        }

        MessageTitlerClass messageTitlerClass = mListMessTitle.get(position);
        int jack = messageTitlerClass.getmPositioner();

        if (jack==positionChosed){
            holder.loryMessageTitle.changeProgress(1);
        }else{
            holder.loryMessageTitle.changeProgress(0);
        }
      //  holder.loryMessageTitle.setLayoutParams(lp);
        holder.loryMessageTitle.setText(messageTitlerClass.getmName());
        holder.loryMessageTitle.setPadding(26,12,26,12);
        holder.loryMessageTitle.setmColor(messageTitlerClass.getmColor());

        //Log.d("bbbbbb",holder.loryMessageTitle.getText().toString());
    }

    @Override
    public int getItemCount() {
       // Log.d("aaaaa",mListMessTitle.size()+"");
        return mListMessTitle.size();

    }
    //用于activity调用，用作标题跳转，以及移动时字体的变化
    public  void scrollTitle(int position,float offset){

        try {
            MyViewHolder holder1 = mListViewHolder.get(position); //第一个
            MyViewHolder holder2 = mListViewHolder.get(position+1);//第二个
            holder1.loryMessageTitle.setdirction(2);
            holder2.loryMessageTitle.setdirction(1);
            holder1.loryMessageTitle.changeProgress(1-offset);
            holder2.loryMessageTitle.changeProgress(offset);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //用于移动过时，确定第几个被选中
    public void positonChosed(int position){
        positionChosed = position;
    }
    //item刷新
    public void positonItemFlash(int position){
        //同时，这个标题的progress要变为1，其余的变为0
        for(int i=0;i<mListViewHolder.size();i++){
            if (i==position){
                mListViewHolder.get(i).loryMessageTitle.changeProgress(1);
            }else{
                mListViewHolder.get(i).loryMessageTitle.changeProgress(0);
            }

        }

    }

}
