package com.sumugu.liubo.myfirstapp.mytouch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by liubo on 16/1/26.
 */
public class SkewableTextView extends TextView {
    private float mSkewX;
    RectF mTempRect = new RectF();

    public SkewableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public SkewableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public SkewableTextView(Context context) {
        super(context);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        if (mSkewX != 0) {
//            canvas.translate(0, getHeight());
//            canvas.skew(mSkewX, 0);
//            canvas.translate(0,  -getHeight());
         float[] src = new float[]{
                0,0,
                getWidth(),0,
                getWidth(),getHeight(),
                0,getHeight()

        };
        float[] dst = new float[]{
                mSkewX,2,
                getWidth()-mSkewX,0,
                getWidth(),getHeight(),
                0,getHeight()
        };
        Matrix matrix = getMatrix();
        matrix.setPolyToPoly(src,0,dst,0,src.length>>1);

            canvas.concat(matrix);

        }
        super.onDraw(canvas);
    }
    public float getSkewX() {
        return mSkewX;
    }

    public void setSkewX(float value) {
        if (value != mSkewX) {
            mSkewX = value;
            invalidate();             // force redraw with new skew value
            invalidateSkewedBounds(); // also invalidate appropriate area of parent
        }
    }

    /**
     * Need to invalidate proper area of parent for skewed bounds
     */
    private void invalidateSkewedBounds() {
        if (mSkewX != 0) {
            Matrix matrix = new Matrix();
            matrix.setSkew(-mSkewX, 0);
            mTempRect.set(0, 0, getRight(), getBottom());
            matrix.mapRect(mTempRect);
            mTempRect.offset(getLeft() + getTranslationX(), getTop() + getTranslationY());
            ((View) getParent()).invalidate((int) mTempRect.left, (int) mTempRect.top,
                    (int) (mTempRect.right +.5f), (int) (mTempRect.bottom + .5f));
        }
    }
}