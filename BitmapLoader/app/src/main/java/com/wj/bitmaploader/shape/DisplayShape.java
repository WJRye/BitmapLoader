package com.wj.bitmaploader.shape;/**
 * Created by wangjiang on 2016/4/29.
 */

/**
 * 该类用于标记图片显示的形状
 * User: WangJiang(https://github.com/WJRye)
 * Date: 2016-04-29
 * Time: 16:41
 */
public abstract class DisplayShape {

    /**
     * 矩形
     */
    public static final int RECT = 1;
    /**
     * 圆角矩形
     */
    public static final int ROUND_RECT = 2;
    /**
     * 圆形
     */
    public static final int CIRCLE = 3;
    /**
     * 聊天气泡图片
     */
    public static final int CHAT = 4;
    /**
     * 图片形状
     */
    private int shape;
    /**
     * 圆角半径
     */
    private int radius;

    public DisplayShape(int shape, int radius) {
        this.shape = shape;
        this.radius = radius;
    }

    public int getShapeType() {
        return shape;
    }

    public int getRadius() {
        return radius;
    }
}
