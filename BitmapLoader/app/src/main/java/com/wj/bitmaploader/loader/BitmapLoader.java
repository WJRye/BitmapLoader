package com.wj.bitmaploader.loader;/**
 * Created by wangjiang on 2016/4/25.
 */

import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wj.bitmaploader.asynctask.HandlerHelper;
import com.wj.bitmaploader.listener.DisplayListener;
import com.wj.bitmaploader.shape.ChatShape;
import com.wj.bitmaploader.shape.DisplayShape;
import com.wj.bitmaploader.utils.BitmapUtil;

import java.io.FileNotFoundException;

/**
 * User: WangJiang(https://github.com/WJRye)
 * Date: 2016-04-25
 * Time: 16:35
 */
public final class BitmapLoader {
    private static final String TAG = "TAG";

    private BitmapLoader() {
    }

    private static class BitmapLoaderHolder {
        private static BitmapLoader INSTANCE = new BitmapLoader();
    }

    public static BitmapLoader getInstance() {
        return BitmapLoaderHolder.INSTANCE;
    }

    private void display(ImageView imageView, Bitmap bitmap, int width, int height) {
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        params.width = width;
        params.height = height;
        imageView.setLayoutParams(params);
        imageView.setImageBitmap(bitmap);
    }

    public void displayBitmap(ImageView imageView, DisplayBitmapOptions options, DisplayListener listener) {
        if (imageView == null || options == null) {
            throw new NullPointerException("ImageView or DisplayBitmapOptions is null!");
        }
        int width = options.getWidth();
        int height = options.getHeight();
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width or Height is illegal!");
        }

        new AsyncHandlerHelper(imageView, options, listener).execute();
    }

    private class AsyncHandlerHelper extends HandlerHelper<Void, Bitmap> {

        private boolean error = false;

        private ImageView imageView;
        private DisplayBitmapOptions options;
        private DisplayListener listener;

        public AsyncHandlerHelper(ImageView imageView, DisplayBitmapOptions options, DisplayListener listener) {
            super();
            this.imageView = imageView;
            this.options = options;
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
                    display(imageView, bitmap, options.getWidth(), options.getHeight());
                } else {
                    if (listener != null) listener.onNull(imageView);
                }
            }
        }

        private Bitmap loadBitmap(Bitmap srcBitmap, DisplayShape shape) throws FileNotFoundException, OutOfMemoryError {
            switch (shape.getShape()) {
                case DisplayShape.RECT:
                    break;
                case DisplayShape.ROUND_RECT:
                    return BitmapUtil.getRoundedBitmap(srcBitmap, shape.getRadius());
                case DisplayShape.CIRCLE:
                    return BitmapUtil.getCircleBitmap(srcBitmap);
                case DisplayShape.CHAT:
                    ChatShape chatShape = (ChatShape) shape;
                    if (chatShape.getOrientation() == ChatShape.LEFT) {
                        return BitmapUtil.getChatLeftBitmap(srcBitmap, chatShape.getRadius());
                    } else if (chatShape.getOrientation() == ChatShape.RIGHT) {
                        return BitmapUtil.getChatRightBitmap(srcBitmap, chatShape.getRadius());
                    }
                    break;
                default:
                    break;
            }
            return srcBitmap;
        }
    }
}
