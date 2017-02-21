package com.example.yhdj.ad0223;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button btnTxt;
    private Button btnImg;
    private ImageView img;
    private TextView txt;
    private String urlStr = "http://a1.gdcp.cn/index.shtml";
    private String urlImg = "http://avatar.csdn.net/F/F/5/1_lmj623565791.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        btnTxt = (Button) findViewById(R.id.btn_loadTxt);
        btnImg = (Button) findViewById(R.id.btn_loadImg);
        img = (ImageView) findViewById(R.id.iv_img);
        txt = (TextView) findViewById(R.id.tv_content);
        btnTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient okHttpClient  = new OkHttpClient();
                Request request = new Request.Builder().url(urlStr).build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        final String content = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txt.setText(content);
                            }
                        });
                    }
                });
            }
        });


        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //第一种方法用okhttp加载图片
//                        OkHttpClient okHttpClient = new OkHttpClient();
//                        Request request = new Request.Builder().url(urlImg).build();
//                        Call call = okHttpClient.newCall(request);
//                        call.enqueue(new Callback() {
//                            @Override
//                            public void onFailure(Request request, IOException e) {
//
//                            }
//
//                            @Override
//                            public void onResponse(Response response) throws IOException {
//                                byte[] byt  = response.body().bytes();
//                                final Bitmap bitmap = BitmapFactory.decodeByteArray(byt,0,byt.length);
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        img.setImageBitmap(bitmap);
//                                    }
//                                });
//                    }
//                });

                //第二种方法用OkHttpUtils加载图片
                OkHttpUtils
                        .get()//
                        .url(urlImg)//
                        .build()//
                        .execute(new BitmapCallback()
                        {

                            @Override
                            public void onError(okhttp3.Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(Bitmap response, int id) {
                                img.setImageBitmap(response);
                            }
                        });
            }
        });
    }
}
