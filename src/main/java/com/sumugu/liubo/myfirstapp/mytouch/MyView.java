package com.sumugu.liubo.myfirstapp.mytouch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liubo on 16/1/27.
 */
public class MyView extends View {
    private Bitmap bitmap;

    public MyView(Context context){
        super(context);
        if(bitmap==null)
            bitmap = Bitmap.createBitmap(200,100, Bitmap.Config.RGB_565);
    }
    public MyView(Context context,AttributeSet attributeSet)
    {
        super(context,attributeSet);
        if(bitmap==null)
            bitmap = Bitmap.createBitmap(200,100, Bitmap.Config.RGB_565);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float[] src = new float[] { 0, 0,         //左上
                bitmap.getWidth(), 0,//右上
                bitmap.getWidth(), bitmap.getHeight(),//右下
                0, bitmap.getHeight() };//左下
        float[] dst = new float[] { 0, 0,
                bitmap.getWidth(), 30,
                bitmap.getWidth(), bitmap.getHeight()-30,
                0, bitmap.getHeight() };
        Matrix mMatrix = new Matrix();
        mMatrix.setPolyToPoly(src, 0, dst, 0, src.length >> 1);
        canvas.drawBitmap(bitmap, mMatrix, null);
        canvas.drawBitmap(bitmap, 0, bitmap.getHeight(), null);
    }
}
