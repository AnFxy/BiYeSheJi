package com.ilikexy.biyesheji.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ilikexy.biyesheji.R;
import com.ilikexy.biyesheji.adapter.MessageTitleAdatper;
import com.ilikexy.biyesheji.constant.CenterLayoutManager;
import com.ilikexy.biyesheji.entity.MessageTitlerClass;
import com.ilikexy.biyesheji.entity.ArticleList;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class MessageFragment extends Fragment implements ViewPager.OnPageChangeListener{
    private Context mContext;//从活动中获取上下文
    private RecyclerView mMyRecycler;//标题栏
    private MessageTitleAdatper mAdapter;//recyclerview的适配器

    private ViewPager mViewPager;//文章内容viewpager
    private FragmentManager mManager;//viewpager子项的碎片
    private FragmentPagerAdapter mPagerAdapter;//viewpager适配器
    private List<MessContentFragment> mListMessConFrag;//文章内容的碎片列表

    public List<String> mListWords;//标题数据列表
    public List<ArticleList> mListArticler;//文章数据列表

    public MessageFragment(Context context,List<String> cwords,List<ArticleList> carlist){
        mContext = context;
        mListWords = cwords;
        mListArticler = carlist;
    }
    //标题栏数据生成
    public List<MessageTitlerClass> getData(){
        List<MessageTitlerClass> listofdata = new ArrayList<MessageTitlerClass>();

        for(int i=0;i<mListWords.size();i++){
            MessageTitlerClass messageTitlerClass = new MessageTitlerClass(mListWords.get(i),
                    getResources().getColor(R.color.colorGreener,null),i);
            listofdata.add(messageTitlerClass);
        }
        return listofdata;
    }
    //88888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
    //文章内容碎片生成，Fragment数据生成,可以来自网络，传入的值可以不只是words【】
    public void getFragmentData() {
        //按照不同类型的资讯进行分类
        for (int i = 0; i < mListWords.size(); i++) {
            List<ArticleList> lister = new ArrayList<>();
            for (int j=0;j<mListArticler.size();j++){
                if (mListArticler.get(j).getArticleType().equals(mListWords.get(i))){//类型匹配
                    lister.add(mListArticler.get(j));
                }
            }
            MessContentFragment fragmenter = new MessContentFragment(lister);
            mListMessConFrag.add(fragmenter);
        }
    }
    //8888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListMessConFrag = new ArrayList<MessContentFragment>();
        getFragmentData();
        Log.d("haha","oncreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_message,container,false);
            mManager = getActivity().getSupportFragmentManager();
            mViewPager = (ViewPager)view.findViewById(R.id.myviewpager_messfrag);
            mMyRecycler = (RecyclerView)view.findViewById(R.id.myrecycler_messfrag);
            viewPagerAdapterDo();
            mAdapter = new MessageTitleAdatper(getData(),mContext,mViewPager);
            CenterLayoutManager manager = new CenterLayoutManager(mContext);
            manager.setOrientation(RecyclerView.HORIZONTAL);
            mMyRecycler.setLayoutManager(manager);
            mMyRecycler.setAdapter(mAdapter);
        Log.d("haha","oncreateview");
        return view;
    }


    //适配器配置
    public void viewPagerAdapterDo(){
        mPagerAdapter = new FragmentPagerAdapter(mManager,0) {
            @Override
            public Fragment getItem(int position) {
                return mListMessConFrag.get(position);
            }
            @Override
            public int getCount() {
                return mListMessConFrag.size();
            }
        };
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
    }
     //每当滑动时，都会调用的方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset==0){
             //第一个可见位置
            int firstItem = mMyRecycler.getChildLayoutPosition(mMyRecycler.getChildAt(0));
            // 最后一个可见位置
            int lastItem = mMyRecycler.getChildLayoutPosition(mMyRecycler.getChildAt(mMyRecycler.getChildCount() - 1));
            //Log.d("nono","当前可见第一个位置为"+firstItem+"最后一个位置为"+lastItem);
            if (position<firstItem||position>lastItem){//当前item不在可视范围
                mAdapter.positonItemFlash(position);
                mMyRecycler.smoothScrollToPosition(position);
            }
        }
        mAdapter.scrollTitle(position,positionOffset);
    }
    //每当滑动后调用的方法
    @Override
    public void onPageSelected(int position) {
          mAdapter.positonChosed(position);

          mMyRecycler.smoothScrollToPosition(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
