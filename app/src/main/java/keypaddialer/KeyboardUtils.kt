package keypaddialer

import android.annotation.SuppressLint
import android.app.Activity
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object KeyboardUtils {
    @SuppressLint("ClickableViewAccessibility")
    fun hideKeyBoardWhenClickOutSide(view: View, activity: Activity) {
        if (view !is EditText) {
            var touchDownTime: Long = 0
            view.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> touchDownTime = SystemClock.elapsedRealtime()
                    MotionEvent.ACTION_UP -> {
                        if (SystemClock.elapsedRealtime() - touchDownTime <= 150) {
                            hideSoftKeyboard(activity)
                            if (view is ViewGroup) {
                                for (i in 0 until view.childCount) {
                                    val innerView = view.getChildAt(i)
                                    innerView.clearFocus()
                                }
                            }
                        }
                    }
                }
                true
            }
        }
    }

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        activity.currentFocus?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}