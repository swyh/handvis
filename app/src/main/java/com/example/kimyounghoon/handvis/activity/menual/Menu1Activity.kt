package com.example.kimyounghoon.handvis.activity.menual

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget
import com.example.kimyounghoon.handvis.R
import kotlinx.android.synthetic.main.activity_menu1.*

class Menu1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu1)

        val gifImage1 = GlideDrawableImageViewTarget(gif1)
        Glide.with(this).load(R.raw.gif_light).into(gif1)
    }
}
