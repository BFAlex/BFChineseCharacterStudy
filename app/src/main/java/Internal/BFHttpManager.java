package Internal;

import android.util.Log;

import com.bf.bfchinesecharacterstudy.BFConstant;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import Common.BFMd5Utils;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by 1 on 2017/12/14.
 */

public class BFHttpManager {

    private static final String device_id = "watch_api//com.readboy.hanzixuexiwatch////////";
    private static final String app_secret = "bba0fec8964863e3673d2ab848eb12db";

    // 单例索引
    private static BFHttpManager instance = null;
    private OkHttpClient httpClient = new OkHttpClient();

    private BFHttpManager() {}

    /* Public Methods */
    // Double Check Lock(DCL)单例模式
    public static synchronized BFHttpManager getInstance() {
        if (instance == null) {
            synchronized (BFHttpManager.class) {
                if (instance == null) {
                    instance = new BFHttpManager();
                }
            }
        }
        return instance;
    }

    public void requestFromURL(String urlStr, Map<String, String>params, Callback callback) {

        String url = completeUrl(urlStr, params);
        Log.d(BFConstant.BFTAG, "complete url:"+url);

        Request request = new Request.Builder().url(url).build();
        OkHttpClient httpClient = new OkHttpClient();

        try {
            httpClient.newCall(request).enqueue(callback);
        } catch (Exception e) {
            Log.d(BFConstant.BFTAG, "err:"+e);
            e.printStackTrace();
        }

    }

    /* Private */
    private String completeUrl(String preStr, Map<String, String> params) {
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        String sn = BFMd5Utils.md5Encrypting(device_id + app_secret + timeStamp);
        params.put("device_id", device_id);
        params.put("t", timeStamp);
        params.put("sn", sn);
        StringBuilder strBuilder = new StringBuilder();
        int pos = 0;
        for (String key : params.keySet()) {
            if (pos > 0) {
                strBuilder.append("&");
            }
            try {
                String strParam = String.format("%s=%s",key, URLEncoder.encode(params.get(key),"utf-8"));
                strBuilder.append(strParam);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            pos++;
        }
        return String.format("%s%s?%s", BFConstant.BASE_URL, preStr, strBuilder);
    }
}
