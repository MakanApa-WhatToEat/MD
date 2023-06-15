package com.example.makanapa.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.makanapa.R
import com.example.makanapa.view.welcome.WelcomeActivity

class SliderAdapter(private val context: Context) : PagerAdapter() {

    private val slideLayouts = arrayOf(
        R.layout.slide_layout1,
        R.layout.slide_layout2,
        R.layout.slide_layout3
    )

    override fun getCount(): Int {
        return slideLayouts.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val slideLayout = inflater.inflate(slideLayouts[position], container, false)

        container.addView(slideLayout)
        return slideLayout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}