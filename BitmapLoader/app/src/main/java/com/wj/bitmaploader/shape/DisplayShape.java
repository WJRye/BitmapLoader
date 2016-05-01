package com.wj.bitmaploader.shape;/**
 * Created by wangjiang on 2016/4/29.
 */

/**
 * User: WangJiang(https://github.com/WJRye)
 * Date: 2016-04-29
 * Time: 16:41
 */
public abstract class DisplayShape {

    public static final int RECT = 1;
    public static final int ROUND_RECT = 2;
    public static final int CIRCLE = 3;
    public static final int CHAT = 4;
    private int shape;
    private int radius;

    public DisplayShape(int shape, int radius) {
        this.shape = shape;
        this.radius = radius;
    }

    public int getShape() {
        return shape;
    }

    public int getRadius() {
        return radius;
    }
}
