package com.example.makanapa.view.welcome

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.makanapa.R
import com.example.makanapa.adapter.SliderAdapter


class SliderActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var indicatorContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slider)

        supportActionBar?.hide()
        viewPager = findViewById(R.id.viewPager)
        indicatorContainer = findViewById(R.id.indicatorContainer)

        setupSlider()
        setupIndicator()

        val btnNext = findViewById<Button>(R.id.btnNext)
        val btnSkip = findViewById<Button>(R.id.btnSkip)

        btnNext.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem < sliderAdapter.count - 1) {
                viewPager.currentItem = currentItem + 1
            } else {
                startWelcomeActivity()
            }
        }

        btnSkip.setOnClickListener {
            startWelcomeActivity()
        }
    }

    private fun setupSlider() {
        sliderAdapter = SliderAdapter(this)
        viewPager.adapter = sliderAdapter

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                // Not needed for the manual indicator
            }

            override fun onPageSelected(position: Int) {
                updateIndicator(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                // Not needed for the manual indicator
            }
        })
    }

    private fun setupIndicator() {
        val numSlides = sliderAdapter.count
        for (i in 0 until numSlides) {
            val indicator = View(this)
            val size = resources.getDimensionPixelSize(R.dimen.indicator_size)
            val margin = resources.getDimensionPixelSize(R.dimen.indicator_margin)
            val layoutParams = LinearLayout.LayoutParams(size, size).apply {
                setMargins(margin, 0, margin, 0)
            }
            indicator.layoutParams = layoutParams
            indicator.setBackgroundResource(R.drawable.indicator_background)
            indicatorContainer.addView(indicator)
        }
    }

    private fun updateIndicator(currentPosition: Int) {
        val numIndicators = indicatorContainer.childCount
        for (i in 0 until numIndicators) {
            val indicator = indicatorContainer.getChildAt(i)
            indicator.setBackgroundResource(
                if (i == currentPosition) R.drawable.indicator_selected_background
                else R.drawable.indicator_background
            )
        }
    }

    private fun startWelcomeActivity() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}