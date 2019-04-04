package com.meetferrytan.skeletonplaceholderview

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.RectF
/*
*//**
 * Taken from android-ktx
 * Performs the given action when this view is next laid out.
 *//*
inline fun View.doOnNextLayout(crossinline action: (view: View) -> Unit) {
    addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
        override fun onLayoutChange(
                view: View,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
        ) {
            view.removeOnLayoutChangeListener(this)
            action(view)
        }
    })
}

*//**
 * Taken from android-ktx
 * Executes [block] with the View's layoutParams and reassigns the layoutParams with the
 * updated version.
 *
 * @see View.getLayoutParams
 * @see View.setLayoutParams
 **//*
inline fun View.updateLayoutParams(block: ViewGroup.LayoutParams.() -> Unit) {
    updateLayoutParams<ViewGroup.LayoutParams>(block)
}

*//**
 * Taken from android-ktx
 * Executes [block] with a typed version of the View's layoutParams and reassigns the
 * layoutParams with the updated version.
 *
 * @see View.getLayoutParams
 * @see View.setLayoutParams
 **//*

@JvmName("updateLayoutParamsTyped")
inline fun <reified T : ViewGroup.LayoutParams> View.updateLayoutParams(block: T.() -> Unit) {
    val params = layoutParams as T
    block(params)
    layoutParams = params
}*/

/**
 * Extension function to find [Bone] by viewId inside a [List]
 *
 * @param viewId the viewId of the expected bone
 * @return [Bone] if exist, null if not exist
 */
internal fun MutableList<Bone>.getBoneById(viewId: Int): Bone? {
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