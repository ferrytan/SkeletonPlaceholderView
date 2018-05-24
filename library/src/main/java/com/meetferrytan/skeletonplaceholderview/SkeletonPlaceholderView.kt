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

//TODO add documentation
class SkeletonPlaceholderView : FrameLayout {
    @JvmOverloads
    constructor(
            context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = 0)
            : super(context, attrs, defStyleAttr) {
        init(context = context, attrs = attrs, defStyleAttr = defStyleAttr)
    }

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
    var skeletonColor: Int = 0
    var boneDefaultWidth: Int = 0
    var boneDefaultHeight: Int = 0
    var boneDefaultCornerRadius: Float = 0f
    private lateinit var mSkeletonPaint: Paint
    private lateinit var mBones: MutableList<Bone>
    private var mViewSkinned: Boolean = false

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int = 0) {
        setWillNotDraw(false)

        val defRootBackgroundColor = ContextCompat.getColor(getContext(), R.color.background_default)
        val defSkeletonColor = ContextCompat.getColor(getContext(), R.color.skeleton_default)
        val defBoneDefaultWidth = resources.getDimensionPixelSize(R.dimen.width_default)
        val defBoneDefaultHeight = resources.getDimensionPixelSize(R.dimen.height_default)
        val defBoneDefaultCornerRadius = resources.getDimensionPixelSize(R.dimen.corner_default)

        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkeletonPlaceholderView, defStyleAttr, defStyleRes)
            try {
                rootBackgroundColor = typedArray.getColor(R.styleable.SkeletonPlaceholderView_sk_background_color, defRootBackgroundColor)
                skeletonColor = typedArray.getColor(R.styleable.SkeletonPlaceholderView_sk_bone_color, defSkeletonColor)
                boneDefaultWidth = typedArray.getDimensionPixelSize(R.styleable.SkeletonPlaceholderView_sk_bone_default_width, defBoneDefaultWidth)
                boneDefaultHeight = typedArray.getDimensionPixelSize(R.styleable.SkeletonPlaceholderView_sk_bone_default_height, defBoneDefaultHeight)
                boneDefaultCornerRadius = typedArray.getDimensionPixelSize(R.styleable.SkeletonPlaceholderView_sk_bone_corner_radius, defBoneDefaultCornerRadius).toFloat()
            } finally {
                typedArray.recycle()
            }
        } else {
            rootBackgroundColor = defRootBackgroundColor
            skeletonColor = defSkeletonColor
            boneDefaultWidth = defBoneDefaultWidth
            boneDefaultHeight = defBoneDefaultHeight
            boneDefaultCornerRadius = defBoneDefaultCornerRadius.toFloat()
        }

        mSkeletonPaint = Paint()
        mSkeletonPaint.color = skeletonColor
        setBackgroundColor(rootBackgroundColor)
    }

    fun <B : Bone> setView(@LayoutRes layoutRes: Int, vararg bones: B) {
        mBones = bones.toMutableList()
        val view = LayoutInflater.from(context).inflate(layoutRes, this, false)
        addView(view)
        skeletonize(view)

        view.afterMeasured {
            removeView(view)
            mViewSkinned = true
            updatePlaceholderSize(view.width, view.height)
        }
    }

    fun setView(@LayoutRes layoutRes: Int, @IdRes vararg bones: Int) {
        val defaultBones: Array<Bone> = bones.map { RectBone(it) }.toTypedArray()
        setView(layoutRes, *defaultBones)
    }

    private fun updatePlaceholderSize(width: Int, height: Int) {
        layoutParams.width = width
        layoutParams.height = height
        setLayoutParams(layoutParams)
    }

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

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (mViewSkinned) {
            mBones.forEach {
                when (it) {
                    is CircleBone -> canvas?.drawCircle(it.centerX, it.centerY, it.radius, mSkeletonPaint)
                    is RectBone -> canvas?.drawRoundRect(it.rect?.createRectF(), it.cornerRadius, it.cornerRadius, mSkeletonPaint)
                    else -> {
                        // Unhandled type
                    }
                }
            }
        }
    }
}