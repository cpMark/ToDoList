package learn.cp.todolist;

import android.app.Application;

import com.example.common.application.ContextHolder;
import com.example.common.local_storage.SpUtils;
import com.example.common.utils.Utils;

/**
 * function：
 * author by cpMark
 * create on 2018/12/21.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ContextHolder.init(getApplicationContext());
        SpUtils.init(getApplicationContext());
        Utils.init(getApplicationContext());
    }
}
