package com.ilikexy.biyesheji.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ilikexy.biyesheji.R;
import com.ilikexy.biyesheji.adapter.VideoListAdapter;
import com.ilikexy.biyesheji.baseactivity.BaseActivity;
import com.ilikexy.biyesheji.entity.VideoItem;
import com.ilikexy.biyesheji.model.VideoTestData;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class VideosFragment extends Fragment {
    private RecyclerView myRecyclerview;
    private BaseActivity baseActivity;
    private VideoListAdapter adapter;
    public VideosFragment(BaseActivity baseActivity){
        this.baseActivity = baseActivity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_videos,container,false);
        myRecyclerview = (RecyclerView)view.findViewById(R.id.rv_videofra);
         adapter = new VideoListAdapter(VideoTestData.getVideoItem(baseActivity));
        LinearLayoutManager manager = new LinearLayoutManager(container.getContext());
        myRecyclerview.setLayoutManager(manager);
        myRecyclerview.setAdapter(adapter);
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            adapter = new VideoListAdapter(VideoTestData.getVideoItem(baseActivity));
            myRecyclerview.setAdapter(adapter);
        }
    }
}
