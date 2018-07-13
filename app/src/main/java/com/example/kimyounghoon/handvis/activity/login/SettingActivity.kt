package com.example.kimyounghoon.handvis.activity.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.kimyounghoon.handvis.*
import kotlinx.android.synthetic.main.activity_setting.*
import android.widget.RadioGroup



class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        init()
    }

    private fun init(){

        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rd_server1 -> setServer(0)
                R.id.rd_server2 -> setServer(1)
            }
            displayNetwork()
        })

        // IP 설정
        bnt_set_ip.setOnClickListener{
            setNetwork()}
        bnt_cancel_ip.setOnClickListener{finish()}
        displayNetwork()
    }

    fun setNetwork(){
        val ip = et_server_ip.text.toString()
        val port = et_server_port.text.toString()

        if(ip.equals("") || port.equals("")){
            toast(this, "공백은 입력할 수 없습니다.")
            return
        }

        Server.setNetwork(ip,port)

        displayNetwork()
        toast(this, "IP : ${Server.getIp()} & PORT : ${Server.getPort()}")

        hideKeyboard(this)

    }

    fun displayNetwork(){
        tv_ip.setText("IP : ${Server.getIp()}")
        tv_port.setText("PORT : ${Server.getPort()}")
    }
}