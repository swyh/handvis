package com.example.kimyounghoon.handvis.activity.main

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_c.*
import com.example.kimyounghoon.handvis.*
import com.example.kimyounghoon.handvis.Services.DataService
import com.example.kimyounghoon.handvis.activity.menual.MenualActivity
import com.example.kimyounghoon.handvis.activity.function.NotificationActivity
import com.example.kimyounghoon.handvis.activity.login.LoginActivity


/**
 * Created by KIMYOUNGHOON on 3/14/2018.
 */

class FragmentC : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {    //상속받은 함수를 재정의
        return if (inflater != null) {
            //fragment_a 리소스를 가져와서 배치함.
            inflater.inflate(R.layout.fragment_c,container,false)
        }else{
            super.onCreateView(inflater, container, savedInstanceState)
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {   // 액티비티가 만들어진 후 실행 됨.
        super.onActivityCreated(savedInstanceState)

        c_btn_logout.setOnClickListener{logout()}
        c_btn_menual.setOnClickListener{menual()}
        c_btn_notification.setOnClickListener { notification() }
    }

    fun notification(){
        val intent = Intent(getActivity(), NotificationActivity::class.java)
        startActivity(intent)
    }

    fun menual(){
        val intent = Intent(getActivity(), MenualActivity::class.java)
        startActivity(intent)
    }

    fun logout(){
        DataService.devices = listOf()  // 초기화

        toast(getActivity(), "로그아웃 되었습니다.")
        val intent = Intent(getActivity(), LoginActivity::class.java)
        startActivity(intent)
        getActivity().finish()
    }

}