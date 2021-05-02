package com.ilikexy.biyesheji.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilikexy.biyesheji.R;
import com.ilikexy.biyesheji.TestActivity;
import com.ilikexy.biyesheji.baseactivity.BaseActivity;
import com.ilikexy.biyesheji.constant.ValueAnimator;
import com.ilikexy.biyesheji.zidingyiview.QQStep;

import androidx.fragment.app.Fragment;

public class FoundFragment extends Fragment implements View.OnClickListener{
    public static QQStep qqStep;
    private ImageView ivTest;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_found,container,false);
        qqStep = (QQStep)view.findViewById(R.id.qqstep_foundfragment);
        ivTest = (ImageView)view.findViewById(R.id.iv_test_foundfragment);
        ivTest.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_test_foundfragment:
                BaseActivity.jump(getContext(), TestActivity.class);
                break;
            default:
                break;
        }
    }
}
