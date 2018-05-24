package com.meetferrytan.skeletonplaceholderview

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.TextView

/**
 * A Library designed to draw a Skeleton by "skinning" the view from a provided layout.
 * Skeleton is composed of [Bone] with different properties, usage defines which bones to be drawn before the view is skinned
 * It is usually used as a Placeholder while loading a data before it is populated to a View.
 *
 * @property rootBackgroundColor the skeleton's root background color
 * @property boneColor the bone's color
 * @property boneDefaultWidth the bone's default width if it has no width to be measured
 * @property boneDefaultHeight the bone's default width if it has no height to be measured
 * @property boneDefaultCornerRadius the bone's default corner radius for [RectBone]
 * @property mBonePaint the paint to draw the Skeleton's bones
 * @property mBones list of the bones to be drawn inside the Skeleton
 * @property mViewSkinned the indicator flag to define whether the View is already skinned to a skeleton
 *
 * @author ferrytan
 * @version 0.2.2, 24 May 2018
 */
class SkeletonPlaceholderView : FrameLayout {

    /**
     * @constructor
     */
    @JvmOverloads
    constructor(
            context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = 0)
            : super(context, attrs, defStyleAttr) {
        init(context = context, attrs = attrs, defStyleAttr = defStyleAttr)
    }

    /**
     * @constructor
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
            context: Context,
            attrs: AttributeSet?,
            defStyleAttr: Int,
            defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs, defStyleAttr, defStyleRes)
    }

    @ColorInt
    var rootBackgroundColor: Int = 0
    @ColorInt
    var boneColor: Int = 0
    var boneDefaultWidth: Int = 0
    var boneDefaultHeight: Int = 0
    var boneDefaultCornerRadius: Float = 0f
    private lateinit var mBonePaint: Paint
    private lateinit var mBones: MutableList<Bone>
    private var mViewSkinned: Boolean = false

    /**
     * Initialize method to define properties by attribute, or set it to defaults
     */
    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int = 0) {
        setWillNotDraw(false)

        val defRootBackgroundColor = ContextCompat.getColor(getContext(), R.color.background_default)
        val defBoneColor = ContextCompat.getColor(getContext(), R.color.bone_default)
        val defBoneDefaultWidth = resources.getDimensionPixelSize(R.dimen.width_default)
        val defBoneDefaultHeight = resources.getDimensionPixelSize(R.dimen.height_default)
        val defBoneDefaultCornerRadius = resources.getDimensionPixelSize(R.dimen.corner_default)

        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkeletonPlaceholderView, defStyleAttr, defStyleRes)
            try {
                rootBackgroundColor = typedArray.getColor(R.styleable.SkeletonPlaceholderView_sk_background_color, defRootBackgroundColor)
                boneColor = typedArray.getColor(R.styleable.SkeletonPlaceholderView_sk_bone_color, defBoneColor)
                boneDefaultWidth = typedArray.getDimensionPixelSize(R.styleable.SkeletonPlaceholderView_sk_bone_default_width, defBoneDefaultWidth)
                boneDefaultHeight = typedArray.getDimensionPixelSize(R.styleable.SkeletonPlaceholderView_sk_bone_default_height, defBoneDefaultHeight)
                boneDefaultCornerRadius = typedArray.getDimensionPixelSize(R.styleable.SkeletonPlaceholderView_sk_bone_corner_radius, defBoneDefaultCornerRadius).toFloat()
            } finally {
                typedArray.recycle()
            }
        } else {
            rootBackgroundColor = defRootBackgroundColor
            boneColor = defBoneColor
            boneDefaultWidth = defBoneDefaultWidth
            boneDefaultHeight = defBoneDefaultHeight
            boneDefaultCornerRadius = defBoneDefaultCornerRadius.toFloat()
        }

        mBonePaint = Paint()
        mBonePaint.color = boneColor
        setBackgroundColor(rootBackgroundColor)
    }

    /**
     * Core method to skin the bones out of a View, inflated by it's LayoutRes
     *
     * @param layoutRes the layout resource of the View
     * @param bones array of the bones to be drawn after the view is skinned
     */
    fun <B : Bone> skinView(@LayoutRes layoutRes: Int, vararg bones: B) {
        mViewSkinned = false
        mBones = bones.toMutableList()
        val view = LayoutInflater.from(context).inflate(layoutRes, this, false)
        addView(view)
        skeletonize(view)

        view.afterMeasured {
            removeView(view)
            updateSkeletonSize(view.width, view.height)
        }
    }

    /**
     * Alternative method to skin the boneViewIds.
     * Provides simple [Bone] creation (as [RectBone] for simple usage of this custom view
     *
     * @param layoutRes the layout resource of the View
     * @param boneViewIds the array of ids of the child views inside the View to be drawn after the view is skinned
     */
    fun skinView(@LayoutRes layoutRes: Int, @IdRes vararg boneViewIds: Int) {
        val defaultBones: Array<Bone> = boneViewIds.map { RectBone(it) }.toTypedArray()
        skinView(layoutRes, *defaultBones)
    }

    /**
     * Update skeleton size by the assigned view's width & height
     */
    private fun updateSkeletonSize(width: Int, height: Int) {
        mViewSkinned = true
        layoutParams.width = width
        layoutParams.height = height
        setLayoutParams(layoutParams)
    }

    /**
     * Skeletonize a View, and its child views if it's an instance of [ViewGroup]
     * Skeletonize here means "cleaning" and update the bones before it's ready to be drawn to the skeleton
     */
    private fun skeletonize(view: View) {
        view.setBackgroundResource(R.color.transparent)
        val bone = mBones.getBoneById(view.id)
        bone?.let {
            val isWrappedTextView = view is TextView && view.text == "" && view.layoutParams.width == WRAP_CONTENT

            if (isWrappedTextView) view.layoutParams.let {
                it.width = boneDefaultWidth
                view.setLayoutParams(it)
            }

            view.afterMeasured {
                val rect = Rect()
                view.getGlobalVisibleRect(rect)

                when (it) {
                    is CircleBone -> {
                        it.centerX = (rect.left + (rect.right - rect.left) / 2).toFloat()
                        it.centerY = (rect.top + (rect.bottom - rect.top) / 2).toFloat()

                        val rectWidth = rect.right - rect.left
                        val rectHeight = rect.bottom - rect.top
                        it.radius = minOf(rectWidth, rectHeight).toFloat() / 2 - it.hSpacing
                    }
                    is RectBone -> {

                        if (it.customWidth >= 0) rect.right = rect.left + it.customWidth
                        if (it.customHeight >= 0) rect.bottom = rect.top + it.customHeight

                        rect.left += it.hSpacing
                        rect.top += it.vSpacing
                        rect.right -= it.hSpacing
                        rect.bottom -= it.vSpacing

                        it.rect = rect
                        if (it.cornerRadius == -1f) it.cornerRadius = boneDefaultCornerRadius
                    }
                    else -> {
                        // Unhandled type
                    }
                }
                mBones.updateBone(view.id, it)
            }
        }

        if (view is ViewGroup) {
            for (i in 0..view.childCount) {
                view.getChildAt(i)?.let { skeletonize(it) }
            }
        }
    }

    /**
     * The actual skeleton shape is called here, after the view is skinned
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (mViewSkinned) {
            mBones.forEach {
                when (it) {
                    is CircleBone -> canvas?.drawCircle(it.centerX, it.centerY, it.radius, mBonePaint)
                    is RectBone -> canvas?.drawRoundRect(it.rect?.createRectF(), it.cornerRadius, it.cornerRadius, mBonePaint)
                    else -> {
                        // Unhandled type
                    }
                }
            }
        }
    }
}