package com.wj.bitmaploader;/**
 * Created by wangjiang on 2016/4/27.
 */

import android.app.Application;

/**
 * User: WangJiang(https://github.com/WJRye)
 * Date: 2016-04-27
 * Time: 19:24
 */
public class BitmapLoaderApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
//        LeakCanary.install(this);
    }
}
