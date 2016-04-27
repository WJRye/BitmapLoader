package com.wj.bitmaploader.listener;/**
 * Created by wangjiang on 2016/4/27.
 */

import android.widget.ImageView;

/**
 * User: WangJiang(https://github.com/WJRye)
 * Date: 2016-04-27
 * Time: 16:43
 */
public interface DisplayListener {

    void onError(String msg);

    void onNull(ImageView iv);

}
