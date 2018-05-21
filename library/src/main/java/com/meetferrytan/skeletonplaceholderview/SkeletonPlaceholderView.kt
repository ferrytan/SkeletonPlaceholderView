package com.meetferrytan.skeletonplaceholderview

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
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
import android.view.ViewTreeObserver
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

    private lateinit var mBoneIds: IntArray
    @ColorInt
    var bgColor: Int = 0
    @ColorInt
    var skeletonColor: Int = 0
    var boneDefaultWidth: Int = 0
    var boneDefaultHeight: Int = 0
    var boneCornerRadius: Float = 0f
    private lateinit var mSkeletonPaint: Paint
    private lateinit var mBones: List<Rect>
    private var textBoneCount: Int = 0

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int = 0) {
        setWillNotDraw(false)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.SkeletonPlaceholderView, defStyleAttr, defStyleRes)

            bgColor = typedArray.getColor(R.styleable.SkeletonPlaceholderView_sk_background_color, ContextCompat.getColor(getContext(), R.color.background_default))
            skeletonColor = typedArray.getColor(R.styleable.SkeletonPlaceholderView_sk_bone_color, ContextCompat.getColor(getContext(), R.color.skeleton_default))
            boneDefaultWidth = typedArray.getDimensionPixelSize(R.styleable.SkeletonPlaceholderView_sk_bone_default_width, resources.getDimensionPixelSize(R.dimen.width_default))
            boneDefaultHeight = typedArray.getDimensionPixelSize(R.styleable.SkeletonPlaceholderView_sk_bone_default_height, resources.getDimensionPixelSize(R.dimen.height_default))
            boneCornerRadius = typedArray.getDimensionPixelSize(R.styleable.SkeletonPlaceholderView_sk_bone_corner_radius, resources.getDimensionPixelSize(R.dimen.corner_default)).toFloat()

            setBackgroundColor(bgColor)
            mSkeletonPaint = Paint()
            mSkeletonPaint.color = skeletonColor
            typedArray.recycle()
        }
    }

    //TODO create bone class to add more dynamic variables per bone
    fun setView(@LayoutRes layoutRes: Int, @IdRes vararg boneIds: Int) {
        this.mBoneIds = boneIds

        val view = LayoutInflater.from(context).inflate(layoutRes, this, false)
        addView(view)
        skeletonize(view)

        view.afterMeasured {
            updatePlaceholderSize(view.width, view.height)
            removeView(view)
        }
    }

    private fun updatePlaceholderSize(width: Int, height: Int) {
        layoutParams.width = width
        layoutParams.height = height
        setLayoutParams(layoutParams)
    }

    private fun skeletonize(view: View) {
        mBones = ArrayList()
        if (view.id in mBoneIds) {
            val isWrappedTextView = view is TextView && view.text == "" && view.layoutParams.width == WRAP_CONTENT

            if (isWrappedTextView) view.layoutParams.let {
                // TODO Add logic to set higher width on next lines
//                textBoneCount++
                it.width = boneDefaultWidth * 1 // * textBoneCount
                view.setLayoutParams(it)
            }

            view.afterMeasured {
                val rect = Rect()
                view.getGlobalVisibleRect(rect)
                mBones += rect
            }
        } else {
            view.setBackgroundResource(R.color.transparent)
        }

        if (view is ViewGroup) {
            for (i in 0..view.childCount) {
                view.getChildAt(i)?.let { skeletonize(it) }
            }
        } else {
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mBones.forEach {
            canvas?.drawRoundRect(it.createRectF(), boneCornerRadius, boneCornerRadius, mSkeletonPaint)
        }
    }

    inline fun <T : View> T.afterMeasured(crossinline f: T.() -> Unit) {
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (measuredWidth > 0 && measuredHeight > 0) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    f()
                }
            }
        })
    }

    fun Rect.createRectF() : RectF{
        return RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
    }
}
