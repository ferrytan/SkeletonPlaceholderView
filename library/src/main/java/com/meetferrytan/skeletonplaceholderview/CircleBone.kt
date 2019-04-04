package com.meetferrytan.skeletonplaceholderview

import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.ColorInt
import androidx.annotation.IdRes

/**
 * [Bone] with circle shape.
 *
 * @property centerX the bone's center x coordinate
 * @property centerY the bone's center x coordinate
 * @property radius length from the bone's center to its perimeter
 */
class CircleBone : Bone {
    var centerX: Float = 0f
    var centerY: Float = 0f
    var radius: Float = 0f

    /**
     * @constructor
     */
    constructor(@IdRes viewId: Int,
                        spacing: Int = 0,
                        centerX: Float = 0f,
                        centerY: Float = 0f,
                        radius: Float = 0f,
                        @ColorInt color: Int = -1)
            : super(viewId = viewId, hSpacing = spacing, vSpacing = spacing, color = color) {
        this.centerX = centerX
        this.centerY = centerY
        this.radius = radius
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
            spacing = builder.spacing,
            color = builder.color
    )

    companion object {
        inline fun build(@IdRes viewId: Int, block: Builder.() -> Unit) = Builder(viewId).apply(block).build()
    }

    /**
     * Builder Pattern for Java interopability
     */
    class Builder(@IdRes internal val viewId: Int) {

        internal var spacing: Int = 0
        @ColorInt
        internal var color: Int = -1

        fun spacing(spacing: Int) = apply { this.spacing = spacing }

        fun color(@ColorInt color: Int) = apply { this.color = color }

        fun build() = CircleBone(this)

    }

    override fun draw(canvas: Canvas, paint: Paint) {
        paint.color = color
        canvas.drawCircle(centerX, centerY, radius, paint)
    }
}