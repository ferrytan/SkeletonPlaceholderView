package com.meetferrytan.sksample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_placeholder.view.*
import kotlinx.android.synthetic.main.item_sample_3.view.*

class Sample3Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sample3, container, false)

        view.txvEpisode.text = "Episode 01"
        view.txvTitle.text = "The Beginning"
        view.txvDate.text = "18 May 2018"
        view.txvLikeCount.text = "9k Likes"

        view.skeletonPlaceholderView.setView(R.layout.item_sample_3,
                R.id.imgThumb, R.id.txvEpisode, R.id.txvTitle, R.id.txvDate)
        view.shimmer.startShimmer()

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = Sample3Fragment()
    }
}
