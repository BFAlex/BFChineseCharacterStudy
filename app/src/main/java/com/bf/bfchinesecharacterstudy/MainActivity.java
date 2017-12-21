package com.bf.bfchinesecharacterstudy;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bf.bfchinesecharacterstudy.Model.BFBookModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Database.BFDatabaseManager;
import Fragment.BFBookAddFragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import Internal.BFHttpManager;


public class MainActivity extends Activity {

    ImageView loadingImg;
    BFBookAddFragment bookAddFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showLoadingView();
        changeToBookAddFeature();

        /* Test */
//        BFAppManager appManager = new BFAppManager();
//        appManager.checkAppPkg(this);
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
                        // 判断返回数据是否非空有效
                        JSONObject json = new JSONObject(jsonStr);
                        isOK = (json.getInt("ok") == 1);
//                        Log.i(BFConstant.BFTAG, "response:" + json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (!response.isSuccessful() || !isOK) {
                        // 恢复上一次阅读记录 或 首次登陆处理
                        // 未有历史记录
                        mainHandler.sendEmptyMessage(NO_FOUND_LAST);

                    } else {
                        // 有效数据处理
                        long delayMillis = 0;
                        Message msg = mainHandler.obtainMessage(GET_RIGHT_JSON, jsonStr);
                        mainHandler.sendMessageDelayed(msg, delayMillis);
                        Log.d(BFConstant.BFTAG, "开始处理有效数据...");
                    }
                }
            });
        }
    }

    private boolean handleConcreteData(String jsonStr) {
        // 非空 + 语文/英语
        if (!TextUtils.isEmpty(jsonStr)){
            int subjectKind = kindOfSubject(jsonStr);
            switch (subjectKind) {
                case 1:// 语文
                    // 跳转语文程序Activity
                    BFBookModel bookModel = BFBookModel.bookModel(jsonStr);
                    // 把Model转成DBModel保存到Database中
                    BFDatabaseManager.newInstance(getApplicationContext()).localSaveBookModel(bookModel);
                    break;
                case 2:
                    Log.d(BFConstant.BFTAG, "没有英语课程的相关处理程序");
                default:
                    Log.d(BFConstant.BFTAG, "没有相关索引课程的处理方法 -> subject kind:"
                            + subjectKind);
                    break;
            }
        }
        return false;
    }
    /*
    1:语文
    2：
    * */
    private int kindOfSubject(String jsonStr) {
        try {
            JSONObject json = new JSONObject(jsonStr);
            JSONObject data = json.getJSONObject("data");
            return data.getInt("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
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
    private final int GET_RIGHT_JSON = 3;

    Handler mainHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case NO_FOUND_LAST:
                    Toast.makeText(getApplication(), "查无结果", Toast.LENGTH_LONG).show();
                    showBookAddFragment();
                    break;
                case GET_RIGHT_JSON:
//                    Log.d(BFConstant.BFTAG, "handle msg:\n"+msg);
                    String jsonStr = (String) msg.obj;
                    if (handleConcreteData(jsonStr)) {
                        finish();
                    } else {
                        // 提示内容显示失败
                    }
                    break;
                default:
                    break;
            }
        }
    };
}
