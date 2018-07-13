package com.example.kimyounghoon.handvis.activity.menual

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.kimyounghoon.handvis.R
import kotlinx.android.synthetic.main.activity_menual.*


class MenualActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menual)

        menual_1.setOnClickListener({
            val intent = Intent(this, Menu1Activity::class.java)
            startActivity(intent)})

        menual_2.setOnClickListener({
            val intent = Intent(this, Menu2Activity::class.java)
            startActivity(intent)})
    }

}
