package com.example.kimyounghoon.handvis.activity.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.kimyounghoon.handvis.R

/**
 * Created by KIMYOUNGHOON on 3/17/2018.
 */

class SplashActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 1초후 화면 전환
        try{
            Thread.sleep(1000)
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }catch (e:InterruptedException){
            e.printStackTrace()
        }
    }

}