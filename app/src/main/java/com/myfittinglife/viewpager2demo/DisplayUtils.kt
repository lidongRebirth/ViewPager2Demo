package com.myfittinglife.viewpager2demo

import android.content.res.Resources
import android.util.TypedValue

/**
 @Author LD
 @Time 2020/11/25 13:56
 @Describe dp和px单位互转
 @Modify
*/

object DisplayUtils {
    fun dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
    }

    fun pxToDp(px: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_PX,
            px,
            Resources.getSystem().displayMetrics
        ).toInt()
    }
}