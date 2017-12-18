package com.bf.bfchinesecharacterstudy;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Fragment.BFBookAddFragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import third_party.BFHttpManager;


public class MainActivity extends Activity {

    ImageView loadingImg;
    BFBookAddFragment bookAddFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showLoadingView();
        changeToBookAddFeature();
    }

//    /* Private */
    private void changeToBookAddFeature() {
        hideLoadingView();
        showBookAddFragment();
    }

    private void handleBarcodeResult(int resultCode, String resultStr) {
        Log.d(BFConstant.BFTAG, "resultCode:" + resultCode + ", resultStr:" + resultStr);

        if (BFBookAddFragment.RESULT_SUCCEED == resultCode) {
            // 获取网络数据
            String urlStr = BFConstant.GET_BOOK_DETAIL_BY_BARCODE + "/" + resultStr;
            Map<String, String> params = new  HashMap<String, String>();
            params.put("info", "1");
            BFHttpManager.getInstance().requestFromURL(urlStr, params, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(BFConstant.BFTAG, "err:"+e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d(BFConstant.BFTAG, "http request response");
                    // 获取返回数据
                    String jsonStr = response.body().string();
                    boolean isOK = false;
                    try {
                        JSONObject json = new JSONObject(jsonStr);
                        isOK = (json.getInt("ok") == 1);
                        Log.i(BFConstant.BFTAG, "response:" + json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (!response.isSuccessful() || !isOK) {
                        // 恢复上一次阅读记录 或 首次登陆处理
                        // 未有历史记录
                        mainHandler.sendEmptyMessage(NO_FOUND_LAST);

                    } else {
                        // 更新当前内容
                    }
                }
            });
        }
    }

    /* Loading View */
    private void showLoadingView() {
        if (loadingImg == null) {
            loadingImg = (ImageView)findViewById(R.id.loading_anim);
        }

        AnimationDrawable anim = (AnimationDrawable)loadingImg.getBackground();
        if (anim != null) {
            loadingImg.setVisibility(View.VISIBLE);
            anim.start();
        }
    }
    private void hideLoadingView() {
        if (loadingImg == null) {
            loadingImg = (ImageView)findViewById(R.id.loading_anim);
        }

        AnimationDrawable anim = (AnimationDrawable)loadingImg.getBackground();
        if (anim != null) {
            loadingImg.setVisibility(View.INVISIBLE);
            anim.stop();
        }
    }

    /* Book Fragment */
    private void showBookAddFragment() {
        bookAddFragment = BFBookAddFragment.newInstance();
        // 增加代理接口
        bookAddFragment.setOnGetResult(new BFBookAddFragment.OnGetResult() {
            @Override
            public void onGetResult(int resultCode, String resultStr) {
                Log.d(BFConstant.BFTAG, "Add Book Fragment返回数据了...");
                handleBarcodeResult(resultCode, resultStr);
            }
        });
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        getFragmentManager().beginTransaction()
                .replace(R.id.frag_content, bookAddFragment, BFConstant.BFTAG)
                .commit();
    }

    /* Handler */
    private final int NO_FOUND_LAST = 2;
    Handler mainHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            Log.d(BFConstant.BFTAG, "msg:"+msg.toString());
            switch (msg.what) {
                case NO_FOUND_LAST:
                    Toast.makeText(getApplication(), "查无结果", Toast.LENGTH_LONG).show();
                    showBookAddFragment();
                    break;
                default:
                    break;
            }
        }
    };
}
