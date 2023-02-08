package com.example.finalproject;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;



public class Circle extends View {
    private int circleColor;
    private int circleTextColor;
    private String circleText;
    private Paint paint;
    private Rect textBounds;

    public int getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
    }

    public int getCircleTextColor() {
        return circleTextColor;
    }

    public void setCircleTextColor(int circleTextColor) {
        this.circleTextColor = circleTextColor;
    }

    public String getCircleText() {
        return circleText;
    }

    public void setCircleText(String circleText) {
        this.circleText = circleText;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Rect getTextBounds() {
        return textBounds;
    }

    public void setTextBounds(Rect textBounds) {
        this.textBounds = textBounds;
    }

    public Circle(Context context, AttributeSet attrs) {
        super(context, attrs);

//        circleText = "wAZESXCTFGBHUJNINHUBGVYCTDRXESZ";

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Circle, 0, 0);
        try {
            circleColor = typedArray.getColor(R.styleable.Circle_circleColor, Color.RED);
            circleText = typedArray.getString(R.styleable.Circle_circleText);
            circleTextColor = typedArray.getColor(R.styleable.Circle_circleTextColor, Color.BLACK);
        } finally {
            typedArray.recycle();
        }
        paint = new Paint();
        textBounds = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(circleColor);
        int viewWidthHalf = this.getMeasuredWidth() / 2;
        int viewHeightHalf = this.getMeasuredHeight() / 2;
        int radius = 0;
        if (viewWidthHalf > viewHeightHalf) {
            radius = viewHeightHalf - 10;
        } else {
            radius = viewWidthHalf - 10;
        }
        canvas.drawCircle(viewWidthHalf, viewHeightHalf, radius, paint);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(40);

        if(circleText != null) {
            paint.getTextBounds(circleText, 0, circleText.length(), textBounds);
            int textHeight = textBounds.height();
            int textWidth = textBounds.width();
            canvas.drawText(circleText, viewWidthHalf, viewHeightHalf + textHeight / 2, paint);
        }

//        paint.getTextBounds(circleText, 0, circleText.length(), textBounds);
//        int textHeight = textBounds.height();
//        int textWidth = textBounds.width();
//        canvas.drawText(circleText, viewWidthHalf, viewHeightHalf + textHeight / 2, paint);
    }
}
