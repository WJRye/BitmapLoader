package com.wj.bitmaploader.util;

import android.graphics.Bitmap;

import com.wj.bitmaploader.shape.ChatShape;
import com.wj.bitmaploader.shape.CircleShape;
import com.wj.bitmaploader.shape.DisplayShape;

/**
 * Author：王江 on 2016/8/19 15:20
 * Description:
 */
public final class DisplayShapeUtil {

    private DisplayShapeUtil() {
    }


    public static Bitmap getBitmapByShape(Bitmap srcBitmap, DisplayShape shape) throws OutOfMemoryError {
        switch (shape.getShapeType()) {
            case DisplayShape.RECT:
                return srcBitmap;
            case DisplayShape.ROUND_RECT:
                return BitmapUtil.getRoundedBitmap(srcBitmap, shape.getRadius());
            case DisplayShape.CIRCLE:
                CircleShape circleShape = (CircleShape) shape;
                if (circleShape.hasBorder()) {
                    srcBitmap = BitmapUtil.getCircleBitmapWithBorder(srcBitmap, circleShape.getBorderWidth(), circleShape.getBorderColor());
                } else {
                    srcBitmap = BitmapUtil.getCircleBitmap(srcBitmap);
                }
                break;
            case DisplayShape.CHAT:
                ChatShape chatShape = (ChatShape) shape;
                if (chatShape.getOrientation() == ChatShape.LEFT) {
                    srcBitmap = BitmapUtil.getChatLeftBitmap(srcBitmap, chatShape.getRadius());
                } else if (chatShape.getOrientation() == ChatShape.RIGHT) {
                    srcBitmap = BitmapUtil.getChatRightBitmap(srcBitmap, chatShape.getRadius());
                }
                break;
            default:
                break;
        }
        return srcBitmap;
    }
}
