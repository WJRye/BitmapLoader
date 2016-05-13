package com.wj.bitmaploader.shape;/**
 * Created by wangjiang on 2016/4/29.
 */

/**
 * 该类用于标记图片时聊天气泡图片
 * User: WangJiang(https://github.com/WJRye)
 * Date: 2016-04-29
 * Time: 17:57
 */
public class ChatShape extends DisplayShape {
    /**
     * 气泡在左边
     */
    public static final int LEFT = 1;
    /**
     * 气泡在右边
     */
    public static final int RIGHT = 2;
    /**
     * 气泡方向
     */
    private int orientation;

    public ChatShape(int orientation, int radius) {
        super(CHAT, radius);
        this.orientation = orientation;
    }

    public int getOrientation() {
        return orientation;
    }
}
