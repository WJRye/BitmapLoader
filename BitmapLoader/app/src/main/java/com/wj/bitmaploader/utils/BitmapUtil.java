package com.wj.bitmaploader.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


/**
 * 处理Bitmap的工具类
 */
public final class BitmapUtil {

    private static final String TAG = "TAG";

    private BitmapUtil() {
    }

    /**
     * 获得聊天右边显示的气泡图片
     *
     * @param imagePath 图片路径
     * @param dstWidth  目标宽度
     * @param dstHeight 目标高度
     * @param radius    圆角半径
     * @return 气泡圆角图片
     */
    public static Bitmap getChatRightBitmap(String imagePath, int dstWidth, int dstHeight, int radius) throws OutOfMemoryError, FileNotFoundException {

        Bitmap srcBitmap = getDstBitmap(imagePath, dstWidth, dstHeight);
        if (srcBitmap == null) {
            return null;
        }

        return getChatRightBitmap(srcBitmap, radius);
    }

    public static Bitmap getChatRightBitmap(Bitmap srcBitmap, int radius) throws OutOfMemoryError {

        int width = srcBitmap.getWidth();
        int height = srcBitmap.getHeight();

        Path path = new Path();
        float xStart = width - 20f;
        float yStart = 30f;
        float yEnd = yStart + 30f;
        float yCenter = (yStart + yEnd) / 2;
        float d = 2f;
        path.moveTo(xStart, yStart);
        path.lineTo(xStart, yStart);
        path.lineTo(width - d, yCenter - d);
        path.quadTo(width - d, yCenter - d, width - d, yCenter + d);
        path.lineTo(xStart, yEnd);

        RectF rectF = new RectF(0, 0, xStart, height);

        return drawChatBitmap(srcBitmap, path, rectF, radius);
    }

    /**
     * 获得聊天左边显示的气泡图片
     *
     * @param imagePath 图片路径
     * @param dstWidth  目标宽度
     * @param dstHeight 目标高度
     * @param radius    圆角半径
     * @return 气泡圆角图片
     */
    public static Bitmap getChatLeftBitmap(String imagePath, int dstWidth, int dstHeight, int radius) throws OutOfMemoryError, FileNotFoundException {
        Bitmap srcBitmap = getDstBitmap(imagePath, dstWidth, dstHeight);
        if (srcBitmap == null) {
            return null;
        }
        return getChatLeftBitmap(srcBitmap, radius);

    }

    public static Bitmap getChatLeftBitmap(Bitmap srcBitmap, int radius) throws OutOfMemoryError {
        Path path = new Path();
        float xStart = 20f;
        float yStart = 30f;
        float yEnd = yStart + 30f;
        float yCenter = (yStart + yEnd) / 2;
        float d = 2f;
        path.moveTo(xStart, yStart);
        path.lineTo(xStart, yStart);
        path.lineTo(d, yCenter - d);
        path.quadTo(d, yCenter - d, d, yCenter + d);
        path.lineTo(xStart, yEnd);

        RectF rectF = new RectF(xStart, 0, srcBitmap.getWidth(), srcBitmap.getHeight());

        return drawChatBitmap(srcBitmap, path, rectF, radius);

    }

    private static Bitmap drawChatBitmap(Bitmap srcBitmap, Path path, RectF rectF, int radius) throws OutOfMemoryError {
        int width = srcBitmap.getWidth();
        int height = srcBitmap.getHeight();

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        Bitmap desBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(desBitmap);
        //抗锯齿
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        canvas.drawARGB(0, 0, 0, 0);

        //画三角形
        canvas.drawPath(path, paint);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        //绘制交集的上层
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Rect rect = new Rect(0, 0, width, height);
        canvas.drawBitmap(srcBitmap, rect, rect, paint);

        return desBitmap;
    }


    private static Bitmap drawRoundedBitmap(Bitmap srcBitmap, int width, int height, int r) throws OutOfMemoryError {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        Rect rect = new Rect(0, 0, width, height);
        RectF rectF = new RectF(rect);
        Bitmap desBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);

        Canvas canvas = new Canvas(desBitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(srcBitmap, rect, rect, paint);

        return desBitmap;
    }

    /**
     * 获得圆角图片
     *
     * @param imagePath 图片地址
     * @param dstWidth  目标宽度
     * @param dstHeight 目标高度
     * @param r         圆角半径
     * @return 圆角bitmap
     */
    public static Bitmap getRoundedBitmap(String imagePath, int dstWidth, int dstHeight, int r) throws OutOfMemoryError, FileNotFoundException {
        return getRoundedBitmap(getDstBitmap(imagePath, dstWidth, dstHeight), r);
    }

    /**
     * 获得圆角图片
     *
     * @param srcBitmap 处理之前的bitmap
     * @param r         圆角半径
     * @return 圆角bitmap
     */
    public static Bitmap getRoundedBitmap(Bitmap srcBitmap, int r) throws OutOfMemoryError {
        if (srcBitmap == null) {
            return null;
        }

        return drawRoundedBitmap(srcBitmap, srcBitmap.getWidth(), srcBitmap.getHeight(), r);

    }

    /**
     * 获得圆形图片
     *
     * @param imagePath 图片地址
     * @param dstWidth  目标宽度
     * @param dstHeight 目标高度
     * @return 圆形bitmap
     */
    public static Bitmap getCircleBitmap(String imagePath, int dstWidth, int dstHeight) throws OutOfMemoryError, FileNotFoundException {
        int size = Math.min(dstWidth, dstHeight);
        return getCircleBitmap(getDstBitmap(imagePath, size, size));
    }

    /**
     * 获得圆形图片
     *
     * @param srcBitmap 处理之前的bitmap
     * @return 圆形bitmap
     */
    public static Bitmap getCircleBitmap(Bitmap srcBitmap) throws OutOfMemoryError {
        if (srcBitmap == null) {
            return null;
        }
        int size = Math.min(srcBitmap.getWidth(), srcBitmap.getHeight());
        return drawRoundedBitmap(Bitmap.createScaledBitmap(srcBitmap, size, size, true), size, size, size / 2);
    }

    public static Bitmap getCircleBitmapWithBorder(Bitmap srcBitmap, int borderWidth, int borderColor) throws OutOfMemoryError {

        int size = Math.max(srcBitmap.getWidth(), srcBitmap.getHeight());
        Bitmap desBitmap = drawRoundedBitmap(Bitmap.createScaledBitmap(srcBitmap, size, size, true), size, size, size / 2);
        Bitmap drawBitmap = desBitmap.copy(Bitmap.Config.ARGB_4444, true);

        Canvas canvas = new Canvas(desBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
        //抗锯齿
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));

        Paint bmPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        bmPaint.setStyle(Paint.Style.FILL);
        bmPaint.setColor(Color.WHITE);
        RectF oval = new RectF(borderWidth, borderWidth, size - borderWidth, size - borderWidth);
        canvas.drawBitmap(drawBitmap, null, oval, bmPaint);

        Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(borderWidth);
        circlePaint.setColor(borderColor);
        float circleRadius = size / 2 - borderWidth / 2;
        canvas.drawCircle(size / 2, size / 2, circleRadius, circlePaint);

        drawBitmap.recycle();
        drawBitmap = null;

        return desBitmap;
    }

    /**
     * 压缩图片
     *
     * @param imagePath 图片路径
     * @param dstWidth  目标宽度
     * @param dstHeight 目标高度
     * @return 压缩后的bitmap
     */
    public static Bitmap getDstBitmap(String imagePath, int dstWidth, int dstHeight) throws FileNotFoundException, OutOfMemoryError {
        if ((imagePath == null || imagePath.length() == 0) || dstWidth == 0 || dstHeight == 0)
            throw new IllegalArgumentException();

        InputStream optInputStream = new BufferedInputStream(new FileInputStream(imagePath));
        InputStream srcInputStream = new BufferedInputStream(new FileInputStream(imagePath));
        int degree = readPictureDegree(imagePath);
        return compress(optInputStream, srcInputStream, dstWidth, dstHeight, degree);
    }

    /**
     * 压缩图片
     *
     * @param data      图片data数据
     * @param dstWidth  目标宽度
     * @param dstHeight 目标高度
     * @return 压缩后的bitmap
     */
    public static Bitmap getDstBitmap(byte[] data, int dstWidth, int dstHeight) throws OutOfMemoryError {
        if ((data == null || data.length == 0) || dstWidth == 0 || dstHeight == 0)
            throw new IllegalArgumentException();

        InputStream optInputStream = new ByteArrayInputStream(data);
        InputStream srcInputStream = new ByteArrayInputStream(data);
        return compress(optInputStream, srcInputStream, dstWidth, dstHeight, 0);
    }

    /**
     * 压缩图片
     *
     * @param inputStream 图片输入流
     * @param dstWidth    目标宽度
     * @param dstHeight   目标高度
     * @return 压缩后的bitmap
     */
    public static Bitmap getDstBitmap(InputStream inputStream, int dstWidth, int dstHeight) throws IOException, OutOfMemoryError {
        if (inputStream == null || dstWidth == 0 || dstHeight == 0)
            throw new IllegalArgumentException();

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        return getDstBitmap(outStream.toByteArray(), dstWidth, dstHeight);
    }


    /**
     * 压缩图片
     *
     * @param srcInputStream 输入流
     * @param dstWidth       目标宽度
     * @param dstHeight      目标高度
     * @param degree         图片旋转的角度
     * @return 压缩后的bitmap
     */
    private static Bitmap compress(InputStream optInputStream, InputStream srcInputStream, int dstWidth, int dstHeight, int degree) throws OutOfMemoryError {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(optInputStream, null, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        if (width <= dstWidth && height <= dstHeight)
            return BitmapFactory.decodeStream(srcInputStream);

        int inSampleSize = 1;

        if (degree == 0 || degree == 180) {
            if (width > height && width > dstWidth) {
                inSampleSize = Math.round((float) width / (float) dstWidth);
            } else if (height > width && height > dstHeight) {
                inSampleSize = Math.round((float) height / (float) dstHeight);
            }
        } else if (degree == 90 || degree == 270) {
            // 图片有旋转时，宽和高调换了
            if (width > height && width > dstHeight) {
                inSampleSize = Math.round((float) width / (float) dstHeight);
            } else if (height > width && height > dstWidth) {
                inSampleSize = Math.round((float) height / (float) dstWidth);
            }
        }

        if (inSampleSize <= 1) inSampleSize = 1;
        opts.inSampleSize = inSampleSize;
        opts.inPreferredConfig = Config.RGB_565;
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        opts.inTargetDensity = Resources.getSystem().getDisplayMetrics().densityDpi;
        opts.inScaled = true;
        opts.inTempStorage = new byte[16 * 1024];
        opts.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeStream(srcInputStream, null, opts);
        if (bitmap != null && (degree == 90 || degree == 270)) {
            // 处理旋转了一定角度的图片，比如有些机型拍出的照片默认旋转了90度的
            Matrix matrix = new Matrix();
            matrix.postRotate(degree);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        }
        return bitmap;
    }

    /**
     * @param path 图片路径
     * @return 图片旋转的度数
     */
    private static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    public static Bitmap drawable2Bitmap(Drawable drawable, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_4444 : Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }


}
