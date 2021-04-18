package com.ilikexy.biyesheji;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ilikexy.biyesheji.baseactivity.BaseActivity;
import com.ilikexy.biyesheji.constant.ConstantClass;
import com.ilikexy.biyesheji.constant.ToastAction;
import com.ilikexy.biyesheji.photouri.CaptureClass;
import com.ilikexy.biyesheji.photouri.DcimUriget;
import com.ilikexy.biyesheji.zidingyiview.DialogContainer;
import com.ilikexy.biyesheji.zidingyiview.RoundedSquare;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    private PaizhaoReceiver paizhaoReceiver;
    private XiangceReceiver xiangceReceiver;
    private Uri mphotoUri=null;
    private TextView text_back_register,text_china,text_xieyi;
    private Button btn_registeractivity;
    private CheckBox checkBox;
    private RoundedSquare img_click_touxiang;
    private EditText edit_zhanghao_registeractivity,edit_mima_registeractivity,edit_fakename_registeractivity;
    private View view_fakename_line,view_zhanghao_line,view_mima_line;
    //全局的图片文件
    private File fileer ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();//初始化函数
    }
    public void init(){
        kongjianInit();
        broadcastPicture();
        //按钮颜色变化，当昵称，账号，密码，头像，皆不为空时，按钮为绿色，
        edit_fakename_registeractivity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isCanRegister()){//按钮设置为绿色
                    btn_registeractivity.setBackground(getDrawable(R.drawable.button_normal));
                    btn_registeractivity.setTextColor(Color.parseColor("#ffffff"));
                }else{
                    btn_registeractivity.setBackground(getDrawable(R.drawable.button_press));
                    btn_registeractivity.setTextColor(Color.parseColor("#b1b1b1"));
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        edit_zhanghao_registeractivity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isCanRegister()){//按钮设置为绿色
                    btn_registeractivity.setBackground(getDrawable(R.drawable.button_normal));
                    btn_registeractivity.setTextColor(Color.parseColor("#ffffff"));
                }else{
                    btn_registeractivity.setBackground(getDrawable(R.drawable.button_press));
                    btn_registeractivity.setTextColor(Color.parseColor("#b1b1b1"));
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        edit_mima_registeractivity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isCanRegister()){//按钮设置为绿色
                    btn_registeractivity.setBackground(getDrawable(R.drawable.button_normal));
                    btn_registeractivity.setTextColor(Color.parseColor("#ffffff"));
                }else{
                    btn_registeractivity.setBackground(getDrawable(R.drawable.button_press));
                    btn_registeractivity.setTextColor(Color.parseColor("#b1b1b1"));
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
    //edit内容是否为空判断
    public Boolean isCanRegister(){
        String getfakename = edit_fakename_registeractivity.getText().toString().trim();
        String getzhanghao = edit_zhanghao_registeractivity.getText().toString().trim();
        String getmima = edit_mima_registeractivity.getText().toString().trim();
        if (!getfakename.equals("")&&!getzhanghao.equals("")&&!getmima.equals("")){
            return true;
        }else{
            return false;
        }
    }
    //控件初始化
    public void kongjianInit(){
        img_click_touxiang = (RoundedSquare)findViewById(R.id.img_click_touxiang);
        view_fakename_line = (View)findViewById(R.id.view_fakename_line);
        view_zhanghao_line = (View)findViewById(R.id.view_zhanghao_line);
        view_mima_line = (View)findViewById(R.id.view_mima_line);
        text_back_register = (TextView)findViewById(R.id.text_back_registeractivity);
        text_china = (TextView)findViewById(R.id.text_china);
        text_xieyi = (TextView)findViewById(R.id.text_xieyi);
        edit_zhanghao_registeractivity = (EditText)findViewById(R.id.edit_zhanghao_register);
        edit_mima_registeractivity = (EditText)findViewById(R.id.edit_mima_register);
        edit_fakename_registeractivity = (EditText)findViewById(R.id.edit_fakename_register);
        btn_registeractivity = (Button)findViewById(R.id.btn_registeractivity);
        checkBox = (CheckBox)findViewById(R.id.checkbox_registeractivity);
        text_back_register.setOnClickListener(this);
        text_china.setOnClickListener(this);
        text_xieyi.setOnClickListener(this);
        edit_zhanghao_registeractivity.setOnClickListener(this);
        edit_mima_registeractivity.setOnClickListener(this);
        edit_fakename_registeractivity.setOnClickListener(this);
        btn_registeractivity.setOnClickListener(this);
        checkBox.setOnClickListener(this);
        img_click_touxiang.setOnClickListener(this);
    }
    //点击edit，其line变为绿色，并且获得焦点，并且弹出软键盘，并且其他edit变为灰色
    public void editClickEvent(EditText editnow,EditText editother1,EditText editother2,int colorfakename,int colorzhanghao,int colormima){
        editnow.setFocusable(true);
        editnow.setFocusableInTouchMode(true);
        editother1.setFocusableInTouchMode(false);
        editother2.setFocusableInTouchMode(false);
        editnow.requestFocus();
        editnow.requestFocusFromTouch();
        view_fakename_line.setBackgroundColor(colorfakename);
        view_mima_line.setBackgroundColor(colormima);
        view_zhanghao_line.setBackgroundColor(colorzhanghao);//弹出键盘
        InputMethodManager inputManager = (InputMethodManager)
                editnow.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);//内容输入服务管理===========
        inputManager.showSoftInput(editnow, 0);//展现软键盘
    }
    //网络请求类,采用Okhttp网络框架
    public void requestNet(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String getfakename = edit_fakename_registeractivity.getText().toString().trim();//获取昵称
               final String getusenamer = edit_zhanghao_registeractivity.getText().toString().trim();//获取账号和密码
                final String getpassword = edit_mima_registeractivity.getText().toString().trim();//获取密码

                OkHttpClient client = new OkHttpClient();
                //所有图片类型
                MediaType mediaType=MediaType.Companion.parse("image/*; charset=utf-8");
                //第一层，说明数据为文件，以及文件类型
                RequestBody fileBody=RequestBody.Companion.create(fileer,mediaType);
                //第二层，指明服务表单的键名，文件名，文件体
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("upload",fileer.getName(),fileBody)
                        .addFormDataPart("fakename",getfakename)
                        .addFormDataPart("usename",getusenamer)
                        .addFormDataPart("password",getpassword)
                        .build();
                Request request = new Request.Builder()
                        .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME+"upload.action")
                        .post(requestBody)
                        .build();
                //发送请求
                Call call = client.newCall(request);
                call.enqueue(new Callback() {//实现请求结果回调接口
                    @Override
                    public void onFailure(Call call, IOException e) {//网络请求失败，说明没有网络，或者服务器出错
                        Looper.prepare();
                        ToastAction.startToast(RegisterActivity.this,"网络出错，请检查！");
                        DialogContainer.dialog.setCancelable(true);
                        DialogContainer.dialog.cancel();
                        Looper.loop();
                    }
                    @Override//请求成功
                    public void onResponse(Call call, Response response) throws IOException {
                        String getresult = response.body().string();//获得服务器传来的数据
                        Log.d("bbbb",getresult);
                        if (getresult.equals("success")){//注册成功
                            Looper.prepare();
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            ToastAction.startToast(RegisterActivity.this,"注册成功！");
                            DialogContainer.dialog.setCancelable(true);
                            DialogContainer.dialog.cancel();
                            //将注册的信息存入本地的sharePreference,同时跳转到登录界面，登录界面已经放置好账号密码
                            saveData(getusenamer,getpassword);
                            BaseActivity.jump(RegisterActivity.this,LoginActivity.class);
                            RegisterActivity.this.finish();
                            Looper.loop();
                        }else{//注册失败
                            Looper.prepare();
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            ToastAction.startToast(RegisterActivity.this,"注册失败！");
                            Log.d("theresult",response.body().string());
                            DialogContainer.dialog.setCancelable(true);
                            DialogContainer.dialog.cancel();
                            Looper.loop();
                        }
                    }
                });
            }
        }).start();

    }
    //将数据存入sharepreference
    public void saveData(String usename,String password){
        //只可以本程序使用
        SharedPreferences.Editor editor = getSharedPreferences(ConstantClass.STRING_SHAREPREFER_USE_PASS,MODE_PRIVATE).edit();
        editor.putString("usename",usename);
        editor.putString("password",password);
        editor.apply();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.text_back_registeractivity:
                RegisterActivity.this.onBackPressed();
                break;
            case R.id.edit_fakename_register:
                editClickEvent(edit_fakename_registeractivity,edit_zhanghao_registeractivity,edit_mima_registeractivity,
                        Color.parseColor("#04c35c"),Color.parseColor("#ececec"),Color.parseColor("#ececec"));
                break;
            case R.id.text_china:
                ToastAction.startToast(RegisterActivity.this,"其他区域尚未开发");
                break;
            case R.id.edit_zhanghao_register:
                editClickEvent(edit_zhanghao_registeractivity,edit_fakename_registeractivity,edit_mima_registeractivity,
                        Color.parseColor("#ececec"),Color.parseColor("#03c45c"),Color.parseColor("#ececec"));
                break;
            case R.id.edit_mima_register:
                editClickEvent(edit_mima_registeractivity,edit_zhanghao_registeractivity,edit_fakename_registeractivity,
                        Color.parseColor("#ececec"),Color.parseColor("#ececec"),Color.parseColor("#03c45c"));
                break;
            case R.id.btn_registeractivity:
                if (!checkBox.isChecked()){
                   ToastAction.startToast(RegisterActivity.this,"请勾选软件使用协议！");
                }else if (!isCanRegister()){
                   ToastAction.startToast(RegisterActivity.this,"请将必要信息填写完整！");
                }else{//加载动画，和网络访问
                    DialogContainer.showDialog(RegisterActivity.this,R.layout.dialog_register,R.id.load_zidiyi_register);
                    requestNet();//请求网络服务
                }
                break;
            case R.id.text_xieyi:
                BaseActivity.jump(RegisterActivity.this,XieYiActivity.class);
                break;

            case R.id.img_click_touxiang://换头像
                changeTouxiang();
                break;
            default:
                break;
        }
    }
    //关于换头像的问题，我封装成一个CaptureClass类可以拍照，也可以从相册选择。
    //对于启动相机和打开相册都是在CaptureClass类完成，对于照片拍照的结果处理需要重写startActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1://打开相机
                if (resultCode == RESULT_OK) {
                    try {
                        final Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mphotoUri);
                       //uri转file
                        String[] arr = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(mphotoUri, arr, null, null, null);
                        int imgIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        String imgPath = cursor.getString(imgIndex);
                        fileer = new File(imgPath);
                        //具体操作为 切换为主线程，图片压缩，Glide插入相册
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int shorter = bitmap.getWidth()>bitmap.getHeight()?bitmap.getHeight():bitmap.getWidth();//适当的裁剪
                                Bitmap bitmap1 = Bitmap.createBitmap(bitmap,0,(bitmap.getHeight()-bitmap.getWidth())/2,
                                        shorter,shorter);
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                img_click_touxiang.setImageBitmap(bitmap1);
                                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,//插入相册
                                        "Titler", "第一张");
                                //发送更新指定uri的广播, Scanner scan
                                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, mphotoUri);
                                sendBroadcast(intent);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{Toast.makeText(RegisterActivity.this,"拍照失败",Toast.LENGTH_SHORT).show();}
                break;
            case 2:
                if (resultCode==RESULT_OK){//打开相册成功
                    Uri uri = data.getData();//通过data获得图片uri
                    String filePath = DcimUriget.getFilePathByUri(this, uri);//调用根据uri获得图片路径的方法
                    if (!TextUtils.isEmpty(filePath)) {//如果路径不为空
                        final Bitmap bitmaperer = BitmapFactory.decodeFile(filePath);
                        fileer = new File(filePath);//将图片转为file形式。
                        Log.d("pathh",""+filePath+(bitmaperer==null));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                img_click_touxiang.setImageBitmap(bitmaperer);
                            }
                        });
                    }
                }else{Toast.makeText(RegisterActivity.this,"打开相册失败",Toast.LENGTH_SHORT).show();}
                break;
            default:
                break;
        }//switchcasef方法
    }
    //弹出对话框
    public void changeTouxiang(){
        DialogContainer.showPictureDialog(RegisterActivity.this,R.layout.dialog_touxiang,
                "com.ilikexy.bigwork.paizhao","com.ilikexy.bigwork.xiangce");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        switch (requestCode){
            case 11:
                if (grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    new CaptureClass(RegisterActivity.this).openDcimright();//请求成功
                }else{
                    Toast.makeText(this,"拒绝授权，程序销毁！",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }
    //注册通知
    public void broadcastPicture(){
        IntentFilter intentFilter1 = new IntentFilter("com.ilikexy.bigwork.paizhao");
        IntentFilter intentFilter2 = new IntentFilter("com.ilikexy.bigwork.xiangce");
        paizhaoReceiver = new PaizhaoReceiver();
        xiangceReceiver = new XiangceReceiver();
        registerReceiver(paizhaoReceiver,intentFilter1);
        registerReceiver(xiangceReceiver,intentFilter2);
    }
    //拍照通知
    class PaizhaoReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mphotoUri = new CaptureClass(RegisterActivity.this).openCamera();//打开相机
        }
    }
    //打开相册通知
    class XiangceReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            new CaptureClass(RegisterActivity.this).openDcim();//打开相册
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(paizhaoReceiver);
        unregisterReceiver(xiangceReceiver);
    }


}