package com.example.kimyounghoon.handvis.activity.menual

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget
import com.example.kimyounghoon.handvis.R
import kotlinx.android.synthetic.main.activity_menu2.*

class Menu2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu2)

        val gifImage2 = GlideDrawableImageViewTarget(gif2)
        Glide.with(this).load(R.raw.gif_update).into(gif2)
    }
}
