package com.wj.bitmaploader.listener;/**
 * Created by wangjiang on 2016/4/27.
 */

import android.widget.ImageView;

/**
 * 该接口用于监听显示图片时是否发生异常
 * User: WangJiang(https://github.com/WJRye)
 * Date: 2016-04-27
 * Time: 16:43
 */
public interface DisplayListener {

    /**
     * 加载图片发生错误
     *
     * @param iv 当前ImageView
     */
    void onError(ImageView iv);

    /**
     * 加载图片失败
     *
     * @param iv 当前ImageView
     */
    void onFail(ImageView iv);

}
