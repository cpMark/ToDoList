package com.example.common.okhttp_network.utils;

import android.os.Handler;

import java.util.concurrent.Executor;

/**
 *  结果回调的辅助类
 */
public class ExecutorDelivery implements Executor {

    private final Executor mResponsePoster;

    public ExecutorDelivery(final Handler handler) {
        // Make an Executor that just wraps the handler.
        mResponsePoster =
                new Executor() {
                    @Override
                    public void execute(Runnable command) {
                        handler.post(command);
                    }
                };
    }

    @Override
    public void execute(Runnable runnable) {
        mResponsePoster.execute(runnable);
    }
}