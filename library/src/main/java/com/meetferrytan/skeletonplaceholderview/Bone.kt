package com.meetferrytan.skeletonplaceholderview

import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.ColorInt
import androidx.annotation.IdRes

/**
 * Base Bone class.
 *
 * @property viewId id reference of the View to be drawn as a bone
 * @property customWidth custom bone width, by default (-1) uses the view's original width OR [SkeletonPlaceholderView.boneDefaultWidth]
 * @property customWidth custom bone height, by default (-1) uses the view's original height [SkeletonPlaceholderView.boneDefaultHeight]
 * @property hSpacing horizontal spacing inside of the bone
 * @property vSpacing vertical spacing inside of the bone
 * @property color color of the bone, by default (-1) uses the default color attribute [SkeletonPlaceholderView.boneDefaultColor]
 * @constructor creates bone with default properties
 */
abstract class Bone(@IdRes val viewId: Int, var customWidth: Int = -1, var customHeight: Int = -1, var hSpacing: Int = 0, var vSpacing: Int = 0, @ColorInt var color: Int = -1) {
    /**
     * Draw the bone to the canvas
     * @param canvas the canvas to draw the bone
     * @param paint the paint to draw on the canvas
     */
    abstract fun draw(canvas: Canvas, paint: Paint)
}