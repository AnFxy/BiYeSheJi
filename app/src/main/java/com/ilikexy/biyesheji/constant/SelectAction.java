package com.ilikexy.biyesheji.constant;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class SelectAction{

    //更新单个数据
    public  Call updateFormation(String c_usename,String c_titler,String c_data){

        //堵塞，网络有结果后，再返回数据，毕竟也不用那么慢
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ConstantClass.STRING_SERVICE_URL+ConstantClass.STRING_SERVICE_PROJECTNAME+
                        "Update?usename="+c_usename+"&changexiang="+c_titler+"&datater="+c_data+"")
                .build();
        //发送请求
        Call call = client.newCall(request);
        return call;
    }


}