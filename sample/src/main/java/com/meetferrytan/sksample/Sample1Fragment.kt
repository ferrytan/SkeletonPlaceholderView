package com.meetferrytan.sksample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_placeholder.view.*

class Sample1Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sample1, container, false)
        view.skeletonPlaceholderView.setView(R.layout.item_sample_1,
                R.id.img, R.id.title, R.id.subtitle, R.id.description)
        view.shimmer.startShimmer()
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = Sample1Fragment()
    }
}