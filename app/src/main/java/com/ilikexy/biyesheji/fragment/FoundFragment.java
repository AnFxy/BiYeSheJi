package com.ilikexy.biyesheji.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilikexy.biyesheji.R;
import com.ilikexy.biyesheji.TestActivity;
import com.ilikexy.biyesheji.TiCollectActivity;
import com.ilikexy.biyesheji.WrongActivity;
import com.ilikexy.biyesheji.baseactivity.BaseActivity;
import com.ilikexy.biyesheji.constant.ValueAnimator;
import com.ilikexy.biyesheji.zidingyiview.QQStep;

import androidx.fragment.app.Fragment;

public class FoundFragment extends Fragment implements View.OnClickListener{
    public static QQStep qqStep;//动态自定义控件
    private ImageView ivTest;//每日一练，答题活动进行处
    private TextView tvDoAllDay,tvCollect,tvWrong,tvRecord;
    public FoundFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_found,container,false);
        qqStep = (QQStep)view.findViewById(R.id.qqstep_foundfragment);
        ivTest = (ImageView)view.findViewById(R.id.iv_test_foundfragment);
        tvDoAllDay = (TextView)view.findViewById(R.id.tv_answer_days_foundfragment);
        tvCollect = (TextView)view.findViewById(R.id.tv_coll_foundfragment);
        tvWrong = (TextView)view.findViewById(R.id.tv_wrong_foundfragment);
        tvRecord = (TextView)view.findViewById(R.id.tv_dorecord_foundfragment);
        ivTest.setOnClickListener(this);
        tvCollect.setOnClickListener(this);
        tvWrong.setOnClickListener(this);
        tvRecord.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_test_foundfragment:
                BaseActivity.jump(getContext(), TestActivity.class);
                break;
            case R.id.tv_coll_foundfragment://收藏本
                BaseActivity.jump(getContext(), TiCollectActivity.class);
                break;
            case R.id.tv_wrong_foundfragment://错题本
                BaseActivity.jump(getContext(), WrongActivity.class);
                break;
            case R.id.tv_dorecord_foundfragment://做题记录
                break;
            default:
                break;
        }
    }
}
