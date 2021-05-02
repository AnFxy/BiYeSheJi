package com.ilikexy.biyesheji.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ilikexy.biyesheji.R;
import com.ilikexy.biyesheji.entity.TiAnswer;

public class TiFragment extends Fragment{
    public TiAnswer tiAnswer;//数据来源
    private TextView tvTitle,tvContentA,tvContentB,tvContentC,tvContentD;//标题和4个选项
    private CheckBox cbA,cbB,cbC,cbD;//4个选项点击处
    public boolean isCheckedIt=false;
    public String theChosedIt="";
    public TiFragment(TiAnswer ctiAanswer){
        this.tiAnswer = ctiAanswer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_ti_answerac,container,false);
        tvTitle = view.findViewById(R.id.tv_title_tifragment);
        tvContentA = view.findViewById(R.id.tv_ti_content_a_tifragment);
        tvContentB = view.findViewById(R.id.tv_ti_content_b_tifragment);
        tvContentC = view.findViewById(R.id.tv_ti_content_c_tifragment);
        tvContentD = view.findViewById(R.id.tv_ti_content_d_tifragment);
        cbA = view.findViewById(R.id.tv_ti_xuanxiang_a_tifragment);
        cbB = view.findViewById(R.id.tv_ti_xuanxiang_b_tifragment);
        cbC = view.findViewById(R.id.tv_ti_xuanxiang_c_tifragment);
        cbD = view.findViewById(R.id.tv_ti_xuanxiang_d_tifragment);
        tvTitle.setText(tiAnswer.getmTitle());
        tvContentA.setText(tiAnswer.getmContentA());
        tvContentB.setText(tiAnswer.getmContentB());
        tvContentC.setText(tiAnswer.getmContentC());
        tvContentD.setText(tiAnswer.getmContentD());
        cbA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbA.isChecked()){
                    cbB.setChecked(false);
                    cbC.setChecked(false);
                    cbD.setChecked(false);
                    isCheckedIt = true;
                    theChosedIt = "A";
                }else{
                    isCheckedIt = false;
                    theChosedIt = "";
                }
            }
        });
        cbB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbB.isChecked()){
                    cbA.setChecked(false);
                    cbC.setChecked(false);
                    cbD.setChecked(false);
                    isCheckedIt = true;
                    theChosedIt = "B";
                }else{
                    isCheckedIt = false;
                    theChosedIt = "";
                }
            }
        });
        cbC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbC.isChecked()){
                    cbB.setChecked(false);
                    cbA.setChecked(false);
                    cbD.setChecked(false);
                    isCheckedIt = true;
                    theChosedIt = "C";
                }else{
                    isCheckedIt = false;
                    theChosedIt = "";
                }
            }
        });
        cbD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbD.isChecked()){
                    cbB.setChecked(false);
                    cbC.setChecked(false);
                    cbA.setChecked(false);
                    isCheckedIt = true;
                    theChosedIt = "D";
                }else{
                    isCheckedIt = false;
                    theChosedIt = "";
                }
            }
        });
        return view;
    }

}
