package com.wbxm.lib.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.text.DecimalFormat;

public class StringUtil {

    /**
     * 判断字符串是否为空或null
     *
     * @param input
     * @return
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input)
                || "null".equals(input.toLowerCase()) || "[]".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 计算 两位数相减或者相乘 精度缺失问题
     *
     * @param float1
     * @param float2
     * @param b      true:乘法 false:减法
     * @return
     */
    public static float precisionHiatus(Float float1, Float float2, boolean b) {
        if (b) {
            return ((float1 * 1000) * (float2 * 1000)) / 1000000;
        } else {
            return ((float1 * 1000) - (float2 * 1000)) / 1000;
        }
    }

    public static float valueOfFloat(String str) {
        if (isEmpty(str)) {
            return 0;
        }
        try {
            return Float.valueOf(str);
        } catch (Exception e) {
            return 0;
        }
    }

    public static long valueOfLong(String str) {
        if (isEmpty(str)) {
            return 0;
        }
        try {
            return Long.valueOf(str);
        } catch (Exception e) {
            return 0;
        }
    }

    public static double valueOfDouble(String str) {
        if (isEmpty(str)) {
            return 0;
        }
        try {
            return Double.valueOf(str);
        } catch (Exception e) {
            return 0;
        }
    }

    public static int valueOfInt(String str) {
        if (isEmpty(str)) {
            return 0;
        }
        try {
            return Integer.valueOf(str);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取文件存储路径
     */
    public static String getFilePath(Context context, String folderName) {
        String folderPath;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            folderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + folderName;
        } else {
            folderPath = context.getFilesDir().getAbsolutePath() + File.separator + folderName;
        }
        File file = new File(folderPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return folderPath;
    }

    /**
     * 获取保存到相册路径
     *
     * @param context
     * @return
     */
    public static String getGalleryPath(Context context) {
        String galleryPath;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            galleryPath = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DCIM + File.separator + "Camera" + File.separator;
        } else {
            galleryPath = context.getFilesDir().getAbsolutePath() + File.separator + Environment.DIRECTORY_DCIM + File.separator + "Camera" + File.separator;
        }
        File file = new File(galleryPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return galleryPath;
    }

}
