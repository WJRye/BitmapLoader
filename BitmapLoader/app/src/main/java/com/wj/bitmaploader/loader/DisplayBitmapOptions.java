package com.wj.bitmaploader.loader;/**
 * Created by wangjiang on 2016/4/27.
 */

import com.wj.bitmaploader.shape.DisplayShape;
import com.wj.bitmaploader.shape.RectShape;

import java.io.InputStream;

/**
 * User: WangJiang(https://github.com/WJRye)
 * Date: 2016-04-27
 * Time: 10:44
 */
public final class DisplayBitmapOptions {
    public static final int TYPE_DATA = 1;
    public static final int TYPE_PATH = 2;
    public static final int TYPE_INPUT_STREAM = 3;

    private int width;
    private int height;
    private int type;
    private byte[] data;
    private int loadingImage;
    private String uniqueID;
    private String path;
    private InputStream inputStream;
    private DisplayShape shape;


    private DisplayBitmapOptions(Builder builder) {
        width = builder.width;
        height = builder.height;
        type = builder.type;
        if (builder.shape == null) {
            shape = new RectShape();
        } else {
            shape = builder.shape;
        }
        switch (type) {
            case TYPE_DATA:
                data = builder.data;
                break;
            case TYPE_PATH:
                path = builder.path;
                break;
            case TYPE_INPUT_STREAM:
                inputStream = builder.inputStream;
                break;
            default:
                break;
        }
        uniqueID = builder.uniqueID;
        loadingImage = builder.loadingImage;
    }

    public static class Builder {
        private int width;
        private int height;
        private int type;
        private byte[] data;
        private String path;
        private InputStream inputStream;
        private DisplayShape shape;
        private String uniqueID;
        private int loadingImage;

        public Builder() {

        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder data(byte[] data) {
            this.type = TYPE_DATA;
            this.data = data;
            setUniqueID(String.valueOf(data.hashCode()));
            return this;
        }

        public Builder path(String path) {
            this.type = TYPE_PATH;
            this.path = path;
            setUniqueID(path);
            return this;
        }

        public Builder inputStream(InputStream inputStream) {
            this.type = TYPE_INPUT_STREAM;
            this.inputStream = inputStream;
            setUniqueID(String.valueOf(inputStream.hashCode()));
            return this;
        }

        private void setUniqueID(String uniqueID) {
            this.uniqueID = uniqueID;
        }

        public Builder shape(DisplayShape shape) {
            this.shape = shape;
            return this;
        }

        public void setImageOnError(int drawableId) {

        }

        public void setImageOnFail(int drawableId) {

        }

        public Builder setImageOnLoading(int drawableId) {
            this.loadingImage = drawableId;
            return this;
        }

        public DisplayBitmapOptions build() {
            return new DisplayBitmapOptions(this);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getType() {
        return type;
    }

    public byte[] getData() {
        return data;
    }

    public String getPath() {
        return path;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public DisplayShape getShape() {
        return shape;
    }

    public int getImageOnLoading() {
        return loadingImage;
    }

    public String getUniqueID() {
        return uniqueID;
    }
}
