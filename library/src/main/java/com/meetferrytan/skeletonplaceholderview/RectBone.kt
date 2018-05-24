package com.meetferrytan.skeletonplaceholderview

import android.graphics.Rect
import android.support.annotation.IdRes

class RectBone : Bone {
    var rect: Rect? = null
    var cornerRadius: Float = -1f

    private constructor(@IdRes viewId: Int,
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
            cornerRadius = builder.cornerRadius
    )

    class Builder(@IdRes internal val viewId: Int) {
        internal var customWidth: Int = -1
        internal var customHeight: Int = -1
        internal var hSpacing: Int = 0
        internal var vSpacing: Int = 0
        internal var cornerRadius: Float = -1f

        fun customWidth(customWidth: Int) = apply { this.customWidth = customWidth }

        fun customHeight(customHeight: Int) = apply { this.customHeight = customHeight }

        fun horizontalSpacing(hSpacing: Int) = apply { this.hSpacing = hSpacing }

        fun verticalSpacing(vSpacing: Int) = apply { this.vSpacing = vSpacing }

        fun cornerRadius(cornerRadius: Float) = apply { this.cornerRadius = cornerRadius }

        fun build() = RectBone(this)

    }

}