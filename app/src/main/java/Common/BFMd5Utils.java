package Common;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 1 on 2017/12/18.
 */

public class BFMd5Utils {

    public static String md5Encrypting(String preStr) {
        if (TextUtils.isEmpty(preStr)) {
            return "";
        }

        MessageDigest md5Msg = null;
        try {
            md5Msg = MessageDigest.getInstance("MD5");
            byte[] bytes = md5Msg.digest(preStr.getBytes());
            String resultStr = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                resultStr += temp;
            }

            return resultStr;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }
}
