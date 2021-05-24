package com.ilikexy.biyesheji.fragment;

import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ilikexy.biyesheji.MainFunctionActivity;
import com.ilikexy.biyesheji.QuestionWriteActivity;
import com.ilikexy.biyesheji.R;
import com.ilikexy.biyesheji.adapter.QuestionItemListAdapter;
import com.ilikexy.biyesheji.baseactivity.BaseActivity;
import com.ilikexy.biyesheji.constant.ConstantClass;
import com.ilikexy.biyesheji.constant.ToastAction;
import com.ilikexy.biyesheji.entity.QuestionItem;

import java.io.IOException;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QuestionFragment extends Fragment implements View.OnClickListener{
    private List<QuestionItem> lister;
    private RecyclerView myRecyclerView;
    private QuestionItemListAdapter adapter;
    private ImageView ivWriterQuestion;
    //public QuestionFragment(List<QuestionItem> clister){
//        this.lister = clister;
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_question,container,false);
        myRecyclerView = (RecyclerView)view.findViewById(R.id.rv_question_quesfragment);
        ivWriterQuestion = (ImageView)view.findViewById(R.id.image_write_questionfrag);
        ivWriterQuestion.setOnClickListener(this);
        getListQuestion();
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
    //通过网络请求，接收到问题数据
    public void getListQuestion(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME+"GetQuestionItem")
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure( Call call, IOException e) {
                        Looper.prepare();
                        ToastAction.startToast(getContext(),"资讯数据请求失败！");
                        Looper.loop();
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        Gson gson = new Gson();
                        //Log.d("hehe",response.body().string());
                        lister = gson.fromJson(response.body().string(),new TypeToken<List<QuestionItem>>(){}.getType());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new QuestionItemListAdapter(lister,getContext(),getActivity());
                                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                                myRecyclerView.setLayoutManager(manager);
                                myRecyclerView.setAdapter(adapter);
                            }
                        });

                    }
                });
            }
        }).start();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden){
            getListQuestion();
        }
        super.onHiddenChanged(hidden);
    }
}
