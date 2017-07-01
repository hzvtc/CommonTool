package com.as.commontool.util.log;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;

import cn.jesse.nativelogger.NLogger;
import cn.jesse.nativelogger.formatter.SimpleFormatter;
import cn.jesse.nativelogger.logger.LoggerLevel;
import cn.jesse.nativelogger.util.CrashWatcher;

/**
 *
 */
public class NativeLogger {
    private static final String TAG = "PalLog";
    private static NativeLogger mInstance;
    private NLogger.Builder mBuilder;
    private NLogger mNLogger;
    private static Context context;
    private static String path;//设置日志输出目录
    private static int period = 1;//设置文件的过期时间
    //    private LoggerLevel level = LoggerLevel.DEBUG;
    private final String SUFFIX_TXT = ".txt";
    private final String SUFFIX_LCK = ".lck";
    private static final String TEMPLATE_DATE = "yyyy-MM-dd";
    //是否需要重新配置
    private static boolean isNeedReConfig = false;
    private static int generation = 0;

    public void setPath(String path) {
        NativeLogger.path = path;
    }

    public void setPeriod(int periodTime) {
        period = periodTime;
    }

    public static void setContext(Context mContext) {
        context = mContext;
    }

    private NativeLogger() {
        if (path == null || path.trim().equals("")) {
            //默认输出路径
            path = getLogDir(context).getPath();
        }
        if (mBuilder == null) {
            mBuilder = NLogger.getInstance()
                    .builder()
                    .loggerLevel(LoggerLevel.DEBUG)
                    .fileLogger(true)
                    .fileDirectory(path)
                    .fileFormatter(new SimpleFormatter())//日志文件名格式
                    .expiredPeriod(period)
                    .catchException(true, new CrashWatcher.UncaughtExceptionListener() {
                        @Override
                        public void uncaughtException(Thread thread, Throwable ex) {
                            android.os.Process.killProcess(android.os.Process.myPid());
                        }
                    });
        }
        this.mNLogger = mBuilder.build();
    }

    public static NativeLogger getInstanse() {
        if (mInstance == null) {
            mInstance = new NativeLogger();
        }
        return mInstance;
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(TEMPLATE_DATE);
        return sdf.format(System.currentTimeMillis());
    }

    private static File getVmsDir(Context context) {
        File dir = null;
        if (Environment.getExternalStorageState().equals("mounted")) {
            dir = new File(Environment.getExternalStorageDirectory().getPath(), getAppName());
            if (!dir.exists()) {
                dir.mkdirs();
            }
        } else {
            dir = context.getFilesDir();
        }

        return dir;
    }

    /**
     * 获取应用程序名称
     */
    private static String getAppName() {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static File getLogDir(Context context) {
        File dir = new File(getVmsDir(context), "logs");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    //重命名2017-01-27.1为2017-01-27
    private void renameFile() {
        if (generation != 0) {
            File logFile = new File(path + File.separator + getCurrentDate() + "." + generation);
//            generation=0;
            File targetLogFile = new File(path + File.separator + getCurrentDate());
            if (logFile.exists() && logFile.isFile()) {
                logFile.renameTo(targetLogFile);
                File lckLogFile = new File(path + File.separator + getCurrentDate() + "." + generation + SUFFIX_LCK);
                if (lckLogFile.exists()) {
                    lckLogFile.delete();
                }
            }
        }
    }

    public static void i(String msg) {
        getInstanse().ni(msg);
    }

    public static void i(String tag, String msg) {
        getInstanse().ni(tag, msg);
    }

    public static void i(String tag, String format, Object arg) {
        getInstanse().ni(tag, format, arg);
    }

    public static void i(String tag, String format, Object argA, Object argB) {
        getInstanse().ni(tag, format, argA, argB);
    }

    public static void i(String tag, String format, Object... args) {
        getInstanse().ni(tag, format, args);
    }

    public static void i(String tag, Throwable ex) {
        getInstanse().ni(tag, ex);
    }


    private void ni(String msg) {
        checkStorageLog();
        NLogger.i(msg);
    }

    private void ni(String tag, String msg) {
        checkStorageLog();
        NLogger.i(tag, msg);
    }

    private void ni(String tag, String format, Object arg) {
        checkStorageLog();
        NLogger.i(tag, format, arg);
    }

    private void ni(String tag, String format, Object argA, Object argB) {
        checkStorageLog();
        NLogger.i(tag, format, argA, argB);
    }

    private void ni(String tag, String format, Object... args) {
        checkStorageLog();
        NLogger.i(tag, format, args);
    }

    private void ni(String tag, Throwable ex) {
        checkStorageLog();
        NLogger.i(tag, ex);
    }

    public static void d(String msg) {
        getInstanse().nd(msg);
    }

    public static void d(String tag, String msg) {
        getInstanse().nd(tag, msg);
    }

    public static void d(String tag, String format, Object arg) {
        getInstanse().nd(tag, format, arg);
    }

    public static void d(String tag, String format, Object argA, Object argB) {
        getInstanse().nd(tag, format, argA, argB);
    }

    public static void d(String tag, String format, Object... args) {
        getInstanse().nd(tag, format, args);
    }

    public static void d(String tag, Throwable ex) {
        getInstanse().nd(tag, ex);
    }

    private void nd(String msg) {
        checkStorageLog();
        NLogger.d(msg);
    }

    private void nd(String tag, String msg) {
        checkStorageLog();
        NLogger.d(tag, msg);
    }

    private void nd(String tag, String format, Object arg) {
        checkStorageLog();
        NLogger.d(tag, format, arg);
    }

    private void nd(String tag, String format, Object argA, Object argB) {
        checkStorageLog();
        NLogger.d(tag, format, argA, argB);
    }

    private void nd(String tag, String format, Object... args) {
        checkStorageLog();
        NLogger.d(tag, format, args);
    }

    private void nd(String tag, Throwable ex) {
        checkStorageLog();
        NLogger.d(tag, ex);
    }

    public static void w(String msg) {
        getInstanse().nw(msg);
    }

    public static void w(String tag, String msg) {
        getInstanse().nw(tag, msg);
    }

    public static void w(String tag, String format, Object arg) {
        getInstanse().nw(tag, format, arg);
    }

    public static void w(String tag, String format, Object argA, Object argB) {
        getInstanse().nw(tag, format, argA, argB);
    }

    public static void w(String tag, String format, Object... args) {
        getInstanse().nw(tag, format, args);
    }

    public static void w(String tag, Throwable ex) {
        getInstanse().nw(tag, ex);
    }

    private void nw(String msg) {
        checkStorageLog();
        NLogger.w(msg);
    }

    private void nw(String tag, String msg) {
        checkStorageLog();
        NLogger.w(tag, msg);
    }

    private void nw(String tag, String format, Object arg) {
        checkStorageLog();
        NLogger.w(tag, format, arg);
    }

    private void nw(String tag, String format, Object argA, Object argB) {
        checkStorageLog();
        NLogger.w(tag, format, argA, argB);
    }

    private void nw(String tag, String format, Object... args) {
        checkStorageLog();
        NLogger.w(tag, format, args);
    }

    private void nw(String tag, Throwable ex) {
        checkStorageLog();
        NLogger.w(tag, ex);
    }

    public static void e(String msg) {
        getInstanse().ne(msg);
    }

    public static void e(String tag, String msg) {
        getInstanse().ne(tag, msg);
    }

    public static void e(String tag, String format, Object arg) {
        getInstanse().ne(tag, format, arg);
    }

    public static void e(String tag, String format, Object argA, Object argB) {
        getInstanse().ne(tag, format, argA, argB);
    }

    public static void e(String tag, String format, Object... args) {
        getInstanse().ne(tag, format, args);
    }

    public static void e(String tag, Throwable ex) {
        getInstanse().ne(tag, ex);
    }

    private void ne(String msg) {
        checkStorageLog();
        NLogger.e(msg);
    }

    private void ne(String tag, String msg) {
        checkStorageLog();
        NLogger.e(tag, msg);
    }

    private void ne(String tag, String format, Object arg) {
        checkStorageLog();
        NLogger.e(tag, format, arg);
    }

    private void ne(String tag, String format, Object argA, Object argB) {
        checkStorageLog();
        NLogger.e(tag, format, argA, argB);
    }

    private void ne(String tag, String format, Object... args) {
        checkStorageLog();
        NLogger.e(tag, format, args);
    }

    private void ne(String tag, Throwable ex) {
        checkStorageLog();
        NLogger.e(tag, ex);
    }

    public static void json(LoggerLevel level, String msg) {
        getInstanse().njson(level, msg);
    }

    public static void json(LoggerLevel level, String subTag, String msg) {
        getInstanse().njson(level, subTag, msg);
    }

    private void njson(LoggerLevel level, String msg) {
        checkStorageLog();
        NLogger.json(level, msg);
    }

    private void njson(LoggerLevel level, String subTag, String msg) {
        checkStorageLog();
        NLogger.json(level, subTag, msg);
    }


    /**
     * 检查本地日志文件是否存在 不存在则重新初始化
     * nativelogger本地日子删除后会生成例如2017-02-13.1的文件
     */
    private void checkStorageLog() {
        File targetLogFile = new File(path + File.separator + getCurrentDate());
        if (!targetLogFile.exists() && !isNeedReConfig) {
            isNeedReConfig = true;
            generation++;
            if (mBuilder!=null){
                mBuilder.build();
            }
            renameFile();
        } else {
            isNeedReConfig = false;
        }

    }

}

