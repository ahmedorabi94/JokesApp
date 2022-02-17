package com.ahmedorabi.jokesapp.features.utils

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlin.math.abs

class CustomSwipeToRefresh constructor(
    context: Context,
    attributes: AttributeSet
) :
    SwipeRefreshLayout(context, attributes) {

    private var mTouchSlop: Int = ViewConfiguration.get(context).scaledTouchSlop
    private var mPrevX: Float = 0.0f


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {

        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                mPrevX = MotionEvent.obtain(ev).x
            }
            MotionEvent.ACTION_MOVE -> {
                val eventX = ev.x
                val xDiff = abs(eventX - mPrevX)
                if (xDiff > mTouchSlop) {
                    return false
                }
            }
        }

        return super.onInterceptTouchEvent(ev)
    }

}