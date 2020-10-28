package com.example.homework4_2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import java.util.Random;

public class Custom extends View {
    Random rand = new Random();
    private View arc1;
    private Paint paintTemp;
    private Paint paintSmall;
    private Paint paintArc1;
    private Paint paintArc2;
    private Paint paintArc3;
    private Paint paintArc4;
    final RectF oval = new RectF();
    private static final int WIDTH = 150;
    private static final int HEIGHT = 200;
    private int centerX;
    private int centerY;
    private float smallCircleArea;
    private float bigCircleArea;
    CoordinateListener coordinateListener;

    public Custom(Context context) {
        super(context);
    }

    public Custom(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    public Custom(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Custom(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setCoordinateListener(CoordinateListener coordinateListener) {
        this.coordinateListener = coordinateListener;
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.Custom);
        paintArc1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintArc1.setColor(typedArray.getColor(R.styleable.Custom_arc1Color, 0));
        paintArc1.setStyle(Paint.Style.FILL);
        paintArc2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintArc2.setColor(typedArray.getColor(R.styleable.Custom_arc2Color, 0));
        paintArc2.setStyle(Paint.Style.FILL);
        paintArc3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintArc3.setColor(typedArray.getColor(R.styleable.Custom_arc3Color, 0));
        paintArc3.setStyle(Paint.Style.FILL);
        paintArc4 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintArc4.setColor(typedArray.getColor(R.styleable.Custom_arc4Color, 0));
        paintArc4.setStyle(Paint.Style.FILL);
        paintSmall = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintSmall.setColor(typedArray.getColor(R.styleable.Custom_circleColorSmall, 0));
        paintSmall.setStyle(Paint.Style.FILL);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        centerX = MeasureSpec.getSize(widthMeasureSpec) / 2;
        centerY = MeasureSpec.getSize(heightMeasureSpec) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        oval.set(centerX - 400, centerY - 400, centerX + 400,
                centerY + 400);
        canvas.drawArc(oval, 0, 90, true, paintArc1);
        canvas.drawArc(oval, 90, 90, true, paintArc2);
        canvas.drawArc(oval, 180, 90, true, paintArc3);
        canvas.drawArc(oval, 270, 90, true, paintArc4);
        canvas.drawCircle(centerX, centerY, 150, paintSmall);
        super.onDraw(canvas);
    }

    public boolean isFirstSector(MotionEvent event) {
        return (event.getX() > centerX && event.getX() < centerX + 400 && event.getY() > centerY && event.getY() < centerY + 400);
    }

    public boolean isSecondSector(MotionEvent event) {
        return (event.getX() < centerX && event.getX() > centerX - 400 && event.getY() > centerY && event.getY() < centerY + 400);
    }

    public boolean isThirdSector(MotionEvent event) {
        return (event.getX() < centerX && event.getX() > centerX - 400 && event.getY() < centerY && event.getY() > centerY - 400);
    }

    public boolean isFourthSector(MotionEvent event) {
        return (event.getX() > centerX && event.getX() < centerX + 400 && event.getY() < centerY && event.getY() > centerY - 400);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        smallCircleArea = (event.getX() - centerX) * (event.getX() - centerX) + (event.getY() - centerY) * (event.getY() - centerY);
        bigCircleArea = (event.getX() - centerX) * (event.getX() - centerX) + (event.getY() - centerY) * (event.getY() - centerY);

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            if (smallCircleArea <= 150 * 150) {
                paintTemp = new Paint();
                paintTemp.setColor(paintArc4.getColor());
                paintArc4.setColor(paintArc3.getColor());
                paintArc3.setColor(paintArc2.getColor());
                paintArc2.setColor(paintArc1.getColor());
                paintArc1.setColor(paintTemp.getColor());
                coordinateListener.getCoordinates(event.getX(), event.getY(), paintSmall.getColor());
                invalidate();
            } else if (bigCircleArea <= 400 * 400) {
                if (isFirstSector(event)) {
                    paintArc1.setColor(rand.nextInt(2147483646));
                    coordinateListener.getCoordinates(event.getX(), event.getY(), paintArc1.getColor());
                    invalidate();
                } else if (isSecondSector(event)) {
                    paintArc2.setColor(rand.nextInt(2147483646));
                    coordinateListener.getCoordinates(event.getX(), event.getY(), paintArc2.getColor());
                    invalidate();
                } else if (isThirdSector(event)) {
                    paintArc3.setColor(rand.nextInt(2147483646));
                    coordinateListener.getCoordinates(event.getX(), event.getY(), paintArc3.getColor());
                    invalidate();
                } else if (isFourthSector(event)) {
                    paintArc4.setColor(rand.nextInt(2147483646));
                    coordinateListener.getCoordinates(event.getX(), event.getY(), paintArc4.getColor());
                    invalidate();
                }
            }
        }
        return super.onTouchEvent(event);
    }
}