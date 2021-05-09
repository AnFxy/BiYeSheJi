package com.ilikexy.biyesheji.zidingyiview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.ilikexy.biyesheji.R;

public class QQStep extends androidx.appcompat.widget.AppCompatTextView {
    private int strokeSize = 10;//默认圆弧的长度
    private int numberSize = 34;//默认数字大小
    private int rightTextSize  = 16;//默认正确率大小
    private int countAllAnswerSize = 16;// 默认做的总体数字体大小
    private int strokeBackColor = Color.parseColor("#ededed");
    private int strokeSolidColor = Color.parseColor("#00aaff");
    private int numberColor = Color.parseColor("#aa00ff");
    private int rightTextColor = Color.parseColor("#03c45c");
    private int countAllAnswerColor = Color.parseColor("#FF69B4");
    private  int  allAnswer = 0;
    private int rightScale = 50;
    /**画笔,有5支画笔，绘制圆弧背景，绘制圆弧填充颜色，绘制数字，绘制正确率，绘制总题数*/
    private Paint strokebackPaint,strokesolidPaint,numberPaint,righttextPaint,countallPaint,borderPaint;
    public QQStep(Context context) {
        this(context,null);
    }

    public QQStep(Context context,  AttributeSet attrs) {
        this(context, attrs,0);
    }

    public QQStep(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    //sp  to  px  不多说，就是 sp转化为 px,要是控件使用者用dp怎么办，那就让他自己添加个dp转sp的函数，就你事多
    public int sp2px(int sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, this.getResources().getDisplayMetrics());
    }

    public void init(Context contexter, AttributeSet attrss){
        TypedArray array = contexter.obtainStyledAttributes(attrss,R.styleable.QQStep);
        strokeSize = array.getDimensionPixelSize(R.styleable.QQStep_strokeSize,sp2px(strokeSize));
        numberSize = array.getDimensionPixelSize(R.styleable.QQStep_numberSize,sp2px(numberSize));
        rightTextSize = array.getDimensionPixelSize(R.styleable.QQStep_rightTextSize,sp2px(rightTextSize));
        countAllAnswerSize = array.getDimensionPixelSize(R.styleable.QQStep_countAllSize,sp2px(countAllAnswerSize));
        strokeBackColor = array.getColor(R.styleable.QQStep_strokeBackColor,strokeBackColor);
        strokeSolidColor = array.getColor(R.styleable.QQStep_strokeSloidColor,strokeSolidColor);
        numberColor = array.getColor(R.styleable.QQStep_numberColor,numberColor);
        rightTextColor = array.getColor(R.styleable.QQStep_rightTextColor,rightTextColor);
        countAllAnswerColor = array.getColor(R.styleable.QQStep_countAllAnswerColor,countAllAnswerColor);
        allAnswer = array.getInteger(R.styleable.QQStep_allAnswer,allAnswer);
        rightScale = array.getInteger(R.styleable.QQStep_rightScale,rightScale);
        strokebackPaint = new Paint();
        strokebackPaint.setStyle(Paint.Style.STROKE);
        strokebackPaint.setStrokeWidth(strokeSize);
        strokebackPaint.setStrokeCap(Paint.Cap.ROUND);
        initPaint(strokebackPaint,strokeBackColor);
        strokesolidPaint  = new Paint();
        strokesolidPaint.setStyle(Paint.Style.STROKE);
        strokesolidPaint.setStrokeWidth(strokeSize);
        strokesolidPaint.setStrokeCap(Paint.Cap.ROUND);
        initPaint(strokesolidPaint,strokeSolidColor);
        numberPaint = new Paint();
        numberPaint.setFakeBoldText(true);
        numberPaint.setTextSize(numberSize);
        initPaint(numberPaint,numberColor);
        righttextPaint = new Paint();
        righttextPaint.setTextSize(rightTextSize);
        righttextPaint.setFakeBoldText(true);
        initPaint(righttextPaint,rightTextColor);
        countallPaint = new Paint();
        countallPaint.setTextSize(countAllAnswerSize);
        countallPaint.setFakeBoldText(true);
        initPaint(countallPaint,countAllAnswerColor);
        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(4);
        borderPaint.setStrokeCap(Paint.Cap.ROUND);
        initPaint(borderPaint,Color.parseColor("#03c45c"));
    }
   /**画笔初始化*/
   public void initPaint(Paint paint,int color){
       paint.setAntiAlias(true);
       paint.setDither(true);
       paint.setColor(color);
   }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthmode = MeasureSpec.getMode(widthMeasureSpec);
        int heightmode = MeasureSpec.getMode(heightMeasureSpec);
        int widthsize = MeasureSpec.getSize(widthMeasureSpec);
        int heightsize = MeasureSpec.getSize(heightMeasureSpec);
//        //正确率，做题总数，两个文字决定整个控件的宽高
//        Rect bounds= new Rect();
//        righttextPaint.getTextBounds(getText().toString(),0,getText().length(),bounds);
//        Rect bounds1 = new Rect();
//        countallPaint.getTextBounds(getText().toString(),0,getText().length(),bounds1);
        //要是用户填的wrap_content,宽度、高度至少
        if (widthmode==MeasureSpec.AT_MOST){
            widthsize = sp2px(120)+getPaddingLeft()+getPaddingRight();
        }
        if (heightmode == MeasureSpec.AT_MOST){
            heightsize = sp2px(120)+getPaddingTop()+getPaddingBottom();
        }
        int maxsize = widthsize>heightsize?widthsize:heightsize;
        setMeasuredDimension(maxsize,maxsize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //先绘制外圆弧
        RectF rectF = new RectF(getPaddingLeft()+strokeSize/2,getPaddingTop()+strokeSize/2,
                getWidth()-getPaddingLeft()-getPaddingRight()-strokeSize/2,
                getHeight()-getPaddingTop()-getPaddingBottom()-strokeSize/2);
        canvas.drawArc(rectF,150,240,false,strokebackPaint);
        //绘制填充圆弧
        RectF rectF1= new RectF(getPaddingLeft()+strokeSize/2,getPaddingTop()+strokeSize/2,
                getWidth()-getPaddingLeft()-getPaddingRight()-strokeSize/2,
                getHeight()-getPaddingTop()-getPaddingBottom()-strokeSize/2);
        canvas.drawArc(rectF1,150,rightScale*2.4f,false,strokesolidPaint);

        //绘制数字
        Rect bounds = new Rect();//画笔测量
        numberPaint.getTextBounds(""+rightScale+"%",0,(""+rightScale+"%").length(),bounds);
        int x = getWidth()/2-bounds.width()/2;
        Paint.FontMetricsInt fontMetricsInt = numberPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top)/2 - fontMetricsInt.bottom;
        int baseline = getHeight()/2+dy-getHeight()/8;
        canvas.drawText(""+rightScale+"%",x,baseline,numberPaint);

        //绘制一个圆角弧度
        Rect bounds2 = new Rect();//画笔测量
        righttextPaint.getTextBounds("正确率",0,"正确率".length(),bounds2);
        RectF rectF2 = new RectF(64,getHeight()/2+bounds2.height()/4,
                getWidth()-64,
                getHeight()/2+2*bounds2.height());
        //绘制正确率
        canvas.drawText("正确率",getWidth()/2-bounds2.width()/2,baseline+bounds.height(),righttextPaint);

        canvas.drawRoundRect(rectF2,bounds2.height()/2,bounds2.height()/2,borderPaint);


        //绘制总题数
        Rect bounds3 = new Rect();//画笔测量
        countallPaint.getTextBounds("做题总数"+allAnswer+"道",0,("做题总数"+allAnswer+"道").length(),bounds3);
        canvas.drawText("做题总数"+allAnswer+"道",getWidth()/2-bounds3.width()/2,
                baseline+bounds.height()+bounds2.height()+strokeSize,countallPaint);

    }
    public void drawAgain(int rightscale,int allanswer){
        this.rightScale = rightscale;
        this.allAnswer = allanswer;
        invalidate();
    }
}
