package com.wj.bitmaploader.shape;/**
 * Created by wangjiang on 2016/4/29.
 */

/**
 * User: WangJiang(https://github.com/WJRye)
 * Date: 2016-04-29
 * Time: 17:57
 */
public class ChatShape extends DisplayShape {

    private int orientation;

    public ChatShape(int orientation, int radius) {
        super(orientation, radius);
        this.orientation = orientation;
    }

    public int getOrientation() {
        return orientation;
    }
}
