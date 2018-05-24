package com.meetferrytan.skeletonplaceholderview

import android.graphics.Rect
import android.support.annotation.IdRes

class RectBone : Bone {
    var rect: Rect? = null
    var cornerRadius: Float = -1f

    constructor(@IdRes viewId: Int,
                customWidth: Int = -1,
                customHeight: Int = -1,
                hSpacing: Int = 0,
                vSpacing: Int = 0,
                rect: Rect? = null,
                cornerRadius: Float = -1f)
            : super(viewId = viewId, customWidth = customWidth, customHeight = customHeight, hSpacing = hSpacing, vSpacing = vSpacing) {
        this.rect = rect
        this.cornerRadius = cornerRadius
    }

    constructor(@IdRes viewId: Int) : super(viewId = viewId)

    companion object {
        inline fun build(@IdRes viewId: Int, block: CircleBone.Builder.() -> Unit) = CircleBone.Builder(viewId).apply(block).build()
    }

    // Builder Pattern for Java interopability
    private constructor(builder: Builder) : this(
            viewId = builder.viewId,
            customWidth = builder.customWidth,
            customHeight = builder.customHeight,
            hSpacing = builder.hSpacing,
            vSpacing = builder.vSpacing,
            rect = builder.rect,
            cornerRadius = builder.cornerRadius
    )

    class Builder(@IdRes val viewId: Int) {
        var customWidth: Int = -1
            private set
        var customHeight: Int = -1
            private set
        var hSpacing: Int = 0
            private set
        var vSpacing: Int = 0
            private set
        var rect: Rect? = null
            private set
        var cornerRadius: Float = -1f
            private set

        fun customWidth(customWidth: Int) = apply { this.customWidth = customWidth }

        fun customHeight(customHeight: Int) = apply { this.customHeight = customHeight }

        fun hSpacing(hSpacing: Int) = apply { this.hSpacing = hSpacing }

        fun vSpacing(vSpacing: Int) = apply { this.vSpacing = vSpacing }

        fun rect(rect: Rect?) = apply { this.rect = rect }

        fun cornerRadius(cornerRadius: Float) = apply { this.cornerRadius = cornerRadius }

        fun build() = RectBone(this)

    }

}