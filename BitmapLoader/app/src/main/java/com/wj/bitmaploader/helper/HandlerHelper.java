package com.wj.bitmaploader.helper;/**
 * Created by wangjiang on 2016/4/27.
 */


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 该类用于帮助在图片的异步加载和显示
 * User: WangJiang(https://github.com/WJRye)
 * Date: 2016-04-27
 * Time: 19:38
 */
public abstract class HandlerHelper<Params, Result> extends Handler {
    private static final int WHAT = 0x123;

    public HandlerHelper() {
        super(Looper.getMainLooper());
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case WHAT:
                Object obj = msg.obj;
                if (obj == null) {
                    onPostResult(null);
                } else {
                    onPostResult((Result) obj);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 启动子线程，然后开始执行
     *
     * @param params 参数
     */
    public final void execute(final Params... params) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                Result result = doInThread(params);
                sendMessage(obtainMessage(WHAT, result));
            }
        });
    }


    /**
     * 在单独的子线程中的操作
     *
     * @param params 参数
     * @return 结果
     */
    protected abstract Result doInThread(Params... params);

    /**
     * 在主线程中的操作
     *
     * @param result 子线程中返回的结果
     */
    protected abstract void onPostResult(Result result);

}
