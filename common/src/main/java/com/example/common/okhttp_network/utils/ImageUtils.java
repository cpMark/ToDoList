package com.example.common.okhttp_network.utils;

import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.reflect.Field;

/**
 * function：图片工具类。
 * 具体功能：
 * （1）获取源图片的宽、高（参数以流的形式）
 * （2）计算源图片和目标图片的宽高比
 * （3）获取目标View的尺寸
 *
 * author by admin
 * create on 2018/5/16.
 */
public class ImageUtils {

    /**
     * 根据InputStream获取源图片实际的宽度和高度
     *
     * @param imageStream   源图片流对象
     * @return               源图片的尺寸对象（含源图片宽高）
     */
    public static ImageSize getImageSize(InputStream imageStream) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //打开仅获取宽高模式
        options.inJustDecodeBounds = true;
        //不加载图片，仅获取宽高（在options参数中）
        BitmapFactory.decodeStream(imageStream, null, options);
        return new ImageSize(options.outWidth, options.outHeight);
    }

    /**
     *  图片尺寸的封装类：宽、高
     */
    public static class ImageSize {
        int width;
        int height;

        public ImageSize() {
        }

        public ImageSize(int width, int height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public String toString() {
            return "ImageSize{" +
                    "width=" + width +
                    ", height=" + height +
                    '}';
        }
    }

    /**
     * 计算最佳宽高比
     *
     * @param srcSize    图片源尺寸对象
     * @param targetSize 图片目标尺寸对象
     * @return 最佳压缩宽高比
     */
    public static int calculateInSampleSize(ImageSize srcSize, ImageSize targetSize) {
        // 源图片的宽度
        int width = srcSize.width;
        int height = srcSize.height;
        int inSampleSize = 1;
        //目标宽高
        int reqWidth = targetSize.width;
        int reqHeight = targetSize.height;

        if (width > reqWidth && height > reqHeight) {
            // 计算出实际宽度和目标宽度的比率
            int widthRatio = Math.round((float) width / (float) reqWidth);
            int heightRatio = Math.round((float) height / (float) reqHeight);
            inSampleSize = Math.max(widthRatio, heightRatio);
        }
        return inSampleSize;
    }

    /**
     * 获取目标View的尺寸
     *
     * @param view 显示图片的目标View
     * @return 返回目标View期待的尺寸对象
     */
    public static ImageSize getImageViewSize(View view) {
        ImageSize imageSize = new ImageSize();
        imageSize.width = getExpectWidth(view);
        imageSize.height = getExpectHeight(view);

        return imageSize;
    }

    /**
     * 根据view获得期望的高度。
     * 逻辑：
     * (1）先获取实际高度
     * （2）实际高度不满足条件，获取xml布局中申明的高度
     * （3）xml布局中声明的高度也不满足，获取xml中声明的最大高度
     * （4）最大高度还是不满足，就获取屏幕高度
     *
     * @param view 显示图片的目标View
     * @return 目标View期望的高度
     */
    private static int getExpectHeight(View view) {

        int height = 0;
        if (view == null) return 0;

        final ViewGroup.LayoutParams params = view.getLayoutParams();
        //如果是WRAP_CONTENT，此时图片还没加载，getWidth根本无效
        if (params != null && params.height != ViewGroup.LayoutParams.WRAP_CONTENT) {
            height = view.getHeight(); // 获得实际的宽度
        }
        if (height <= 0 && params != null) {
            height = params.height; // 获得布局文件中的声明的宽度
        }

        if (height <= 0) {
            height = getImageViewFieldValue(view, "mMaxHeight");// 获得设置的最大的宽度
        }

        //如果宽度还是没有获取到，憋大招，使用屏幕的宽度
        if (height <= 0) {
            DisplayMetrics displayMetrics = view.getContext().getResources()
                    .getDisplayMetrics();
            height = displayMetrics.heightPixels;
        }

        return height;
    }

    /**
     * 根据view获得期望的宽度。
     * 逻辑：
     * （1）先获取实际宽度
     * （2）实际宽度不满足条件，获取xml布局中申明的宽度
     * （3）xml布局中声明的宽度也不满足，获取xml中声明的最大宽度
     * （4）最大宽度还是不满足，就获取屏幕宽度
     *
     * @param view 显示图片的目标View
     * @return 返回期望的宽度
     */
    private static int getExpectWidth(View view) {
        int width = 0;
        if (view == null) return 0;

        final ViewGroup.LayoutParams params = view.getLayoutParams();
        //如果是WRAP_CONTENT，此时图片还没加载，getWidth根本无效
        if (params != null && params.width != ViewGroup.LayoutParams.WRAP_CONTENT) {
            // 获得实际的宽度
            width = view.getWidth();
        }

        if (width <= 0 && params != null) {
            // 获得布局文件中的声明的宽度
            width = params.width;
        }

        if (width <= 0) {
            // 获得设置的最大的宽度
            width = getImageViewFieldValue(view, "mMaxWidth");
        }
        //如果宽度还是没有获取到，憋大招，使用屏幕的宽度
        if (width <= 0) {
            DisplayMetrics displayMetrics = view.getContext().getResources()
                    .getDisplayMetrics();
            width = displayMetrics.widthPixels;
        }

        return width;
    }

    /**
     * 通过反射获取imageview的某个属性值
     *
     * @param object    反射的对象
     * @param fieldName 反射的字段（int类型字段）
     * @return 返回反射的结果
     */
    private static int getImageViewFieldValue(Object object, String fieldName) {
        int value = 0;
        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = field.getInt(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                value = fieldValue;
            }
        } catch (Exception e) {
        }
        return value;

    }
}
