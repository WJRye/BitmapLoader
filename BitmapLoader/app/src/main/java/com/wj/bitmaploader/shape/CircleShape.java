package com.wj.bitmaploader.shape;/**
 * Created by wangjiang on 2016/4/29.
 */

/**
 * User: WangJiang(https://github.com/WJRye)
 * Date: 2016-04-29
 * Time: 17:56
 */
public class CircleShape extends DisplayShape {
    private boolean hasBorder;
    private int borderWidth;
    private int borderColor;

    public CircleShape() {
        this(false, 0, 0);
    }

    public CircleShape(int borderWidth, int borderColor) {
        this(true, borderWidth, borderColor);
    }

    private CircleShape(boolean hasBorder, int borderWidth, int borderColor) {
        super(CIRCLE, 0);
        this.hasBorder = hasBorder;

        if (hasBorder) {
            if (borderWidth < 0) {
                throw new IllegalArgumentException("borderWidth is <=0 !");
            }
            this.borderWidth = borderWidth;
            this.borderColor = borderColor;
        }
    }

    public boolean hasBorder() {
        return hasBorder;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public int getBorderColor() {
        return borderColor;
    }
}
