package com.myfittinglife.viewpager2demo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.viewpager2.widget.ViewPager2

/**
 * @Author LD
 * @Time 2020/11/24 16:21
 * @Describe 自定义指示器
 * @Modify
 */
class CircleIndicatorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    //计算圆心坐标加上的
    var mIndicators = mutableListOf<Indicator>()

    // indicator 的数量
    private var mCount = 0
    //半径
    private var mRadius = 0
    //border-画笔宽度
    private var mStrokeWidth = 0
    // 圆之间的间距
    private var mSpace = 0

    //-----------------------------------------------

    //绘制圆加上的
    //选中的位置
    private var mSelectPosition: Int = 0
    //画圆的画笔
    private val mCirclePaint = Paint()
    //被选中的颜色
    private var mSelectColor = 0
    // 小圆点默认颜色
    private var mDotNormalColor = 0

    //----------------------------------------------
    //Indicator的点击事件监听器
    private var mOnIndicatorClickListener: OnIndicatorClickListener? = null

    //-----------------------------------------------
    //要关联的ViewPager
    private var mViewPager: ViewPager2? = null


    //圆心坐标
    inner class Indicator {
        // 圆心x坐标
        var cx = 0f
        // 圆心y 坐标
        var cy = 0f
    }
    init {
        //画笔设置
        mCirclePaint.isDither = true
        mCirclePaint.isAntiAlias = true
        mCirclePaint.style = Paint.Style.FILL_AND_STROKE
        mCirclePaint.color = mDotNormalColor
        mCirclePaint.strokeWidth = mStrokeWidth.toFloat() //画笔宽度

        getAttr(context, attrs!!)

    }

    /**
     * 计算View的大小，就是整个自定义控件的大小
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = (mRadius + mStrokeWidth) * 2 * mCount + mSpace * (mCount - 1)
        //widthMeasureSpec和heightMeasureSpec是在XML里设置的宽度和高度
        if(heightMeasureSpec<2 * mRadius){
            //决定了当前View的大小
            setMeasuredDimension(width, 2 * mRadius)
        }else{
            //决定了当前View的大小
            setMeasuredDimension(width, heightMeasureSpec)
        }
        //在这里测量每个圆点的位置
        measureIndicator()
    }

    /**
     * 测量每个圆点的位置
     */
    private fun measureIndicator() {
        mIndicators.clear()
        //临时变量记录该圆心横坐标
        var cx = 0f
        //[)左闭右开
        for (i in 0 until mCount) {
            val indicator = Indicator()
            if (i == 0) {
                //第一个圆的横坐标为半径+画笔的宽度
                cx = mRadius + mStrokeWidth.toFloat()
            } else {
                cx += (mRadius + mStrokeWidth) * 2 + mSpace.toFloat()
            }
            indicator.cx = cx
            //位于布局中间
            indicator.cy = measuredHeight / 2.toFloat()
            mIndicators.add(indicator)
        }
    }

    /**
     * 绘制所有的圆
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //可以得到所得对象的下标值i in mIndicators.indices
        for (i in mIndicators.indices) {
            val indicator = mIndicators[i]
            val x = indicator.cx
            val y = indicator.cy
            if (mSelectPosition == i) {                  //这个点为当前选择的点，设置画笔颜色和风格
                mCirclePaint.style = Paint.Style.FILL
                mCirclePaint.color = mSelectColor
            } else {                                            //设置未选中点的画笔颜色和风格
                mCirclePaint.color = mDotNormalColor
                mCirclePaint.style = Paint.Style.STROKE
            }
            canvas?.drawCircle(x, y, mRadius.toFloat(), mCirclePaint)
        }
    }


    //--------设置接口,为了回调让Activity知道，可以不写以下两个，viewpager照样切换---------------------
    interface OnIndicatorClickListener {
        fun onSelected(position: Int)
    }

    /**
     * 看自己需求，可以为小圆点专门设定监听器
     */
    fun setOnIndicatorClickListener(onIndicatorClickListener: OnIndicatorClickListener) {
        mOnIndicatorClickListener = onIndicatorClickListener
    }

    //----------------------------------------------------------------------------------------------
    //处理点击小圆点的点击事件，看点击的位置是否在小圆点内
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var xPoint = 0f
        var yPoint = 0f
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                xPoint = event.x
                yPoint = event.y
                handleActionDown(xPoint, yPoint)
            }
        }
        return super.onTouchEvent(event)
    }
    //处理点击事件
    private fun handleActionDown(xDis: Float, yDis: Float) {
        for (i in mIndicators.indices) {
            val indicator = mIndicators[i]
            //圆点的坐标是(cx,cy) 半径是mRadius， 圆边宽是mStrokeWidth
            if ( xDis >= indicator.cx - (mRadius + mStrokeWidth) && xDis < indicator.cx + mRadius + mStrokeWidth
//                && yDis >= yDis - (indicator.cy + mStrokeWidth) && yDis < indicator.cy + mRadius + mStrokeWidth
                //TODO(这里和参考的文章不同，为啥是它那样)
                && yDis >= indicator.cy - (mRadius   + mStrokeWidth) && yDis < indicator.cy + mRadius + mStrokeWidth) {
                //找到了点击的Indicator
                //切换ViewPager
                mViewPager?.setCurrentItem(i, false)
                //回调
                if (mOnIndicatorClickListener != null) {
                    mOnIndicatorClickListener!!.onSelected(i)
                }
            }
        }
    }
    //----------------------------------------------------------------------------------------------
    /**
     * 向外提供接口，设置CircleIndicatorView与ViewPager2关联
     */
     fun setUpWithViewPager(viewPager: ViewPager2) {
        //重置ViewPager
//        releaseViewPager()

        mViewPager = viewPager

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mSelectPosition = position
                invalidate()
            }
        })
        //获取真实的数值
        val count: Int = (viewPager.adapter as MyAdapter).itemCount
        setCount(count)
    }
    private fun setCount(count: Int) {
        mCount = count
        invalidate()
    }


    //获取自定义属性
    private fun getAttr(
        context: Context,
        attrs: AttributeSet
    ) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CircleIndicatorView)
        //半径(没设置则用6dp)
        mRadius = typedArray.getDimensionPixelSize(
            R.styleable.CircleIndicatorView_indicatorRadius,
            DisplayUtils.dpToPx(6)
        )
        //画笔宽度(没设置则用2dp)
        mStrokeWidth = typedArray.getDimensionPixelSize(
            R.styleable.CircleIndicatorView_indicatorBorderWidth,
            DisplayUtils.dpToPx(2)
        )
        //圆之间间距(没设置则用5dp)
        mSpace = typedArray.getDimensionPixelSize(
            R.styleable.CircleIndicatorView_indicatorSpace,
            DisplayUtils.dpToPx(5)
        )
        //圆未选中颜色
        mDotNormalColor = typedArray.getColor(
            R.styleable.CircleIndicatorView_indicatorColor,
            Color.BLACK
        )
        //圆选中颜色
        mSelectColor = typedArray.getColor(
            R.styleable.CircleIndicatorView_indicatorSelectedColor,
            Color.WHITE
        )
    }


}

