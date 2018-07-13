package com.example.kimyounghoon.handvis

import android.app.Activity
import android.graphics.Color
import android.graphics.PorterDuff
import android.widget.ImageView
import android.widget.Toast
import com.example.kimyounghoon.handvis.activity.main.FragmentB

/**
 * Created by KIMYOUNGHOON on 3/17/2018.
 */
var mFragmentB : FragmentB = FragmentB()

var server_num:Int = 1

public object User{   // 차후 세션/쿠키로 바꿀 예정
    var id:String = ""
    var cookie:Map<String,String>? = null
}

public object Server {
    var IP: ArrayList<String> = arrayListOf("165.246.229.227", "165.246.243.113")
    var PORT: ArrayList<String> = arrayListOf("80", "9999")

    val UPDATE: List<String> = listOf("iot/update.php", "api/update")
    val LIST: List<String> = listOf("iot/getjson.php", "api/list")

    val LOGIN: List<String> = listOf("iot/login.php", "api/users/login")
    val JOIN: List<String> = listOf("iot/join.php", "api/register")

    val NOTIFY: List<String> = listOf("iot/notify.php", "api/notification")
    val RECOGNITION : List<String> = listOf("", "api/recognition")
    val INDEX:List<String> = listOf("", "index.jsp")

    fun getIp():String{
        return IP[server_num]
    }
    fun getPort():String{
        return PORT[server_num]
    }

    fun setNetwork(ip:String, port:String){
        IP[server_num] = ip
        PORT[server_num] = port
    }

    fun url_root():String{
        return "http://${IP[server_num]}:${PORT[server_num]}/"
    }

    fun url_list():String{
        return url_root() + LIST[server_num]
    }

    fun url_update():String{
        return url_root() + UPDATE[server_num]
    }

    fun url_login():String{
        return url_root() + LOGIN[server_num]
    }

    fun url_join():String{
        return url_root() + JOIN[server_num]
    }

    fun url_notify():String{
        return url_root() + NOTIFY[server_num]
    }

    fun url_recognition():String{
        return url_root() + RECOGNITION[server_num]
    }

    fun url_index():String{
        return url_root() + INDEX[server_num]
    }
}

fun setServer(i:Int){
    server_num = i
}

fun used_image_view(image:ImageView){
    image.setColorFilter(Color.parseColor("#ffe6e6"), PorterDuff.Mode.MULTIPLY);   // 사진을 어둡게
}

fun revise_image_view(image:ImageView){
    image.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.DST);
}

fun gray_image_view(image:ImageView){
    image.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);   // 사진을 어둡게
}

fun toast(activity:Activity,message:String){
    Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
}

fun hideKeyboard(activity : Activity){
 //   val inputManager: InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//    inputManager.hideSoftInputFromWindow(activity.currentFocus.windowToken, InputMethodManager.SHOW_FORCED)
}