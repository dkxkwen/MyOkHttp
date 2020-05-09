package com.vikily.okhttp.util;

import android.os.Environment;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 显示当前行号的Log日志
 */
public class VLog {
    public static String TAG = "VLog";

    public static final String LOG_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Log/";
    private static final int SAVE_LOG_DAYS = 2;

    private static boolean isDebug = true;
    private static boolean isSaveToFile = true;

    private static String LOG_FILE_PATH;
    private static File logFile;
    private static FileWriter fileWriter;

    private static String currentDate;


    private static ExecutorService mThreadPool = Executors.newSingleThreadExecutor();



    private static volatile VLog mInstance = null;


    private VLog() {

    }

    public static VLog getInstance() {
        if (mInstance == null) {
            synchronized (TimeUtil.class) {
                if (mInstance == null) {
                    mInstance = new VLog();
                }
            }
        }
        return mInstance;
    }


    private static String generateTag() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s(%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(Locale.CHINA, tag, callerClazzName, caller.getLineNumber());
        tag = TAG + ": " + tag;

        return tag;
    }

    public static void e(String msg) {
        checkDate();

        Log.e(generateTag(), msg);

        if (isSaveToFile) {
            writeToFile(generateTag() + "\t" + msg);
        }
    }

    public static void w(String msg) {
        if (isDebug)
            Log.w(generateTag(), msg);

        if (isSaveToFile) {
            writeToFile(generateTag() + "\t" + msg);
        }
    }


    public static void i(String msg) {
        checkDate();

        if (isDebug)
            Log.i(generateTag(), msg);

        if (isSaveToFile) {
            writeToFile(generateTag() + "\t" + msg);
        }
    }

    public static void d(String msg) {
        checkDate();

        if (isDebug)
            Log.d(generateTag(), msg);

        if (isSaveToFile) {
            writeToFile(generateTag() + "\t" + msg);
        }
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(generateTag(), msg);

        if (isSaveToFile) {
            writeToFile(generateTag() + "\t" + msg);
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (isDebug)
            Log.w(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }

    public static void t(String logMsg) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            d(logMsg + " (main, id=" + Thread.currentThread().getId() + ")");
        } else {
            d(logMsg + " (NOT Main, id=" + Thread.currentThread().getId() + ")");
        }
    }

    public void setDebug(boolean value) {
        isDebug = value;
    }
    public void setIsSaveToFile(boolean value) {
        isSaveToFile = value;
    }

    public static void setTag(String tag) {
        TAG = tag;
    }


    private static void writeToFile(final String msg) {
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!logFile.exists() && !logFile.createNewFile()) {
                        Log.d("VLog", "Create log file failed, file=" + logFile.getAbsolutePath());
                    }
                    if (fileWriter == null)
                        return;
                    fileWriter.append(TimeUtil.getCurrentDateTime()).append("\t")
                            .append(msg).append("\n");
                    fileWriter.flush();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void delete() {
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                File file = new File(LOG_PATH);
                if (file.exists()) {
                    File[] files = file.listFiles();
                    if (files == null || files.length < SAVE_LOG_DAYS + 1) {
                        return;
                    }

                    for (File value : files) {
                        //删除 SAVE_LOG_DAYS 天之前的日志
                        if (value.lastModified() < System.currentTimeMillis() - SAVE_LOG_DAYS * 24 * 60 * 60 * 1000L) {
                            value.delete();
                        }
                    }
                }
            };
        });
    }

    /*
     * 检查是否需要写入的新的文件
     * */
    private static void checkDate() {
        if (TimeUtil.getCurrentData().equals(currentDate)) {
            return;
        }

        File logPath = new File(LOG_PATH);
        if (!logPath.exists() && !logPath.mkdirs()) {
            VLog.e("Create log dir failed :" + logPath);
            return;
        }

        currentDate = TimeUtil.getCurrentData();
        logFile = new File(LOG_PATH + "App_" + currentDate + ".txt");
        try {
            if (fileWriter != null) {
                fileWriter.close();
            }
            fileWriter = new FileWriter(logFile.getAbsoluteFile(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        delete();
    }
}
