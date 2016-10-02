package com.ht.cn.simplechat.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.ht.cn.simplechat.R;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.com
 */
public class FlowButton extends View {

    private Paint recPaint;
    private Paint textPaint;
    private PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    private int screenWidth;
    private int screenHeight;
    private String text;
    private int textWidth;
    private int textHeight;
    private Rect textRect;
    private Paint mPaint;
    private boolean flow;
    private int width;
    private int height;
    private int flowPadding;
    private int flowColor;

    public FlowButton(Context context) {
        this(context, null);
    }

    public FlowButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FlowButton, defStyleAttr, 0);
        flowColor = typedArray.getColor(R.styleable.FlowButton_flowColor, Color.parseColor("#ffffff"));
        text = (String) typedArray.getText(R.styleable.FlowButton_flowText);
        float textSize = typedArray.getDimension(R.styleable.FlowButton_flowTextSize, 20);
        flowPadding = (int) typedArray.getDimension(R.styleable.FlowButton_flowPadding, 20);
        int flowTextColor = typedArray.getColor(R.styleable.FlowButton_flowTextColor, Color.BLACK);
        flow = typedArray.getBoolean(R.styleable.FlowButton_flow, false);
        typedArray.recycle();

        //初始化画笔
        //画矩形的画笔
        recPaint = new Paint();
        recPaint.setColor(flowTextColor);
        recPaint.setStrokeWidth(1);
        recPaint.setStyle(Paint.Style.STROKE);
        //画文字的画笔
        textPaint = new Paint();
        textPaint.setColor(flowTextColor);
        textPaint.setDither(true);
        textPaint.setTextSize(textSize);
        textPaint.setStyle(Paint.Style.FILL);
        //遮罩画笔
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(flowColor);
        mPaint.setDither(true);
        //获取屏幕宽高
        int WH[] = getScreenWH(context);
        screenWidth = WH[0];
        screenHeight = WH[1];
        //获取文字高度
        textRect = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), textRect);
        textWidth = textRect.width();
        textHeight = textRect.height();
    }

    //测量文字高度
    public double getTxtHeight(Paint mPaint) {
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        return Math.ceil(fm.descent - fm.ascent);
    }

    //测量屏幕
    public int[] getScreenWH(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(displayMetrics);
        int Swidth = displayMetrics.widthPixels;
        int Sheight = displayMetrics.heightPixels;
        int WH[] = new int[]{Swidth, Sheight};
        return WH;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widMod = MeasureSpec.getMode(widthMeasureSpec);
        int heightMod = MeasureSpec.getMode(heightMeasureSpec);
        int defWin = textWidth;
        int defHei = textHeight;

        width = widMod == MeasureSpec.EXACTLY ? MeasureSpec.getSize(widthMeasureSpec) : defWin + getPaddingLeft() + getPaddingRight()+20;
        height = heightMod == MeasureSpec.EXACTLY ? MeasureSpec.getSize(heightMeasureSpec) : defHei + getPaddingTop() + getPaddingBottom()+10;
        setMeasuredDimension(width, height);
    }
    private MyThread myThread;
    private float x = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawText(text, 0, text.length(), (width - textWidth) / 2, (height + textHeight) / 2, textPaint);
        if (flow) {
            mPaint.setColor(flowColor);
            int saveLayer = canvas.saveLayer(0, 0, getMeasuredWidth(), getMeasuredHeight(), textPaint, 0);
            canvas.drawRect(0, 0, width, height, recPaint);
            mPaint.setXfermode(xfermode);
            canvas.drawRect(0, 0, x, height, mPaint);
            mPaint.setXfermode(null);
            canvas.restoreToCount(saveLayer);
            if (null == myThread) {
                myThread = new MyThread();
                myThread.start();
            }
        } else {
            canvas.drawRect(0, 0, width, height, recPaint);
        }
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            while (true) {
                if (x >= getMeasuredWidth()) {
                    x = 0;
                }
                x += 3;
                try {
                    Thread.sleep(50);
                    postInvalidate();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setBegainFlow() {
        flow = true;
        flowColor = Color.parseColor("#ff4081");
       invalidate();
    }

    public void setEndFlow() {
        flow = false;
        if(null != myThread){
            myThread.interrupt();
        }
        invalidate();
    }
    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
    }
}
