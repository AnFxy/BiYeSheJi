package com.ilikexy.biyesheji;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ilikexy.biyesheji.baseactivity.BaseActivity;
import com.ilikexy.biyesheji.zidingyiview.RoundedSquare;

public class MineFormationActivity extends BaseActivity implements View.OnClickListener{

    private TextView mTextBack;
    private RoundedSquare mRoundedSquare;
    private Bitmap mbitmap;//头像图片
    private LinearLayout mLinear;
    private RecyclerView mRecycler;
    //相机，照片通知类
//    private PaizhaoRecieverOne paizhaoReceiverOne;
//    private XiangceRecieverOne xiangceReceiverOne;
//    private FormaReciever formaReciever;
//    //图片全局uri
//    private Uri mPhotoUri;
    //全局属性变量如下
    private String mfakenamer,msex,mage,mhometown,maddress,msignature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_formation);
        init();
    }
    public void init(){
        //全局变量初始化
        mfakenamer="昵称";
        msex = "男";
        mage = "0";
        mhometown = "未填写";
        maddress = "未填写";
        msignature = "这个人很懒，还没填写";

        mbitmap = BitmapFactory.decodeResource(getResources(),R.drawable.graytouxiang);//默认头像图片
        mTextBack = (TextView)findViewById(R.id.text_back_forma);
        mRoundedSquare = (RoundedSquare)findViewById(R.id.imag_formation_activity);
        mLinear = (LinearLayout)findViewById(R.id.linear_formation_activity);
        mRecycler = (RecyclerView)findViewById(R.id.recycler_formation_activity);

        mTextBack.setOnClickListener(this);
        mLinear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        
    }
}