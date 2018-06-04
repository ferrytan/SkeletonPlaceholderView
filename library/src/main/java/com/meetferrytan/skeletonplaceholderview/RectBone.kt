package com.meetferrytan.skeletonplaceholderview

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.annotation.ColorInt
import android.support.annotation.IdRes

/**
 * [Bone] with rectangle shape.
 *
 * @property rect the bone's [Rect]
 * @property cornerRadius the bone's corner radius
 */
class RectBone : Bone {
    var rect: Rect? = null
    var cornerRadius: Float = -1f

    /**
     * @constructor
     */
    private constructor(@IdRes viewId: Int,
                        customWidth: Int = -1,
                        customHeight: Int = -1,
                        hSpacing: Int = 0,
                        vSpacing: Int = 0,
                        cornerRadius: Float = -1f,
                        @ColorInt color: Int = -1)
            : super(viewId = viewId, customWidth = customWidth, customHeight = customHeight, hSpacing = hSpacing, vSpacing = vSpacing, color = color) {
        this.cornerRadius = cornerRadius
    }

    /**
     * @constructor
     */
    constructor(@IdRes viewId: Int) : super(viewId = viewId)

    /**
     * @constructor
     */
    private constructor(builder: Builder) : this(
            viewId = builder.viewId,
            customWidth = builder.customWidth,
            customHeight = builder.customHeight,
            hSpacing = builder.hSpacing,
            vSpacing = builder.vSpacing,
            cornerRadius = builder.cornerRadius,
            color = builder.color
    )

    companion object {
        inline fun build(@IdRes viewId: Int, block: CircleBone.Builder.() -> Unit) = CircleBone.Builder(viewId).apply(block).build()
    }

    /**
     * Builder Pattern for Java interopability
     */
    class Builder(@IdRes internal val viewId: Int) {
        internal var customWidth: Int = -1
        internal var customHeight: Int = -1
        internal var hSpacing: Int = 0
        internal var vSpacing: Int = 0
        internal var cornerRadius: Float = -1f
        @ColorInt
        internal var color: Int = -1

        fun customWidth(customWidth: Int) = apply { this.customWidth = customWidth }

        fun customHeight(customHeight: Int) = apply { this.customHeight = customHeight }

        fun horizontalSpacing(hSpacing: Int) = apply { this.hSpacing = hSpacing }

        fun verticalSpacing(vSpacing: Int) = apply { this.vSpacing = vSpacing }

        fun cornerRadius(cornerRadius: Float) = apply { this.cornerRadius = cornerRadius }

        fun color(@ColorInt color: Int) = apply { this.color = color }

        fun build() = RectBone(this)

    }

    override fun draw(canvas: Canvas, paint: Paint) {
        paint.color = color
        rect?.let {
            if (cornerRadius > 0) {
                canvas.drawRoundRect(it.createRectF(), cornerRadius, cornerRadius, paint)
            } else {
                canvas.drawRect(it, paint)
            }
        }
    }
}