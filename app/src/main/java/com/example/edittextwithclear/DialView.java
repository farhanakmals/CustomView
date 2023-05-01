package com.example.edittextwithclear;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DialView extends View {
    private static int SELECTION_COUNT = 4;

    private float mWidth;
    private float mHeight;

    private Paint mTextPaint;
    private Paint mDialPaint;

    private float mRadius;
    private  int mActionSelection;

    private final StringBuffer mTempLable = new StringBuffer(8);
    private final float[] mTempResult = new float[2];

    private void init() {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mDialPaint.setTextSize(40f);

        mDialPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDialPaint.setColor(Color.GRAY);

        mActionSelection = 0;

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mActionSelection = (mActionSelection + 1) % SELECTION_COUNT;

                if (mActionSelection >= 1) {
                    mDialPaint.setColor(Color.GREEN);
                } else {
                    mDialPaint.setColor(Color.GRAY);
                }
                invalidate();
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mRadius = (float) (Math.min(mWidth, mHeight/2*0.8));
    }

    public DialView(Context context) {
        super(context);
        init();
    }

    public DialView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DialView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private float[] computeXYPositiuon(final int pos, final float radius) {
        float[] result = mTempResult;
        Double startAngel = Math.PI * (9/8d);
        Double angle = startAngel + (pos * (Math.PI / 4));
        result[0] = (float) (radius * Math.cos(angle) + (mWidth / 2));
        result[1] = (float) (radius * Math.sin(angle) + (mHeight / 2));
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mDialPaint);

        // draw lable
        final float lableRadius = mRadius + 20;
        StringBuffer lable = mTempLable;

        for (int i = 0; i < SELECTION_COUNT; i++) {
            float[] xyData = computeXYPositiuon(i, lableRadius);
            float x = xyData[0];
            float y = xyData[1];
            lable.setLength(0);
            lable.append(i);
            canvas.drawText(lable, 0, lable.length(), x, y, mTextPaint);
        }

        // draw small indicator circle
        final float markerRadius = mRadius - 35;
        float[] xyData = computeXYPositiuon(mActionSelection, markerRadius);
        float x = xyData[0];
        float y = xyData[1];

        canvas.drawCircle(x, y, 20, mTextPaint);

    }
}
