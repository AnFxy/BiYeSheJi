package com.ilikexy.biyesheji.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ilikexy.biyesheji.R;
import com.ilikexy.biyesheji.adapter.MessConRecyAdapter;
import com.ilikexy.biyesheji.entity.ArticleList;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MessContentFragment extends Fragment {//首页消息内容每个标题点击后，下面的消息内容
    private RecyclerView mMyRecycler;//列表
    private MessConRecyAdapter mConRecyAdapter;//文章内容适配器
    private List<ArticleList> mListofArticle;//文章实体类列表
    public MessContentFragment(List<ArticleList> clist){
        mListofArticle = clist;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_message_content,
                container,false);
        mMyRecycler = (RecyclerView)view.findViewById(R.id.myrecy_messconfrag);
        mConRecyAdapter  = new MessConRecyAdapter(mListofArticle,container.getContext(),getActivity());
        //设置布局
        LinearLayoutManager manager = new LinearLayoutManager(container.getContext());
        mMyRecycler.setLayoutManager(manager);
        mMyRecycler.setAdapter(mConRecyAdapter);
        return view;
    }
}
