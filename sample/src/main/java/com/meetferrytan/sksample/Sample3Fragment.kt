package com.meetferrytan.sksample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meetferrytan.skeletonplaceholderview.SkeletonPlaceholderView
import kotlinx.android.synthetic.main.item_placeholder.view.*
import kotlinx.android.synthetic.main.item_sample_3.view.*

class Sample3Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sample3, container, false)

        view.txvEpisode.text = "Episode 01 (w=100)"
        view.txvTitle.text = "The Beginning (w=200, vs=1)"
        view.txvDate.text = "18 May 2018(w=300, vs=1)"
        view.txvLikeCount.text = "9k Likes"

        view.skeletonPlaceholderView.setView(R.layout.item_sample_3,
                SkeletonPlaceholderView.RectBone(R.id.imgThumb),
                SkeletonPlaceholderView.RectBone(R.id.txvEpisode, customWidth = 100, vSpacing = resources.getDimensionPixelSize(R.dimen.spacing1dp)),
                SkeletonPlaceholderView.RectBone(R.id.txvTitle, customWidth = 200, vSpacing = resources.getDimensionPixelSize(R.dimen.spacing1dp)),
                SkeletonPlaceholderView.RectBone(R.id.txvDate, customWidth = 300, vSpacing = resources.getDimensionPixelSize(R.dimen.spacing1dp)))
        view.shimmer.startShimmer()

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = Sample3Fragment()
    }
}
