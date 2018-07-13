package com.example.kimyounghoon.handvis.activity.main

import android.app.Fragment
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.TextView
import com.example.kimyounghoon.handvis.R
import com.example.kimyounghoon.handvis.BackPressCloseHandler
import kotlinx.android.synthetic.main.activity_main.*
import khttp.delete as httpDelete



class MainActivity : AppCompatActivity() {

    private val mFragmentManager = fragmentManager  // fragment 관리
    private lateinit var  backPressCloseHandler: BackPressCloseHandler

    //나중에 초기화 시키기 위해 lateinit
    lateinit var fragment_btn:List<ImageButton>
    lateinit var fragment_line:List<TextView>
    lateinit var fragment:List<Fragment>
    var selector:Int = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        backPressCloseHandler = BackPressCloseHandler(this) // 뒤로가기 2번

        //button과 fragment를 list 형식으로 초기화
        fragment_btn = listOf(fragment_button1, fragment_button2, fragment_button3)
        fragment_line = listOf(fragment_line1, fragment_line2, fragment_line3)
        fragment = listOf(FragmentA(), FragmentB(), FragmentC())

        //beginTransaction() 매소드로 프래그먼트를 불러올 준비를하고 add 매소드를 통해 fragment 추가, commit() 메소드로 추가한 프래그먼트를 배치
        mFragmentManager.beginTransaction().add(R.id.fragment, fragment[selector]).commit()

        fragment_line[selector].setBackgroundColor(Color.DKGRAY)

        //각 button에 listener를 달아줌
        for(i in 0..fragment_btn.size-1)
            fragment_btn[i].setOnClickListener { changeView(i) }

    }

    public override fun onBackPressed() {
        backPressCloseHandler.onBackPressed()
    }


    private fun changeView(i:Int){

        //누른 button 강조
        fragment_line[selector].setBackgroundColor(Color.WHITE)
        selector = i
        fragment_line[selector].setBackgroundColor(Color.DKGRAY)

        //fragment 교체
        val fragmentTransaction = mFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment, fragment[selector]).commit()
    }
}