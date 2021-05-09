package com.ilikexy.biyesheji.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ilikexy.biyesheji.MainFunctionActivity;
import com.ilikexy.biyesheji.MineFormationActivity;
import com.ilikexy.biyesheji.R;
import com.ilikexy.biyesheji.adapter.FoundRecyclerAdapter;
import com.ilikexy.biyesheji.baseactivity.BaseActivity;
import com.ilikexy.biyesheji.constant.ConstantClass;
import com.ilikexy.biyesheji.constant.ToastAction;
import com.ilikexy.biyesheji.constant.ValueAnimator;
import com.ilikexy.biyesheji.entity.DaTiJieGuo;
import com.ilikexy.biyesheji.entity.MyFound;
import com.ilikexy.biyesheji.entity.User;
import com.ilikexy.biyesheji.zidingyiview.RoundedSquare;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SetFragment extends Fragment implements View.OnClickListener{
    //构造函数传入 recyclerview数据 和 个人信息
    //private List<MyFound> myListFound;//因为我的recycle和 发现中的recycler一样，所以可以套用adapter
    private LinearLayout linear_mine_fragement;
    private RoundedSquare roundedSquare;
    private TextView text_fakename,text_usename;
    //recycler的处理
    private RecyclerView recyclerView;
    private String mUsename;
    public SetFragment(String usename){
        this.mUsename = usename;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_set,container,false);
        linear_mine_fragement = (LinearLayout)view.findViewById(R.id.linear_mine_fragment);
        roundedSquare = (RoundedSquare)view.findViewById(R.id.imag_mine_fragment);
        text_fakename = (TextView)view.findViewById(R.id.text_fakename_minefra);
        text_usename = (TextView)view.findViewById(R.id.text_usename_minefra);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_mine_fragment);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        FoundRecyclerAdapter adapter = new FoundRecyclerAdapter(getMyListFound());
        recyclerView.setAdapter(adapter);
        linear_mine_fragement.setOnClickListener(this);
//        roundedSquare.setImageBitmap(myMine.getmBitmap());
//        text_fakename.setText(myMine.getmFakeName());
//        text_usename.setText(myMine.getmUseName());
        setDataView();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linear_mine_fragment://跳转到用户信息修改界面
                BaseActivity.jump(getContext(), MineFormationActivity.class);
                break;
            default:
                break;
        }
    }
    public void setDataView(){
         new Thread(new Runnable() {
             @Override
             public void run() {
                 new Thread(new Runnable() {
                     @Override
                     public void run() {
                         OkHttpClient client = new OkHttpClient();
                         Request request = new Request.Builder()
                                 .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME+
                                         "GetInformation?usename="+mUsename)
                                 .build();
                         Call call = client.newCall(request);
                         call.enqueue(new Callback() {
                             @Override
                             public void onFailure( Call call, IOException e) {
                                 Looper.prepare();
                                 ToastAction.startToast(getContext(),"数据请求失败！");
                                 Looper.loop();
                             }

                             @Override
                             public void onResponse(Call call, Response response) throws IOException {
                                 Gson gson = new Gson();
                                 //Log.d("hehe",response.body().string());
                                 final User user = gson.fromJson(response.body().string(),new TypeToken<User>(){}.getType());
                                 getActivity().runOnUiThread(new Runnable() {
                                     @Override
                                     public void run() {
                                         text_fakename.setText(user.getFakename());
                                         text_usename.setText(user.getUsename());
                                     }
                                 });
                                 //更新头像
                                 updatePicture(user.getPicuid());
                             }
                         });
                     }
                 }).start();
             }
         }).start();
    }
    public void updatePicture(final String uid){
        new Thread(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME+
                                        "downloadServlet.do?picuid="+uid)
                                .build();
                        Call call = client.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure( Call call, IOException e) {
                                Looper.prepare();
                                ToastAction.startToast(getContext(),"数据请求失败！");
                                Looper.loop();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        roundedSquare.setMbitmap(bitmap);
                                    }
                                });
                            }
                        });
                    }
                }).start();
            }
        }).start();
    }
    public List<MyFound> getMyListFound(){
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.zhifu);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),R.drawable.shoucang);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(),R.drawable.xiangce);
        Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(),R.drawable.kabao);
        Bitmap bitmap5 = BitmapFactory.decodeResource(getResources(),R.drawable.biaoqin);
        Bitmap bitmap6 = BitmapFactory.decodeResource(getResources(),R.drawable.shezhi);
        MyFound myFound1 = new MyFound(bitmap1,"支付","",false,true);
        MyFound myFound2 = new MyFound(bitmap2,"收藏","",true,false);
        MyFound myFound3 = new MyFound(bitmap3,"相册","",true,false);
        MyFound myFound4 = new MyFound(bitmap4,"卡包","",true,false);
        MyFound myFound5 = new MyFound(bitmap5,"表情","",false,true);
        MyFound myFound6 = new MyFound(bitmap6,"设置","",true,false);
        List<MyFound> lister = new ArrayList<MyFound>();
        lister.add(myFound1);
        lister.add(myFound2);
        lister.add(myFound3);
        lister.add(myFound4);
        lister.add(myFound5);
        lister.add(myFound6);
        return lister;
    }
}
