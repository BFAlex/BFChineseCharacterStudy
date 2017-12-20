package com.bf.bfchinesecharacterstudy.Model;

import android.util.Log;

import com.bf.bfchinesecharacterstudy.BFConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 1 on 2017/12/20.
 */

public class BFBookModel {

    public int bookSubject;
    public String bookName;
    public String bookBarcode;
    public BFLessonModel[] lessonModels;

    /*
    * 根据json数据构建模型对象
    * */
    public static BFBookModel bookModel(JSONObject json) {
        BFBookModel bookModel = new BFBookModel();
        if (bookModel != null) {
//            Log.d(BFConstant.BFTAG, "解析数据json:\n" + json);

            try {
                JSONObject bookJson = json.getJSONObject("data");
                bookModel.bookName = bookJson.getString("name");
                bookModel.bookSubject = bookJson.getInt("subject");
                bookModel.bookBarcode = bookJson.getString("barCode");
                JSONArray jsonArr = bookJson.getJSONArray("chinese");
                Log.d(BFConstant.BFTAG, "key:chinese, value:\n" + jsonArr);
                bookModel.lessonModels = new BFLessonModel[jsonArr.length()];
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject lessonJson = jsonArr.getJSONObject(i);
                    BFLessonModel lessonModel = BFLessonModel.lessonModel(lessonJson);
                    if (lessonModel != null) {
                        bookModel.lessonModels[i] = lessonModel;
                    }
                }
                Log.d(BFConstant.BFTAG, "Book Model数据初始化结果:\n" + bookModel);

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        return bookModel;
    }
    /*
    * 根据json字符串构建模型对象
    * */
    public static BFBookModel bookModel(String jsonStr) {

        JSONObject json = null;
        try {
            json = new JSONObject(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return BFBookModel.bookModel(json);
    }
}
