package com.meetferrytan.skeletonplaceholderview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.facebook.shimmer.ShimmerFrameLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (findViewById(R.id.skeletonPlaceholderView) as SkeletonPlaceholderView).setView(R.layout.item_sample, R.id.img, R.id.title, R.id.description)
        (findViewById(R.id.shimmer) as ShimmerFrameLayout).startShimmerAnimation()
    }
}
