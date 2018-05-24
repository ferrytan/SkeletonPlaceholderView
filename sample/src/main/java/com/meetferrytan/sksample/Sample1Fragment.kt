package com.meetferrytan.sksample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meetferrytan.skeletonplaceholderview.RectBone
import kotlinx.android.synthetic.main.item_placeholder.view.*
import kotlinx.android.synthetic.main.item_sample_1.view.*

class Sample1Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sample1, container, false)
        view.title.text = "Title (cornerRadius=2dp)"
        view.subtitle.text = "Subtitle (cornerRadius=4dp)"
        view.description.text = "Description (cornerRadius=8dp, customHeight=100)"

        view.skeletonPlaceholderView.setView(R.layout.item_sample_1,
                RectBone(R.id.img),
                RectBone.Builder(R.id.title)
                        .cornerRadius(resources.getDimensionPixelSize(R.dimen.radius2dp).toFloat())
                        .build(),
                RectBone.Builder(R.id.subtitle)
                        .cornerRadius(resources.getDimensionPixelSize(R.dimen.radius4dp).toFloat())
                        .build(),
                RectBone.Builder(R.id.description)
                        .cornerRadius(resources.getDimensionPixelSize(R.dimen.radius8dp).toFloat())
                        .build())
        view.shimmer.startShimmer()

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = Sample1Fragment()
    }
}