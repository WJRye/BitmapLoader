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
    private String path;
    private InputStream inputStream;
    private DisplayShape shape;

    private DisplayBitmapOptions(Builder builder) {
        this.width = builder.width;
        this.height = builder.height;
        this.type = builder.type;
        if (builder.shape == null) {
            this.shape = new RectShape();
        } else {
            this.shape = builder.shape;
        }
        switch (type) {
            case TYPE_DATA:
                this.data = builder.data;
                break;
            case TYPE_PATH:
                this.path = builder.path;
                break;
            case TYPE_INPUT_STREAM:
                this.inputStream = builder.inputStream;
                break;
            default:
                break;
        }
    }

    public static class Builder {
        private int width;
        private int height;
        private int type;
        private byte[] data;
        private String path;
        private InputStream inputStream;
        private DisplayShape shape;

        public Builder(int type) {
            this.type = type;
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
            this.data = data;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder inputStream(InputStream inputStream) {
            this.inputStream = inputStream;
            return this;
        }

        public Builder shape(DisplayShape shape) {
            this.shape = shape;
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

}
