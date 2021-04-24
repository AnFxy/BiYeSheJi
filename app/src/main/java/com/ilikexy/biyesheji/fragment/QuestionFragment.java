package com.ilikexy.biyesheji.fragment;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ilikexy.biyesheji.QuestionWriteActivity;
import com.ilikexy.biyesheji.R;
import com.ilikexy.biyesheji.adapter.QuestionItemListAdapter;
import com.ilikexy.biyesheji.baseactivity.BaseActivity;
import com.ilikexy.biyesheji.entity.QuestionItem;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class QuestionFragment extends Fragment implements View.OnClickListener{
    private List<QuestionItem> lister;
    private RecyclerView myRecyclerView;
    private QuestionItemListAdapter adapter;
    private ImageView ivWriterQuestion;
    public QuestionFragment(List<QuestionItem> clister){
        this.lister = clister;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_question,container,false);
        myRecyclerView = (RecyclerView)view.findViewById(R.id.rv_question_quesfragment);
        ivWriterQuestion = (ImageView)view.findViewById(R.id.image_write_questionfrag);
        ivWriterQuestion.setOnClickListener(this);
        adapter = new QuestionItemListAdapter(lister,container.getContext(),getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(container.getContext());
        myRecyclerView.setLayoutManager(manager);
        myRecyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_write_questionfrag:
                BaseActivity.jump(getContext(), QuestionWriteActivity.class);
                break;
            default:
                break;
        }
    }
}
