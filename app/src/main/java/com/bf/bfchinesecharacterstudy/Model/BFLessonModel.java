package com.bf.bfchinesecharacterstudy.Model;

import android.util.Log;

import com.bf.bfchinesecharacterstudy.BFConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 1 on 2017/12/20.
 */

/*
* {"name":"1 理想","chinese":[{"data":["濯(zhuó)"],"type":0,"label":"生字学习"}],"id":210153}
* */

public class BFLessonModel {

    public String id;
    public String name;
    public String dataStr;
//    public String words;

    public static BFLessonModel lessonModel(JSONObject json) {
        BFLessonModel lessonModel = new BFLessonModel();

        if (lessonModel != null) {
            Log.d(BFConstant.BFTAG, "解析Lesson Model:" + json);
            try {
                lessonModel.id = json.getString("id");
                lessonModel.name = json.getString("name");
                JSONArray chineseArr = json.getJSONArray("chinese");
                lessonModel.dataStr = lessonModel.formatDataToStr(chineseArr);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        String descStr = String.format("[id:%s, name:%s, dataStr:%s]", lessonModel.id, lessonModel.name, lessonModel.dataStr);
        Log.d(BFConstant.BFTAG, "解析的Lesson: " + descStr + "\n");
        return lessonModel;
    }

    /* Private */
    private String formatDataToStr(JSONArray chineseJsonArr) {
        if (chineseJsonArr == null) { return ""; }
        String dataStr = "";
        try {
            JSONObject dataJson = chineseJsonArr.getJSONObject(0);
            JSONArray dataArr = dataJson.getJSONArray("data");
            for (int i = 0; i < dataArr.length(); i++) {
                dataStr += dataArr.getString(i);
                if (i != dataArr.length()-1) {
                    dataStr += ",";
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }

        return dataStr;
    }
}
