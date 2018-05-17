package com.meetferrytan.sksample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSample1.setOnClickListener(this)
        btnSample2.setOnClickListener(this)
        btnSample3.setOnClickListener(this)
        btnSample4.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val itemId = v?.id

        val transaction = supportFragmentManager.beginTransaction()
        when(itemId){
            R.id.btnSample1 -> transaction.replace(R.id.fragmentContainer, Sample1Fragment.newInstance())
            R.id.btnSample2 -> transaction.replace(R.id.fragmentContainer, Sample2Fragment.newInstance())
            R.id.btnSample3 -> transaction.replace(R.id.fragmentContainer, Sample3Fragment.newInstance())
            R.id.btnSample4 -> transaction.replace(R.id.fragmentContainer, Sample4Fragment.newInstance())
        }

        transaction.commit()
    }

}