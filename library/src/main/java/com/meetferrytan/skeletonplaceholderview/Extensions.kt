package com.meetferrytan.skeletonplaceholderview

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.RectF
import android.view.View
import android.view.ViewTreeObserver

/**
 * Extension function to measure View
 *
 * @param f the function to be run after view measure, apply View's property to [Bone] inside this method
 */
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

/**
 * Extension function to find [Bone] by viewId inside a [List]
 *
 * @param viewId the viewId of the expected bone
 * @return [Bone] if exist, null if not exist
 */
internal fun List<Bone>.getBoneById(viewId: Int): Bone? {
    return run value@{
        forEach {
            when (it.viewId) {viewId -> return@value it
            }
        }
        return@value null
    }
}

/**
 * Extension function to update [Bone] inside a [MutableList]
 * @param viewId the viewId of the expected bone
 * @param updatedBone the [Bone] to replace the existing bone if found
 */
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

/**
 * Extension function to create [RectF] from [Rect].
 * Used in drawing rounded rectangle in [Canvas]
 * @see Canvas.drawRoundRect
 */
internal fun Rect.createRectF(): RectF = RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())