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

    @ColorInt
    var bgColor: Int = 0
    @ColorInt
    var skeletonColor: Int = 0
    var boneDefaultWidth: Int = 0
    var boneDefaultHeight: Int = 0
    var boneDefaultCornerRadius: Float = 0f
    private lateinit var mSkeletonPaint: Paint
    private lateinit var mBones: MutableList<Bone>
    private var mViewSkinned: Boolean = false;

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int = 0) {
        setWillNotDraw(false)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.SkeletonPlaceholderView, defStyleAttr, defStyleRes)

            bgColor = typedArray.getColor(R.styleable.SkeletonPlaceholderView_sk_background_color, ContextCompat.getColor(getContext(), R.color.background_default))
            skeletonColor = typedArray.getColor(R.styleable.SkeletonPlaceholderView_sk_bone_color, ContextCompat.getColor(getContext(), R.color.skeleton_default))
            boneDefaultWidth = typedArray.getDimensionPixelSize(R.styleable.SkeletonPlaceholderView_sk_bone_default_width, resources.getDimensionPixelSize(R.dimen.width_default))
            boneDefaultHeight = typedArray.getDimensionPixelSize(R.styleable.SkeletonPlaceholderView_sk_bone_default_height, resources.getDimensionPixelSize(R.dimen.height_default))
            boneDefaultCornerRadius = typedArray.getDimensionPixelSize(R.styleable.SkeletonPlaceholderView_sk_bone_corner_radius, resources.getDimensionPixelSize(R.dimen.corner_default)).toFloat()

            setBackgroundColor(bgColor)
            mSkeletonPaint = Paint()
            mSkeletonPaint.color = skeletonColor
            typedArray.recycle()
        }
    }

    //TODO create bone class to add more dynamic variables per bone
    fun <B : Bone> setView(@LayoutRes layoutRes: Int, @IdRes vararg bones: B) {
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

                        if(it.customWidth >= 0) rect.right = rect.left + it.customWidth
                        if(it.customHeight >= 0) rect.bottom = rect.top + it.customHeight

                        rect.left+=it.hSpacing
                        rect.top+=it.vSpacing
                        rect.right-=it.hSpacing
                        rect.bottom-=it.vSpacing

                        it.rect = rect
                        if (it.cornerRadius == -1f) it.cornerRadius = boneDefaultCornerRadius
                    }
                    else -> {
                        // should not be possible
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
                        // should not be possible
                    }
                }
            }
        }
    }

    private inline fun <T : View> T.afterMeasured(crossinline f: T.() -> Unit) {
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (measuredWidth > 0 && measuredHeight > 0) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    f()
                }
            }
        })
    }

    private fun List<Bone>.getBoneById(viewId: Int): Bone? {
        return run value@{
            forEach {
                when (it.viewId) {viewId -> return@value it
                }
            }
            return@value null
        }
    }

    private fun MutableList<Bone>.updateBone(viewId: Int, updatedBone: Bone) {
        val boneIndex = run value@{
            for (i in indices) {
                get(i).let {
                    if (it.viewId == viewId) return@value i
                }
            }
            return@value -1
        }

        if (boneIndex in indices) set(boneIndex, updatedBone)
    }


    private fun Rect.createRectF(): RectF = RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())

    abstract class Bone(@IdRes val viewId: Int, var customWidth: Int = -1, var customHeight: Int = -1, var hSpacing: Int = 0, var vSpacing: Int = 0)

    class CircleBone(@IdRes viewId: Int,
                     spacing: Int = 0,
                     var centerX: Float = 0f,
                     var centerY: Float = 0f,
                     var radius: Float = 0f)
        : Bone(viewId = viewId, hSpacing = spacing, vSpacing = spacing)

    class RectBone(@IdRes viewId: Int,
                   customWidth: Int = -1,
                   customHeight: Int = -1,
                   hSpacing: Int = 0,
                   vSpacing: Int = 0,
                   var rect: Rect? = null,
                   var cornerRadius: Float = -1f)
        : Bone(viewId = viewId, customWidth = customWidth, customHeight = customHeight, hSpacing = hSpacing, vSpacing = vSpacing)
}
