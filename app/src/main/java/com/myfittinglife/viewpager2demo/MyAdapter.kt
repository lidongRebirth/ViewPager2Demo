package com.myfittinglife.viewpager2demo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @Author LD
 * @Time 2020/11/24 15:06
 * @Describe Fragment的适配器（无限滚动暂时实现不了）
 * @Modify
 */
class MyAdapter(fragment:FragmentActivity): FragmentStateAdapter(fragment) {

    var fragments = mutableListOf<Fragment>()
    val mLooperCount = 500

    //创建Fragment
    init {
        fragments.add(MyFragment.newInstance("1"))
        fragments.add(MyFragment.newInstance("2"))
        fragments.add(MyFragment.newInstance("3"))
        fragments.add(MyFragment.newInstance("4"))

    }

    override fun getItemCount(): Int {
        return fragments.size
//        return mLooperCount
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
//        var realPosition = position % fragments.size
//        return fragments[realPosition]
    }
}