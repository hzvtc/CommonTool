package com.as.commontool.util.string;

import android.content.Context;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by FJQ on 2017/2/24.
 */
public class StringUtil {
    private static final String TAG = "StringUtil";
    private static StringUtil mInstance;
    private Context context;

    private StringUtil(Context context) {
        this.context = context;
    }

    public static StringUtil getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new StringUtil(context.getApplicationContext());
        }
        return mInstance;
    }
    //将字符串进行MD5编码
    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
