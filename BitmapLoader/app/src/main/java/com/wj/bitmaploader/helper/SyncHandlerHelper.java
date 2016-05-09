package com.wj.bitmaploader.helper;/**
 * Created by wangjiang on 2016/5/4.
 */

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.wj.bitmaploader.listener.DisplayListener;
import com.wj.bitmaploader.loader.DisplayBitmapOptions;
import com.wj.bitmaploader.shape.ChatShape;
import com.wj.bitmaploader.shape.CircleShape;
import com.wj.bitmaploader.shape.DisplayShape;
import com.wj.bitmaploader.utils.BitmapUtil;

import java.io.FileNotFoundException;

/**
 * User: WangJiang(https://github.com/WJRye)
 * Date: 2016-05-04
 * Time: 16:59
 */
public class SyncHandlerHelper extends HandlerHelper<Void, Bitmap> {
    private int KEY = (int) System.currentTimeMillis();
    private boolean error = false;
    private ImageView imageView;
    private DisplayBitmapOptions options;
    private DisplayListener listener;

    public SyncHandlerHelper(ImageView imageView, DisplayBitmapOptions options, DisplayListener listener) {
        super();
        this.imageView = imageView;
        this.options = options;
        this.imageView.setTag(KEY, options);
        this.listener = listener;
    }

    @Override
    protected Bitmap doInThread(Void... params) {
        try {
            switch (options.getType()) {
                case DisplayBitmapOptions.TYPE_DATA:
                    return loadBitmap(BitmapUtil.getDstBitmap(options.getData(), options.getWidth(), options.getHeight()), options.getShape());
                case DisplayBitmapOptions.TYPE_PATH:
                    return loadBitmap(BitmapUtil.getDstBitmap(options.getPath(), options.getWidth(), options.getHeight()), options.getShape());
                case DisplayBitmapOptions.TYPE_INPUT_STREAM:
                    return loadBitmap(BitmapUtil.getDstBitmap(options.getInputStream(), options.getWidth(), options.getHeight()), options.getShape());
            }
        } catch (Exception e) {
            error = true;
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            error = true;
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostResult(Bitmap bitmap) {
        if (error) {
            if (listener != null) listener.onError(imageView);
        } else {
            if (bitmap != null) {
                if (options.equals(imageView.getTag(KEY))) {
                    imageView.setImageBitmap(bitmap);
                }
            } else {
                if (listener != null) listener.onFail(imageView);
            }
        }
    }

    private static Bitmap loadBitmap(Bitmap srcBitmap, DisplayShape shape) throws FileNotFoundException, OutOfMemoryError {
        switch (shape.getShapeType()) {
            case DisplayShape.RECT:
                break;
            case DisplayShape.ROUND_RECT:
                return BitmapUtil.getRoundedBitmap(srcBitmap, shape.getRadius());
            case DisplayShape.CIRCLE:
                CircleShape circleShape = (CircleShape) shape;
                if (circleShape.hasBorder()) {
                    return BitmapUtil.getCircleBitmapWithBorder(srcBitmap, circleShape.getBorderWidth(), circleShape.getBorderColor());
                } else {
                    return BitmapUtil.getCircleBitmap(srcBitmap);
                }
            case DisplayShape.CHAT:
                ChatShape chatShape = (ChatShape) shape;
                if (chatShape.getOrientation() == ChatShape.LEFT) {
                    return BitmapUtil.getChatLeftBitmap(srcBitmap, chatShape.getRadius());
                } else if (chatShape.getOrientation() == ChatShape.RIGHT) {
                    return BitmapUtil.getChatRightBitmap(srcBitmap, chatShape.getRadius());
                }
            default:
                break;
        }
        return srcBitmap;
    }


}
