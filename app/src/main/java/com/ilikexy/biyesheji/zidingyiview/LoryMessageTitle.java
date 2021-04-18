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

import com.ilikexy.biyesheji.R;

public class LoryMessageTitle extends androidx.appcompat.widget.AppCompatTextView {
    //背景图案
    private float progress = 1f;//移动比例参数progress，默认是0
    private int mRoundedRadius = 3;//圆角矩形直径默认长度
    private int mTraigleLength = 4;//直角三角形对角线边默认一半长度
    private int mRectHeight = 20;//圆角矩形默认高度
    private int mRectWidth = 20;//圆角矩形默认长度
    private int mColor = getResources().getColor(R.color.colorGreener,null);//控件默认颜色
    private Paint mPaint;//控件绘制的画笔，由于控件背景色颜色统一，所以用一支画笔就可以了

    //字体
    private int mOrignColor = getResources().getColor(R.color.colorChange,null);//默认字体原始颜色
    private int mChangeColor = Color.WHITE;//默认字体变化颜色
    private Paint mOrignPaint,mChangePaint;//绘制两种字体的画笔
    private int textsize = 50;//默认原字体大小
    private int textsize1 = 55;//默认字体变化的大小
    private int mDirection = 1; //默认变化从左到右
    public LoryMessageTitle(Context context){
        this(context,null);
    }
    public LoryMessageTitle(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }
    public LoryMessageTitle(Context context,AttributeSet attrs,int delStyleAttr){
        super(context,attrs,delStyleAttr);
        //初始化
        init(context,attrs);
    }
    //初始化函数
    public void init(Context context,AttributeSet attrs){
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LoryMessageTitle);
        mRoundedRadius = array.getDimensionPixelSize(R.styleable.LoryMessageTitle_roudedRadius,sp2px(mRoundedRadius));
        mTraigleLength = array.getDimensionPixelSize(R.styleable.LoryMessageTitle_triangleLengthHalf,sp2px(mTraigleLength));
        mRectHeight = array.getDimensionPixelSize(R.styleable.LoryMessageTitle_rectHeight,sp2px(mRectHeight));
        mColor = array.getColor(R.styleable.LoryMessageTitle_colorAll,mColor);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setDither(true);
        mPaint.setColor(mColor);
        //字体
        mOrignColor = array.getColor(R.styleable.LoryMessageTitle_orignColor,mOrignColor);
        mChangeColor = array.getColor(R.styleable.LoryMessageTitle_changeColor,mChangeColor);
        mDirection = array.getInteger(R.styleable.LoryMessageTitle_changeDirection,mDirection);
        //orignPaint
        mOrignPaint = new Paint();
        mOrignPaint.setColor(mOrignColor);
        mOrignPaint.setAntiAlias(true);
        mOrignPaint.setDither(true);
        mOrignPaint.setTextSize(textsize);
        //changePaint
        mChangePaint = new Paint();
        mChangePaint.setColor(mChangeColor);
        mChangePaint.setAntiAlias(true);
        mChangePaint.setDither(true);
        mChangePaint.setTextSize(textsize1);

    }
    //sp  to  px  不多说，就是 sp转化为 px,要是控件使用者用dp怎么办，那就让他自己添加个dp转sp的函数，就你事多
    public int sp2px(int sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, this.getResources().getDisplayMetrics());
    }
    //测量方法

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthmode = MeasureSpec.getMode(widthMeasureSpec);
        int heightmode = MeasureSpec.getMode(heightMeasureSpec);
        int widthsize = MeasureSpec.getSize(widthMeasureSpec);
        int heightsize = MeasureSpec.getSize(heightMeasureSpec);
        //文字决定背景的长宽,用画笔测量文字的宽度，长度
        Rect bounds = new Rect();
        mChangePaint.getTextBounds(getText().toString(),0,getText().length(),bounds);
        //要是用户填的wrap_content,宽度、高度至少
        if (widthmode==MeasureSpec.AT_MOST){
            int widthsize1 = sp2px(mRectWidth)+getPaddingLeft()+getPaddingRight();//背景宽度
            int widthsize2 = bounds.width() + getPaddingLeft()+getPaddingRight();//文字宽度
            widthsize = widthsize2+(widthsize1)/2;
        }
        if (heightmode==MeasureSpec.AT_MOST){
            int heightsize1 = mRectHeight+mTraigleLength+getPaddingTop()+getPaddingBottom();
            int heightsize2 = bounds.height() + +getPaddingTop()+getPaddingBottom();
            heightsize = heightsize2+(heightsize1)/2;
        }
        setMeasuredDimension(widthsize,heightsize);
    }
    //绘制方法,一开始绘制一个静态的控件，然后这个控件会移动，有个0-1的参数，决定这个控件移动的比例，移动的方向
    //我们先做它的从左到右的移动。引入这个参数叫 progress。默认是0；

    @Override
    protected void onDraw(Canvas canvas) {
        //静态控件如下
//        //首先绘制圆角矩形
//        { canvas.save();
//        RectF rectf = new RectF(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom() - mTraigleLength);
//        canvas.drawRoundRect(rectf, mRoundedRadius, mRoundedRadius, mPaint);
//        canvas.restore();
//        //然后绘制三角形,是一个倒过来的直角三角形，可以用一个倒过来的正方形代替，对角线和矩形重合即可，也可以通过路径绘制
//        canvas.save();
//        Path path = new Path();
//        path.moveTo(getWidth() / 2 - mTraigleLength, getHeight() - getPaddingBottom() - mTraigleLength);//起点
//        path.lineTo(getWidth() / 2, getHeight() - getPaddingBottom());//往下移动
//        path.lineTo(getWidth() / 2 + mTraigleLength, getHeight() - getPaddingBottom() - mTraigleLength);//往右移动
//        path.close();//路径闭合
//        canvas.drawPath(path, mPaint);
//        canvas.restore();
//    }
        //首先绘制圆角矩形
        mPaint.setColor(mColor);
        mOrignPaint.setColor(mOrignColor);
        mChangePaint.setColor(mChangeColor);
       //首先绘制圆角矩形,随着progress运动,圆角矩形的左边x参数在增加，右边参数也在增加，由于控件长度固定，所以可以出现
        //圆角矩形右侧被吞噬的样子
       float x1;
       float x2;
        Rect bounds = new Rect();
        mChangePaint.getTextBounds(getText().toString(),0,getText().length(),bounds);//拿画笔测量文字矩形
        if (mDirection==2){
           x1 = (1-progress)*(getWidth())+getPaddingLeft()-15;
           x2 = (2-progress)*(getWidth())-getPaddingRight()+15;
        }else {
            x1 = (progress-1)*(getWidth())+getPaddingLeft()-15;
           x2 = progress*(getWidth())-getPaddingRight()+15;
        }
        canvas.save();
        RectF rectf =new RectF(x1,getPaddingTop(),
                x2,getHeight()-getPaddingBottom()-mTraigleLength);
        canvas.drawRoundRect(rectf,mRoundedRadius,mRoundedRadius,mPaint);
        //然后绘制三角形,是一个倒过来的直角三角形，可以用一个旋转的正方形代替，对角线和矩形重合即可，也可以通过路径绘制
        canvas.save();
        Path path = new Path();
        path.moveTo(getWidth()/2-mTraigleLength+x1+15-getPaddingLeft(),getHeight()-getPaddingBottom()-mTraigleLength);//起点
        path.lineTo(getWidth()/2+x1+15-getPaddingLeft(),getHeight()-getPaddingBottom());//往下移动
        path.lineTo(getWidth()/2+mTraigleLength+x1+15-getPaddingLeft(),getHeight()-getPaddingBottom()-mTraigleLength);//往右移动
        path.close();//路径闭合
        canvas.drawPath(path,mPaint);
        canvas.restore();
        //之后，我们和上次开发的可变字体进行结合，实现字体的变动，类似于内涵段子，不过它好像被封了

        int x = getWidth()/2-bounds.width()/2;
        Paint.FontMetricsInt fontMetricsInt = mOrignPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top)/2 - fontMetricsInt.bottom;
        int baseline = getHeight()/2+dy -mTraigleLength/2;//以圆角矩形为中心
        //在绘制变色文字，覆盖原文字
        if(mDirection==1){
            canvas.save();
            canvas.clipRect(0,0,progress*getWidth()-getPaddingLeft()+15,getHeight());
            canvas.drawText(getText().toString(),x,baseline,mChangePaint);
            canvas.restore();
            canvas.save();
            canvas.clipRect(progress*getWidth()-getPaddingLeft()+15,0,getWidth(),getHeight());
            canvas.drawText(getText().toString(),x,baseline,mOrignPaint);
            canvas.restore();
        }else{
            canvas.save();
            canvas.clipRect(0,0,(1-progress)*getWidth()+getPaddingRight()-15,getHeight());
            canvas.drawText(getText().toString(),x,baseline,mOrignPaint);
            canvas.restore();
            canvas.save();
            canvas.clipRect((1-progress)*getWidth()+getPaddingRight()-15,0,getWidth(),getHeight());
            canvas.drawText(getText().toString(),x,baseline,mChangePaint);
            canvas.restore();
        }

    }
//  最后就是这个控件随progress变化，而重新绘制。
    public void changeProgress(float progresser){
        progress = progresser;
        invalidate();//重新绘制
    }
// 也有人说，我不想在xml文件中设置控件状态，我想java文件中改变控件的圆角矩形高度、圆角大小，小三角形对角线宽一半，颜色等
    //你丫的需求真多呀，就你多事
    //那字体呢，我想Java文件设置字体颜色、大小。。。。
    public void setSate(int roundedRadius,int roundedheight,int traigleLengthHalf,int color){
        mRoundedRadius = roundedRadius;
        mRectHeight = roundedheight;
        mTraigleLength = traigleLengthHalf;
        mColor = color;
        invalidate();
    }
    public void setmOrignColor(int c_color){
        this.mOrignColor = c_color;
        invalidate();
    }
    public void setmChangeColor(int c_color){
        this.mChangeColor = c_color;
        invalidate();
    }
    //设置绘制时字体 大小 粗细于一体
    public void setmWordSize(int orignSize,int changSize){
        this.textsize = orignSize;
        this.textsize1 = changSize;
        invalidate();
    }
    public void setdirction(int c_direction){
        mDirection = c_direction;
        invalidate();
    }
    public void setmColor(int color){
        mColor = color;
        invalidate();
    }
}
