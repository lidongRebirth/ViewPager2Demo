package com.myfittinglife.viewpager2demo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
@Author LD
@Time 2020/11/24 15:00
@Describe Viewpager2和Fragment使用
@Modify
 */
class MainActivity : AppCompatActivity(), CircleIndicatorView.OnIndicatorClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        mViewpager.offscreenPageLimit = 3
        mViewpager.adapter = MyAdapter(this)

        //是否允许滑动
//        mViewpager.isUserInputEnabled = false
//        mViewpager.currentItem = getStartItem()
        circleIndicatorView.setUpWithViewPager(mViewpager)
//        circleIndicatorView.setOnIndicatorClickListener(this)
    }

    /**
     * 用于无限滚动，初始值让它处于中间值
     */
    fun getStartItem(): Int {
        var currentItem = 500 / 2
        if (currentItem % 4 == 0) {
            return currentItem
        }
        while (currentItem % 4 != 0) {
            currentItem++
        }
        return currentItem
    }

    override fun onSelected(position: Int) {
        Toast.makeText(applicationContext, "点击的位置为:$position", Toast.LENGTH_SHORT).show()
    }
}

