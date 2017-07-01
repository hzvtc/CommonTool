package com.as.commontool.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by FJQ on 2017/3/1.
 */
public class FileManager {
    private static final String TAG = "FileManager";
    private static FileManager mInstance;
    private Context context;

    private FileManager(Context context) {
        this.context = context;
    }

    public static FileManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new FileManager(context.getApplicationContext());
        }
        return mInstance;
    }

    public void writeToFile(String data,File target,boolean isAppend){
        if (!target.exists()){
            try {
                target.createNewFile();
                FileWriter fileWritter = new FileWriter(target.getName(),true);
                BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
                bufferWritter.write(data);
                bufferWritter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeToFile1(String data,File target){
        byte[] contentInBytes = data.getBytes();
        FileOutputStream fop=null;
        try {
            fop = new FileOutputStream(target);
            fop.write(contentInBytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (fop!=null){
                try {
                    fop.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String readRawTextFile(Context context, int resId) {
        InputStream is = context.getResources().openRawResource(resId);
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader buf = new BufferedReader(reader);
        StringBuilder text = new StringBuilder();
        try {
            String line;
            while ((line = buf.readLine()) != null) {
                text.append(line).append('\n');
            }
        } catch (IOException e) {
            return null;
        }
        return text.toString();
    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static String readFileContentByLines(File file) {
        StringBuffer content = new StringBuffer();
        if (!file.exists()) {
            return content.toString();
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                content.append(tempString);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return content.toString();
        } finally {
            if (reader!=null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return content.toString();
                }
            }
        }
        return content.toString();
    }

    
}
