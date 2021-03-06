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
    //?????????????????????
    private File fileer ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();//???????????????
    }
    public void init(){
        kongjianInit();
        broadcastPicture();
        //????????????????????????????????????????????????????????????????????????????????????????????????
        edit_fakename_registeractivity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isCanRegister()){//?????????????????????
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
                if (isCanRegister()){//?????????????????????
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
                if (isCanRegister()){//?????????????????????
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
    //edit????????????????????????
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
    //???????????????
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
    //??????edit??????line????????????????????????????????????????????????????????????????????????edit????????????
    public void editClickEvent(EditText editnow,EditText editother1,EditText editother2,int colorfakename,int colorzhanghao,int colormima){
        editnow.setFocusable(true);
        editnow.setFocusableInTouchMode(true);
        editother1.setFocusableInTouchMode(false);
        editother2.setFocusableInTouchMode(false);
        editnow.requestFocus();
        editnow.requestFocusFromTouch();
        view_fakename_line.setBackgroundColor(colorfakename);
        view_mima_line.setBackgroundColor(colormima);
        view_zhanghao_line.setBackgroundColor(colorzhanghao);//????????????
        InputMethodManager inputManager = (InputMethodManager)
                editnow.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);//????????????????????????===========
        inputManager.showSoftInput(editnow, 0);//???????????????
    }
    //???????????????,??????Okhttp????????????
    public void requestNet(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String getfakename = edit_fakename_registeractivity.getText().toString().trim();//????????????
               final String getusenamer = edit_zhanghao_registeractivity.getText().toString().trim();//?????????????????????
                final String getpassword = edit_mima_registeractivity.getText().toString().trim();//????????????

                OkHttpClient client = new OkHttpClient();
                //??????????????????
                MediaType mediaType=MediaType.Companion.parse("image/*; charset=utf-8");
                //??????????????????????????????????????????????????????
                RequestBody fileBody=RequestBody.Companion.create(fileer,mediaType);
                //???????????????????????????????????????????????????????????????
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
                //????????????
                Call call = client.newCall(request);
                call.enqueue(new Callback() {//??????????????????????????????
                    @Override
                    public void onFailure(Call call, IOException e) {//???????????????????????????????????????????????????????????????
                        Looper.prepare();
                        ToastAction.startToast(RegisterActivity.this,"???????????????????????????");
                        DialogContainer.dialog.setCancelable(true);
                        DialogContainer.dialog.cancel();
                        Looper.loop();
                    }
                    @Override//????????????
                    public void onResponse(Call call, Response response) throws IOException {
                        String getresult = response.body().string();//??????????????????????????????
                        Log.d("bbbb",getresult);
                        if (getresult.equals("success")){//????????????
                            Looper.prepare();
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            ToastAction.startToast(RegisterActivity.this,"???????????????");
                            DialogContainer.dialog.setCancelable(true);
                            DialogContainer.dialog.cancel();
                            //?????????????????????????????????sharePreference,?????????????????????????????????????????????????????????????????????
                            saveData(getusenamer,getpassword);
                            BaseActivity.jump(RegisterActivity.this,LoginActivity.class);
                            RegisterActivity.this.finish();
                            Looper.loop();
                        }else{//????????????
                            Looper.prepare();
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            ToastAction.startToast(RegisterActivity.this,"???????????????");
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
    //???????????????sharepreference
    public void saveData(String usename,String password){
        //????????????????????????
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
                ToastAction.startToast(RegisterActivity.this,"????????????????????????");
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
                   ToastAction.startToast(RegisterActivity.this,"??????????????????????????????");
                }else if (!isCanRegister()){
                   ToastAction.startToast(RegisterActivity.this,"?????????????????????????????????");
                }else{//??????????????????????????????
                    DialogContainer.showDialog(RegisterActivity.this,R.layout.dialog_register,R.id.load_zidiyi_register);
                    requestNet();//??????????????????
                }
                break;
            case R.id.text_xieyi:
                BaseActivity.jump(RegisterActivity.this,XieYiActivity.class);
                break;

            case R.id.img_click_touxiang://?????????
                changeTouxiang();
                break;
            default:
                break;
        }
    }
    //?????????????????????????????????????????????CaptureClass?????????????????????????????????????????????
    //??????????????????????????????????????????CaptureClass?????????????????????????????????????????????????????????startActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1://????????????
                if (resultCode == RESULT_OK) {
                    try {
                        final Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mphotoUri);
                       //uri???file
                        String[] arr = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(mphotoUri, arr, null, null, null);
                        int imgIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        String imgPath = cursor.getString(imgIndex);
                        fileer = new File(imgPath);
                        //??????????????? ????????????????????????????????????Glide????????????
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int shorter = bitmap.getWidth()>bitmap.getHeight()?bitmap.getHeight():bitmap.getWidth();//???????????????
                                Bitmap bitmap1 = Bitmap.createBitmap(bitmap,0,(bitmap.getHeight()-bitmap.getWidth())/2,
                                        shorter,shorter);
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                img_click_touxiang.setImageBitmap(bitmap1);
                                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,//????????????
                                        "Titler", "?????????");
                                //??????????????????uri?????????, Scanner scan
                                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, mphotoUri);
                                sendBroadcast(intent);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{Toast.makeText(RegisterActivity.this,"????????????",Toast.LENGTH_SHORT).show();}
                break;
            case 2:
                if (resultCode==RESULT_OK){//??????????????????
                    Uri uri = data.getData();//??????data????????????uri
                    String filePath = DcimUriget.getFilePathByUri(this, uri);//????????????uri???????????????????????????
                    if (!TextUtils.isEmpty(filePath)) {//?????????????????????
                        final Bitmap bitmaperer = BitmapFactory.decodeFile(filePath);
                        fileer = new File(filePath);//???????????????file?????????
                        Log.d("pathh",""+filePath+(bitmaperer==null));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                img_click_touxiang.setImageBitmap(bitmaperer);
                            }
                        });
                    }
                }else{Toast.makeText(RegisterActivity.this,"??????????????????",Toast.LENGTH_SHORT).show();}
                break;
            default:
                break;
        }//switchcasef??????
    }
    //???????????????
    public void changeTouxiang(){
        DialogContainer.showPictureDialog(RegisterActivity.this,R.layout.dialog_touxiang,
                "com.ilikexy.bigwork.paizhao","com.ilikexy.bigwork.xiangce");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        switch (requestCode){
            case 11:
                if (grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    new CaptureClass(RegisterActivity.this).openDcimright();//????????????
                }else{
                    Toast.makeText(this,"??????????????????????????????",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }
    //????????????
    public void broadcastPicture(){
        IntentFilter intentFilter1 = new IntentFilter("com.ilikexy.bigwork.paizhao");
        IntentFilter intentFilter2 = new IntentFilter("com.ilikexy.bigwork.xiangce");
        paizhaoReceiver = new PaizhaoReceiver();
        xiangceReceiver = new XiangceReceiver();
        registerReceiver(paizhaoReceiver,intentFilter1);
        registerReceiver(xiangceReceiver,intentFilter2);
    }
    //????????????
    class PaizhaoReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mphotoUri = new CaptureClass(RegisterActivity.this).openCamera();//????????????
        }
    }
    //??????????????????
    class XiangceReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            new CaptureClass(RegisterActivity.this).openDcim();//????????????
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(paizhaoReceiver);
        unregisterReceiver(xiangceReceiver);
    }


}