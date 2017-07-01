package com.as.commontool.util.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 2 on 2016/10/17.
 */
public class BitmapManager {
    private  static BitmapManager mInstance;
    private Context context;

    private BitmapManager(Context context) {
        this.context = context;
    }

    public  static BitmapManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new BitmapManager(context.getApplicationContext());
        }
        return mInstance;
    }
    /**
     * 设置图片的缩放倍数 获取图片 分配内存
     * @param options Option 参数类
     * @param width
     * @param height
     * @return
     */
    private  BitmapFactory.Options initBitmapFactoryOption(BitmapFactory.Options options, int width, int height){
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int inSampleSize = 1;
        if (srcHeight > height || srcWidth > width) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / height);
            } else {
                inSampleSize = Math.round(srcWidth / width);
            }
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        return options;
    }
    /**
     * 获取缩放后的本地图片
     * 若实际宽高比和需求的宽高比不一致
     * @param filePath 文件路径
     * @param width    宽
     * @param height   高
     * @return
     */
    public  Bitmap readBitmapFromFile(String filePath, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options = initBitmapFactoryOption(options,width,height);

        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 获取缩放后的本地图片
     * 这种方法效率较高
     * @param filePath 文件路径
     * @param width    宽
     * @param height   高
     * @return
     */
    public  Bitmap readBitmapFromFileDescriptor(String filePath, int width, int height) {

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return readBitmapFromInputStream(fis,width,height);
    }

    /**
     * 获取缩放后的本地图片
     *
     * @param ins    输入流
     * @param width  宽
     * @param height 高
     * @return
     */
    public  Bitmap readBitmapFromInputStream(InputStream ins, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(ins, null, options);
        options = initBitmapFactoryOption(options,width,height);

        return BitmapFactory.decodeStream(ins, null, options);
    }

    /**
     * 相当的耗费内存 建议采用decodeStream代替decodeResource
     * @param resources
     * @param resourcesId
     * @param width
     * @param height
     * @return
     */
    public  Bitmap readBitmapFromResource(Resources resources, int resourcesId, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resourcesId, options);
        options = initBitmapFactoryOption(options,width,height);

        return BitmapFactory.decodeResource(resources, resourcesId, options);
    }

    /**
     * 如果对性能要求较高，则应该使用 decodeStream；如果对性能要求不高，且需要 Android 自带的图片自适应缩放功能，则可以使用 decodeResource。
     * @param resources
     * @param resourcesId
     * @param width
     * @param height
     * @return
     */
    public  Bitmap readBitmapFromStream(Resources resources, int resourcesId, int width, int height) {
        InputStream ins = resources.openRawResource(resourcesId);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(ins, null, options);
        options = initBitmapFactoryOption(options,width,height);

        return BitmapFactory.decodeStream(ins, null, options);
    }

    /**
     * 从二进制数据读取图片
     * @param data 二进制数据
     * @param width
     * @param height
     * @return
     */
    public  Bitmap readBitmapFromByteArray(byte[] data, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);
        options = initBitmapFactoryOption(options,width,height);

        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }

    /**
     * 获取缩放后的本地图片
     *
     * @param filePath 文件路径
     * @return
     */
    public  Bitmap readBitmapFromAssetsFile( String filePath) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        InputStream is = null;
        try {
            is = am.open(filePath);
            image = BitmapFactory.decodeStream(is);

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return image;
    }

    public  void writeBitmapToFile(String filePath, Bitmap b, int quality) {
        File desFile=null;
        FileOutputStream fos=null;
        BufferedOutputStream bos = null;
        try {
            desFile = new File(filePath);
            fos = new FileOutputStream(desFile);
            bos = new BufferedOutputStream(fos);
            b.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (fos!=null){
                try {
                    fos.close();
                    fos=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (bos!=null){
                try {
                    bos.close();
                    bos=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private  Bitmap compressImage(Bitmap image) {
        if (image == null) {
            return null;
        }
        ByteArrayOutputStream baos = null;
        ByteArrayInputStream isBm=null;
        try {
            baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] bytes = baos.toByteArray();
            isBm = new ByteArrayInputStream(bytes);
            Bitmap bitmap = BitmapFactory.decodeStream(isBm);
            return bitmap;
        } catch (OutOfMemoryError e) {
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
            }

            if (isBm!=null){
                try {
                    isBm.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 根据scale生成一张图片
     *
     * @param bitmap
     * @param scale  等比缩放值
     * @return
     */
    public  Bitmap bitmapScale(Bitmap bitmap, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale); // 长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    /**
     * 读取照片exif信息中的旋转角度
     *
     * @param path 照片路径
     * @return角度
     */
    private  int readPictureDegree(String path) {
        if (TextUtils.isEmpty(path)) {
            return 0;
        }
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
        } catch (Exception e) {
        }
        return degree;
    }

    private  Bitmap rotateBitmap(Bitmap b, float rotateDegree) {
        if (b == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(rotateDegree);
        Bitmap rotaBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
        return rotaBitmap;
    }

    /**
     * 图片二进制流转换
     * @param bm
     * @return
     */
    public byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public  Drawable bitmapToDrawable(Resources resources, Bitmap bm) {
        Drawable drawable = new BitmapDrawable(resources, bm);
        return drawable;
    }

    public  Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
