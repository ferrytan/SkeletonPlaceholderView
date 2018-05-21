package com.meetferrytan.sksample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_placeholder.view.*
import kotlinx.android.synthetic.main.item_sample_4.view.*

class Sample4Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sample4, container, false)

        view.txvName.text = "John Doe"
        view.txvDate.text = "1 min ago"
        view.txvCommentText.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
        view.txvDislike.text = "23"
        view.txvLike.text = "1k"
        view.txvReplies.text = "120 Replies"
        view.skeletonPlaceholderView.setView(R.layout.item_sample_4,
                R.id.imgProfilePicture, R.id.txvName, R.id.txvLabelAuthor, R.id.txvCommentText)
        view.shimmer.startShimmer()

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = Sample4Fragment()
    }
}