package com.meetferrytan.sksample

import android.content.Context
import com.meetferrytan.skeletonplaceholderview.CircleBone
import com.meetferrytan.skeletonplaceholderview.RectBone
import com.meetferrytan.skeletonplaceholderview.SkeletonPlaceholderView

private fun circleBone(): CircleBone {
    return CircleBone(R.id.imgCover)
}

private fun circleBoneByBuilder(): CircleBone {
    return CircleBone.Builder(R.id.imgCover)
            .spacing(0)
            .build()
}

private fun circleBoneByBuilderDsl(): CircleBone {
    return CircleBone.Builder(R.id.imgCover).apply {
        spacing(100)
    }.build()
}

private fun rectBone(): RectBone {
    return RectBone(R.id.txtGenre)
}

private fun rectBoneByBuilder(): RectBone {
    return RectBone.Builder(R.id.txtGenre)
            .cornerRadius(4f)
            .customHeight(200)
            .customWidth(100)
            .horizontalSpacing(2)
            .verticalSpacing(4)
            .build()
}

private fun rectBoneByBuilderDsl(): RectBone {
    return RectBone.Builder(R.id.txtGenre)
            .apply {
                cornerRadius(4f)
                customHeight(200)
                customWidth(100)
                horizontalSpacing(2)
                verticalSpacing(4)
            }.build()
}

private fun skeletonPlaceholderView(context: Context) {
    val skeletonPlaceholderView = SkeletonPlaceholderView(context)

    skeletonPlaceholderView.setView(R.layout.item_sample_2,
            R.id.imgCover,
            R.id.txtGenre,
            R.id.txtTitle,
            R.id.frmSubscribeCount)

    // OR

    skeletonPlaceholderView.setView(R.layout.item_sample_2,
            circleBone(),
            rectBone(),
            circleBoneByBuilder(),
            rectBoneByBuilder())

    // OR

    skeletonPlaceholderView.setView(R.layout.item_sample_2,
            CircleBone(R.id.imgCover),
            RectBone.Builder(R.id.txtGenre)
                    .customWidth(200)
                    .customHeight(50)
                    .build(),
            CircleBone.Builder(R.id.imgCover)
                    .apply {
                        spacing(4)
                    }.build())
}