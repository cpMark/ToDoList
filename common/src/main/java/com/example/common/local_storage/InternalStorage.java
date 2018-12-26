package com.example.common.local_storage;

import com.example.common.R;
import com.example.common.application.ContextHolder;
import com.example.common.utils.ToastUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 以文件的形式保存数据
 */
public class InternalStorage {

    private static volatile InternalStorage sInstance;

    /**
     * 默认的存储文件名称
     */
    private static final String DB_JSON_DATA = "db_json_data";

    private InternalStorage() {
    }

    public static InternalStorage getInstance() {
        if (sInstance == null) {
            synchronized (InternalStorage.class) {
                if (sInstance == null) {
                    sInstance = new InternalStorage();
                }
            }
        }

        return sInstance;
    }

    /**
     * 保存内容到内部存储器中
     *
     * @param filename 文件名
     * @param content  内容
     */
    public boolean save(String filename, String content) {
        File file = new File(ContextHolder.getContext().getFilesDir(), filename);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean save(String content) {
        return save(DB_JSON_DATA, content);
    }

    /**
     * 通过文件名获取内容
     *
     * @param filename 文件名
     * @return 文件内容
     */
    public String get(String filename) {
        FileInputStream fis = null;
        try {
            fis = ContextHolder.getContext().openFileInput(filename);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int len = -1;
            while ((len = fis.read(data)) != -1) {
                baos.write(data, 0, len);
            }
            return new String(baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String get() {
        return get(DB_JSON_DATA);
    }

    /**
     * 以追加的方式在文件的末尾添加内容
     *
     * @param filename 文件名
     * @param content  追加的内容
     */
    public boolean append(String filename, String content) {
        FileOutputStream fos = null;
        try {
            fos = ContextHolder.getContext().openFileOutput(filename,
                    ContextHolder.getContext().MODE_APPEND);
            fos.write(content.getBytes());
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.show(ContextHolder.getContext().getString(R.string.append_file_occur_error));
            return false;
        }
    }

    public boolean append(String content) {
        return append(DB_JSON_DATA, content);
    }

    /**
     * 删除文件
     *
     * @param filename 文件名
     * @return 是否成功
     */
    public boolean delete(String filename) {
        return ContextHolder.getContext().deleteFile(filename);
    }

    public boolean delete() {
        return delete(DB_JSON_DATA);
    }

    /**
     * 获取内部存储路径下的所有文件名
     *
     * @return 文件名数组
     */
    public String[] queryAllFile() {
        return ContextHolder.getContext().fileList();
    }

}
