package com.ninja.hobbify.ui.introscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.ninja.hobbify.HobbyActivity
import com.ninja.hobbify.R
import com.ninja.hobbify.data.models.IntroView
import com.ninja.hobbify.databinding.ActivityIntroBinding
import com.ninja.hobbify.ui.introscreen.adapters.ViewPagerIntroAdapter

class IntroActivity : AppCompatActivity() {

    lateinit var introView: List<IntroView>
    private lateinit var introActivityIntroBinding: ActivityIntroBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_intro)

        introActivityIntroBinding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(introActivityIntroBinding.root)


        addToIntroView()

        introActivityIntroBinding.viewPager2.adapter = ViewPagerIntroAdapter(introView)
        introActivityIntroBinding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        introActivityIntroBinding.circleIndicator.setViewPager(introActivityIntroBinding.viewPager2)

        introActivityIntroBinding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if(position == 2){
                    animateButton()
                }
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })
    }

    private fun animateButton() {
        introActivityIntroBinding.btnStartApp.visibility = View.VISIBLE

        introActivityIntroBinding.btnStartApp.animate().apply {
            duration = 1400
            alpha(1f)

            introActivityIntroBinding.btnStartApp.setOnClickListener {
                val intent = Intent(applicationContext, HobbyActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.start()
    }

    private fun addToIntroView() {
        introView = listOf(
            IntroView(
                "Welcome to Hobbify", R.drawable.viewpager1
            ),
            IntroView(
                "Discover, nurture, and celebrate your hobbies", R.drawable.viewpager2
            ),
            IntroView(
                "Get your happy on with Hobbify", R.drawable.viewpager3
            )
        )
    }
}