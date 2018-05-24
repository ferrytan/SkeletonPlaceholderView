package com.meetferrytan.sksample;

import android.content.Context;

import com.meetferrytan.skeletonplaceholderview.CircleBone;
import com.meetferrytan.skeletonplaceholderview.RectBone;
import com.meetferrytan.skeletonplaceholderview.SkeletonPlaceholderView;

public class JavaImplementationSample {

    private CircleBone circleBone() {
        return new CircleBone(R.id.imgCover);
    }

    private CircleBone circleBoneByBuilder() {
        return new CircleBone.Builder(R.id.imgCover)
                .spacing(0)
                .build();
    }

    private RectBone rectBone() {
        return new RectBone(R.id.txtGenre);
    }

    private RectBone rectBoneByBuilder() {
        return new RectBone.Builder(R.id.txtGenre)
                .cornerRadius(4f)
                .customHeight(200)
                .customWidth(100)
                .horizontalSpacing(2)
                .verticalSpacing(4)
                .build();
    }

    private void skeletonPlaceholderView(Context context) {
        SkeletonPlaceholderView skeletonPlaceholderView = new SkeletonPlaceholderView(context);

        skeletonPlaceholderView.skinView(R.layout.item_sample_2,
                R.id.imgCover,
                R.id.txtGenre,
                R.id.txtTitle,
                R.id.frmSubscribeCount);

        // OR

        skeletonPlaceholderView.skinView(R.layout.item_sample_2,
                circleBone(),
                rectBone(),
                circleBoneByBuilder(),
                rectBoneByBuilder());

        // OR

        skeletonPlaceholderView.skinView(R.layout.item_sample_2,
                new CircleBone(R.id.imgCover),
                new RectBone.Builder(R.id.txtGenre)
                        .customWidth(200)
                        .customHeight(50)
                        .build());
    }
}
