package com.example.common.image;

/**
 * function：
 * author by cpMark
 * create on 2018/5/15.
 */
public class BitmapUtils {


    /**
     *  获取最合适的宽高比
     */
    public static int findBestSampleSize(
            int actualWidth, int actualHeight, int desiredWidth, int desiredHeight) {
        double wr = (double) actualWidth / desiredWidth;
        double hr = (double) actualHeight / desiredHeight;
        double ratio = Math.min(wr, hr);
        float n = 1.0f;
        while ((n * 2) <= ratio) {
            n *= 2;
        }

        return (int) n;
    }
}
