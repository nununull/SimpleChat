package com.ht.cn.simplechat.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ht.cn.simplechat.R;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.com
 */
public class CircleImage extends View {

    private float clipRadius = 0.35f;
    private Bitmap bitmap;
    private int bitmapWidth;
    private int bitmapHeight;
    private float circleRadius;
    private PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    private Paint paint = null;
    private Paint mPaint;

    public CircleImage(Context context) {
        this(context, null);
    }

    public CircleImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleImage, defStyleAttr, 0);
        //获取图片的资源id
        //这里使用的自定义属性，没有你就直接加载图片吧
        int imgResouce = typedArray.getResourceId(R.styleable.CircleImage_image, R.mipmap.simplechat_2);
        //截取的百分比，默认0.35
        clipRadius = typedArray.getFloat(R.styleable.CircleImage_clipRadius, 0.35f);
        //使用完了记得释放
        typedArray.recycle();
        if (imgResouce != R.mipmap.simplechat_2) {
            bitmap = BitmapFactory.decodeResource(getResources(), imgResouce);
        } else {
            bitmap = BitmapFactory.decodeResource(getResources(), imgResouce);
        }
        //图片宽度
        designBitmap(bitmap);
        //初始化画笔
        paint = new Paint();
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(3);
        //关闭硬件加速，PorterDuffXfermode 这个API的bug貌似还蛮多，你可以选择不关闭试试
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    private void designBitmap(Bitmap bitmap) {
        bitmapWidth = bitmap.getWidth();
        //图片的高度
        bitmapHeight = bitmap.getHeight();
        //使用小的一边的0.35长度作为半径
        circleRadius = Math.min(bitmapWidth, bitmapHeight) * 0.35f;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widMod = MeasureSpec.getMode(widthMeasureSpec);
        int heightMod = MeasureSpec.getMode(heightMeasureSpec);

        int width = widMod == MeasureSpec.EXACTLY ? MeasureSpec.getSize(widthMeasureSpec) : bitmapWidth + getPaddingRight() + getPaddingLeft();
        int height = heightMod == MeasureSpec.EXACTLY ? MeasureSpec.getSize(heightMeasureSpec) : bitmapHeight + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int saveLayer = canvas.saveLayer(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint, 0);
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, circleRadius, paint);
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        paint.setXfermode(xfermode);
        canvas.drawBitmap(bitmap, (getMeasuredWidth() - bitmapWidth) / 2, (getMeasuredHeight() - bitmapHeight) / 2, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(saveLayer);
        canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,circleRadius+1.5f,mPaint);
    }

    //传入图片资源id进行图片设置
    public void setImageResource(int imgResource) {
        if (0 == imgResource) {
            new NullPointerException();
        }
        bitmap = BitmapFactory.decodeResource(getResources(), imgResource);
        designBitmap(bitmap);
        invalidate();
    }

    //传入图片进行图片设置
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        designBitmap(bitmap);
        Log.e("CircleImageView","开始执行invalidate()");
        invalidate();
    }
}
