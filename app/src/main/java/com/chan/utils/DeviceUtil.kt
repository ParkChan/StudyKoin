package com.chan.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

fun getScreenWidth(context: Context): Int {
    val windowManager =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(dm)
    return dm.widthPixels
}