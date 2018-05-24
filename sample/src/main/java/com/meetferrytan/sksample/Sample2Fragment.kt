package com.meetferrytan.sksample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_placeholder.view.*
import kotlinx.android.synthetic.main.item_sample_2.view.*

class Sample2Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sample2, container, false)
        view.txtGenre.text = "Romance"
        view.txtTitle.text = "Heartbeat"
        view.txtSubscribeCount.text = "12k Subscribers"

        view.skeletonPlaceholderView.skinView(R.layout.item_sample_2,
                R.id.imgCover,
                R.id.txtGenre,
                R.id.txtTitle,
                R.id.frmSubscribeCount)
        view.shimmer.startShimmer()

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = Sample2Fragment()
    }
}
