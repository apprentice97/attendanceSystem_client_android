package com.example.attendancesystem_client_android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Environment;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Picture {

    public static Bitmap compressBitmap(Bitmap bitmap){
        return Bitmap.createScaledBitmap(bitmap, 200, 300, true);
    }


    /**
     * 质量压缩
     *
     * @param format  图片格式 jpeg,png,webp
     * @param quality 图片的质量,0-100,数值越小质量越差
     */
    public static void compressQuality(Bitmap.CompressFormat format, int quality) {
        File sdFile = Environment.getExternalStorageDirectory();
        File originFile = new File(sdFile, "originImg.jpg");
        Bitmap originBitmap = BitmapFactory.decodeFile(originFile.getAbsolutePath());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        originBitmap.compress(format, quality, bos);
        try {
            FileOutputStream fos = new FileOutputStream(new File(sdFile, "resultImg.jpg"));
            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 采样率压缩
     *
     * @param inSampleSize  可以根据需求计算出合理的inSampleSize
     */
    public static void compressInSampleSize(int inSampleSize) {
        File sdFile = Environment.getExternalStorageDirectory();
        File originFile = new File(sdFile, "originImg.jpg");
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置此参数是仅仅读取图片的宽高到options中，不会将整张图片读到内存中，防止oom
        options.inJustDecodeBounds = true;
        Bitmap emptyBitmap = BitmapFactory.decodeFile(originFile.getAbsolutePath(), options);

        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        Bitmap resultBitmap = BitmapFactory.decodeFile(originFile.getAbsolutePath(), options);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        try {
            FileOutputStream fos = new FileOutputStream(new File(sdFile, "resultImg.jpg"));
            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 缩放压缩
     */
    public static void compressScale(URL url, String targetPath){
//        File sdFile = Environment.getExternalStorageDirectory();
//        File originFile = new File(sdFile, "originImg.jpg");
//        Bitmap bitmap = BitmapFactory.decodeFile(originFile.getAbsolutePath());
//        File originFile = new File(originalPath);
//        Bitmap bitmap = BitmapFactory.decodeFile(originalPath);
//        System.out.println(originalPath);
//        System.out.println(originFile.getName());

        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //设置缩放比
        int radio = 1;
        Bitmap result = Bitmap.createBitmap(bitmap.getWidth() / radio, bitmap.getHeight() / radio, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        RectF rectF = new RectF(0, 0, bitmap.getWidth() / radio, bitmap.getHeight() / radio);
        //将原图画在缩放之后的矩形上
        canvas.drawBitmap(bitmap, null, rectF, null);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        result.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        try {
            FileOutputStream fos = new FileOutputStream(new File(targetPath));
            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
