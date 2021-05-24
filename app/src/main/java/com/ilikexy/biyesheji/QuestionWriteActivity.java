package com.ilikexy.biyesheji;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ilikexy.biyesheji.baseactivity.BaseActivity;
import com.ilikexy.biyesheji.constant.ConstantClass;
import com.ilikexy.biyesheji.constant.ToastAction;
import com.ilikexy.biyesheji.entity.ReceiveArticleList;

import java.io.IOException;
import java.util.List;

public class QuestionWriteActivity extends BaseActivity implements View.OnClickListener{
    /**两个edittext*/
    private EditText etQuestionTitle,etQuestionContent;
    private TextView tvCountTitle,tvCountContent,tvSendQuestion;
    private Boolean isCanSendQuestion = false;
    private ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_write);
        init();
    }
    public void init(){
        etQuestionTitle = (EditText)findViewById(R.id.edit_questitle_queswriteac);
        etQuestionContent = (EditText)findViewById(R.id.edit_questioncon_queswriteac);
        tvCountTitle = (TextView)findViewById(R.id.text_counttitlewords_queswriteac);
        tvCountContent = (TextView)findViewById(R.id.text_countconwords_queswriteac);
        tvSendQuestion = (TextView)findViewById(R.id.text_menu_common);
        ivBack = (ImageView)findViewById(R.id.image_back_common);
        ivBack.setOnClickListener(this);
        tvSendQuestion.setOnClickListener(this);
        /**检测标题和内容，如果有任何一方为空，则保持灰色*/
        etQuestionTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                String data = etQuestionTitle.getText().toString();//获取标题内容
                String data1 = etQuestionContent.getText().toString();//获取问题内容
                tvCountTitle.setText(""+data.length()+"/20");
                if (!data.equals("")&&!data1.equals("")){
                    isCanSendQuestion = true;
                    tvSendQuestion.setTextColor(Color.parseColor("#00aaff"));
                }else{
                    isCanSendQuestion = false;
                    tvSendQuestion.setTextColor(Color.parseColor("#ededed"));
                }
            }
        });
        etQuestionContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                String data = etQuestionTitle.getText().toString();//获取标题内容
                String data1 = etQuestionContent.getText().toString();//获取问题内容
                tvCountContent.setText(""+data1.length()+"/600");
                if (!data.equals("")&&!data1.equals("")){
                    isCanSendQuestion = true;
                    tvSendQuestion.setTextColor(Color.parseColor("#00aaff"));
                }else{
                    isCanSendQuestion = false;
                    tvSendQuestion.setTextColor(Color.parseColor("#ededed"));
                }
            }
        });
    }
    //获得用户自己的usename
    public  String getUsename(){
        SharedPreferences sp = getSharedPreferences(ConstantClass.STRING_SHAREPREFER_USE_PASS,MODE_PRIVATE);
        String getusenamer = sp.getString("usename","");
        return getusenamer;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_menu_common:
                if (isCanSendQuestion){
                    String data = etQuestionTitle.getText().toString();//获取标题内容
                    String data1 = etQuestionContent.getText().toString();//获取问题内容
                    sendQuestionToServer(data,data1);
                }
                break;
            case R.id.image_back_common:
                onBackPressed();
                break;
            default:
                break;
        }
    }
    /**将问题发布到服务器中*/
    public void sendQuestionToServer(final String dataTitle, final String dataContent){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME+
                                "SendQuestion?"+"questiontitle="+dataTitle+"&usename="+getUsename()+"&questiontime="+
                                ArticlerActivity.getTimeNow()+"&questioncontent="+dataContent)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure( Call call, IOException e) {
                        Looper.prepare();
                        ToastAction.startToast(QuestionWriteActivity.this,"服务器连接失败！请检查网络");
                        Looper.loop();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String uidd = response.body().string();
                        if (!uidd.equals("failure")){
                            //跳转到问题活动中
                            Looper.prepare();
                            ToastAction.startToast(QuestionWriteActivity.this,"问题发布成功！");
                            QuestionActivity.jumpToQuestion(QuestionWriteActivity.this,uidd);
                            Looper.loop();

                        }else{
                            Looper.prepare();
                            ToastAction.startToast(QuestionWriteActivity.this,"问题发布失败，服务器故障！");
                            Looper.loop();
                        }
                    }
                });
            }
        }).start();
    }


}