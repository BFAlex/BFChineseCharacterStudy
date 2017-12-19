package Common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.bf.bfchinesecharacterstudy.BFConstant;

/**
 * Created by 1 on 2017/12/19.
 */

public class BFAppManager {

    public BFAppManager() {}

    /**/
    public void checkAppPkg(Context context) {
        String pkgName = context.getPackageName();context.getPackageCodePath();
        Log.d(BFConstant.BFTAG, ""+context.getPackageResourcePath());
        /*com.bf.bfchinesecharacterstudy*/
        Log.d(BFConstant.BFTAG, "App包名:" + pkgName);
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(pkgName, 0);
            Log.d(BFConstant.BFTAG, "versionCode:"+packageInfo.versionCode
                    + ", versionName:"+packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Uri pkgUri = Uri.parse("content://" + pkgName);
        Cursor cursor = context.getContentResolver().query(pkgUri, null,
                null, null, null);
        Log.d(BFConstant.BFTAG, "cursor:"+cursor);
        if (cursor != null) {
            // 打印所有的列属性
            String[] columnNames = cursor.getColumnNames();
            for (int i = 0; i < columnNames.length; i++) {
                Log.d(BFConstant.BFTAG, "column " + i + ":" + columnNames[i]);
            }
//            while (cursor.moveToNext()) {
//
//            }
        }
    }
}
