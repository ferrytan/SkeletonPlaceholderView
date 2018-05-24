package com.meetferrytan.skeletonplaceholderview

import android.support.annotation.IdRes

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
    private constructor(@IdRes viewId: Int,
                spacing: Int = 0,
                centerX: Float = 0f,
                centerY: Float = 0f,
                radius: Float = 0f)
            : super(viewId = viewId, hSpacing = spacing, vSpacing = spacing) {
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
            spacing = builder.spacing
    )

    companion object {
        inline fun build(@IdRes viewId: Int, block: Builder.() -> Unit) = Builder(viewId).apply(block).build()
    }

    /**
     * Builder Pattern for Java interopability
     */
    class Builder (@IdRes internal val viewId: Int){

        internal var spacing: Int = 0

        fun spacing(spacing: Int) = apply { this.spacing = spacing }

        fun build() = CircleBone(this)

    }

}