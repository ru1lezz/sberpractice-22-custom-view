package com.example.android.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class MyCustomView extends View {

    private static final int RECT_SIZE_DEFAULT = 100;
    private static final Random RANDOM = new Random();

    private Paint mPaint;
    private Rect mRect;

    private int mRectColor;
    private int mRectSize;
    private int mRectLeftX;
    private int mRectTopY;
    private int mRectRightX;
    private int mRectBottomY;
    private int mPointerX;
    private int mPointerY;

    public MyCustomView(Context context) {
        super(context);

        init(null);
    }

    public MyCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public MyCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    public MyCustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mRect = new Rect();

        mRectLeftX = 10;
        mRectTopY = 10;
        mRectRightX = 300;
        mRectBottomY = 150;

        if(attrs == null)
            return;

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MyCustomView);

        mRectColor = typedArray.getColor(R.styleable.MyCustomView_rect_color, Color.BLACK);
        mRectSize = typedArray.getDimensionPixelSize(R.styleable.MyCustomView_rect_size, RECT_SIZE_DEFAULT);

        mPaint.setColor(mRectColor);
        mPaint.setTextSize(100);

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mRect.left = mRectLeftX;
        mRect.top = mRectTopY;
        mRect.right = mRectRightX;
        mRect.bottom = mRectBottomY;
        canvas.drawRect(mRect, mPaint);
        canvas.drawText(String.format("x: %s  y: %s", mRect.left, mRect.top), 200, 200, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if(mRect.contains(x ,y)) {
                    mPointerX = x - mRectLeftX;
                    mPointerY = y - mRectTopY;
                    postInvalidate();
                    return true;
                }
            }
            case MotionEvent.ACTION_MOVE: {
                if(mRect.contains(x ,y)) {
                    mRectLeftX = x - mPointerX;
                    mRectTopY = y - mPointerY;
                    mRectRightX += mRectLeftX - mRect.left;
                    mRectBottomY += mRectTopY - mRect.top;
                    postInvalidate();
                    return true;
                }

            }
            case MotionEvent.ACTION_UP: {
                mPointerY = 0;
                mPointerX = 0;
                mPaint.setColor(Color.rgb(RANDOM.nextInt(256), RANDOM.nextInt(256), RANDOM.nextInt(256)));
                postInvalidate();
                return true;
            }
        }
        return value;
    }
}
