package com.meetferrytan.skeletonplaceholderview

import android.support.annotation.IdRes

class CircleBone : Bone {
    var centerX: Float = 0f
    var centerY: Float = 0f
    var radius: Float = 0f

    constructor(@IdRes viewId: Int,
                spacing: Int = 0,
                centerX: Float = 0f,
                centerY: Float = 0f,
                radius: Float = 0f)
            : super(viewId = viewId, hSpacing = spacing, vSpacing = spacing) {
        this.centerX = centerX
        this.centerY = centerY
        this.radius = radius
    }

    constructor(@IdRes viewId: Int) : super(viewId = viewId)

    // Builder Pattern for Java interopability
    private constructor(builder: Builder) : this(
            viewId = builder.viewId,
            spacing = builder.spacing,
            centerX = builder.centerX,
            centerY = builder.centerY,
            radius = builder.radius
    )

    companion object {
        inline fun build(@IdRes viewId: Int, block: Builder.() -> Unit) = Builder(viewId).apply(block).build()
    }

    class Builder (@IdRes val viewId: Int){

        var spacing: Int = 0
            private set
        var centerX: Float = 0f
            private set
        var centerY: Float = 0f
            private set
        var radius: Float = 0f
            private set

        fun spacing(spacing: Int) = apply { this.spacing = spacing }

        fun centerX(centerX: Float) = apply { this.centerX = centerX }

        fun centerY(centerY: Float) = apply { this.centerY = centerY }

        fun radius(radius: Float) = apply { this.radius = radius }

        fun build() = CircleBone(this)

    }

}