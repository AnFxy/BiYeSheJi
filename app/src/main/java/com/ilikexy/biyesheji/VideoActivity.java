package com.ilikexy.biyesheji;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.ilikexy.biyesheji.baseactivity.BaseActivity;

public class VideoActivity extends BaseActivity {
    private VideoView myVideoView;
    private CheckBox mycb;
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        init();
    }
    public String getURIFromIntent(){
        Intent intent = getIntent();
        String uri = intent.getStringExtra("uri");
        return uri;
    }

     public String getWhichFromIntent(){
            Intent intent = getIntent();
            String which = intent.getStringExtra("which");
            return which;
        }
    public int getBoFangFromIntent(){
        Intent intent = getIntent();
        int bofang = intent.getIntExtra("bofang",0);
        return bofang;
    }
    public static void jumpToVideoAc(Context context, String uri,String which,int bofang){
        Intent intent = new Intent(context,VideoActivity.class);
        intent.putExtra("uri",uri);
        intent.putExtra("which",which);
        intent.putExtra("bofang",bofang);
        context.startActivity(intent);
    }
    public void init(){
        myVideoView = (VideoView)findViewById(R.id.vv_videoac);
        mycb = (CheckBox)findViewById(R.id.cb_start_stop);
        image = (ImageView)findViewById(R.id.iv_chongzhi);
        Uri uri = Uri.parse(getURIFromIntent());
        myVideoView.setVideoURI(uri);
        myVideoView.setMediaController(new MediaController(VideoActivity.this));
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (myVideoView.isPlaying()){
                  myVideoView.resume();
              }
            }
        });
        mycb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (mycb.isChecked()&&!myVideoView.isPlaying()){
                   myVideoView.start();
               }
               if (!mycb.isChecked()&&myVideoView.isPlaying()){
                   myVideoView.pause();
               }
            }
        });
        addBoFangCount();
    }
    public void addBoFangCount(){
        SharedPreferences.Editor editor = getSharedPreferences("video",MODE_PRIVATE).edit();
        editor.putInt(getWhichFromIntent(),getBoFangFromIntent()+1);
        editor.commit();
    }
}