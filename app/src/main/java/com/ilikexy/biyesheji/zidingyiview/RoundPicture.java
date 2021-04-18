package com.ilikexy.biyesheji.zidingyiview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.ilikexy.biyesheji.R;
import com.ilikexy.biyesheji.photouri.PicCutAndScale;

import androidx.annotation.Nullable;

public class RoundPicture extends androidx.appcompat.widget.AppCompatImageView {
    //需要裁剪的图片是个默认头像
    public Bitmap mbitmap = BitmapFactory.decodeResource(getResources(),R.drawable.graytouxiang);
    private int mPicBorderSize = 4;//图片边框默认大小为4
    private int mPicBorderColor = Color.WHITE;//边框默认为白色
    private Paint mBorderPaint;//边框画笔
    private Paint mPicPaint;//图片裁剪画笔
    public RoundPicture(Context context) {
        this(context,null);
    }

    public RoundPicture(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundPicture(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAppAttrbutes(context,attrs);
    }
    //sp  to  px  不多说，就是 sp转化为 px,要是控件使用者用dp怎么办，那就让他自己添加个dp转sp的函数，就你事多
    public int sp2px(int sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, this.getResources().getDisplayMetrics());
    }
    //初始化构造方法，将自定义的一些属性进行
    public void initAppAttrbutes(Context context,AttributeSet attrs){
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundPicture);
        mPicBorderColor = array.getColor(R.styleable.RoundPicture_borderColor,mPicBorderColor);
        mPicBorderSize = array.getDimensionPixelSize(R.styleable.RoundPicture_borderSize,sp2px(mPicBorderSize));
        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setDither(true);
        mBorderPaint.setColor(mPicBorderColor);
        mPicPaint = new Paint();
        mPicPaint.setDither(true);
        mPicPaint.setAntiAlias(true);
        //默认从控件设置中放置图片，要是控件没设置图片则用默认图片
        if (getDrawable()!=null){
            mbitmap = ((BitmapDrawable)getDrawable()).getBitmap();//从控件中获取
        }
    }
    //测量绘制图片
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthmode = MeasureSpec.getMode(widthMeasureSpec);
        int heightmode = MeasureSpec.getMode(heightMeasureSpec);
        int widthsize = MeasureSpec.getSize(widthMeasureSpec);
        int heightsize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthmode==MeasureSpec.AT_MOST){//要是有wrap_Content的话
            widthsize = getPaddingLeft()+getPaddingRight()+sp2px(35)+2*mPicBorderSize;
        }
        if (heightmode==MeasureSpec.AT_MOST){
            heightsize = getPaddingBottom()+getPaddingTop()+sp2px(35)+2*mPicBorderSize;
        }
        setMeasuredDimension(widthsize,heightsize);
    }
    //开始绘制图片

    @Override
    protected void onDraw(Canvas canvas) {
        //先绘制边框
        RectF rectF = new RectF(getPaddingLeft(),getPaddingTop(),
                getWidth()-getPaddingLeft(),getHeight()-getPaddingTop());
        canvas.drawArc(rectF,0,360,false,mBorderPaint);
        //再绘制图片
        canvas.save();
        Path path = new Path();
        path.addCircle(getWidth()/2+getPaddingLeft()-getPaddingRight(),getHeight()/2+getPaddingTop()-getPaddingBottom(),
                (getWidth()-getPaddingLeft()-getPaddingRight())/2-mPicBorderSize, Path.Direction.CCW);
        canvas.clipPath(path);
        Bitmap mmbitmap = PicCutAndScale.getSquareRect(mbitmap,getWidth()-getPaddingLeft()-getPaddingRight()-2*mPicBorderSize);
        canvas.drawBitmap(mmbitmap,getPaddingLeft()+mPicBorderSize,getPaddingTop()+mPicBorderSize,mPicPaint);
        canvas.restore();
    }
    //修改参数
    public void setmPicBorderSize(int cmPicBorderSize){
        mPicBorderSize = cmPicBorderSize;
        invalidate();
    }
    public void setmPicBorderColor(int cmPicBorderColor){
        mPicBorderColor = cmPicBorderColor;
        invalidate();
    }
    public void setmMBitmap(Bitmap cmbitmap){
        mbitmap = cmbitmap;
        invalidate();
    }
}
