package com.meetferrytan.skeletonplaceholderview

import android.graphics.Rect
import android.graphics.RectF
import android.view.View
import android.view.ViewTreeObserver


internal inline fun <T : View> T.afterMeasured(crossinline f: T.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                f()
            }
        }
    })
}

internal fun List<Bone>.getBoneById(viewId: Int): Bone? {
    return run value@{
        forEach {
            when (it.viewId) {viewId -> return@value it
            }
        }
        return@value null
    }
}

internal fun MutableList<Bone>.updateBone(viewId: Int, updatedBone: Bone) {
    val boneIndex = run value@{
        for (i in indices) {
            get(i).let {
                if (it.viewId == viewId) return@value i
            }
        }
        return@value -1
    }

    if (boneIndex in indices) set(boneIndex, updatedBone)
}

internal fun Rect.createRectF(): RectF = RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())