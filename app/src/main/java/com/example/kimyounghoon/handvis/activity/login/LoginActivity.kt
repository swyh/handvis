package com.example.kimyounghoon.handvis.activity.login

import android.Manifest
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.kimyounghoon.handvis.activity.main.MainActivity
import com.example.kimyounghoon.handvis.R
import kotlinx.android.synthetic.main.activity_login.*
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.os.Build
import android.util.Log
import com.example.kimyounghoon.handvis.User
import com.example.kimyounghoon.handvis.Server
import com.example.kimyounghoon.handvis.toast
import khttp.responses.Response
import java.util.concurrent.Semaphore
import kotlin.concurrent.thread


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button.setOnClickListener { login() }
        join_button.setOnClickListener{join()}
        login_btn_setting.setOnClickListener{
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        requestPermissionCamera()
    }

    fun login(){
        val id = login_id.text.toString()
        val pw = login_pw.text.toString()
        var sem = Semaphore(1)
        var perm:Int= 0;
        var sessionID:String? = null

        toast(this, "로그인 중입니다. 잠시만 기다려 주세요.")
        sem.acquire()
        thread { // android -> server -> android
            val response: Response = khttp.post(
                    url = Server.url_login(),
                    data = mapOf("userId" to id, "password" to pw))

            sessionID = response.cookies["JSESSIONID"]

            if(sessionID == null)
               perm = 0;
            else
                perm = 1;
            sem.release()
        }


        sem.acquire()
        Log.e("login", id)
        Log.e("login", pw)
        Log.e("login", perm.toString())

        if(perm == 1){

            User.cookie = mapOf("JSESSIONID" to sessionID.toString())
            User.id = id

            toast(this, "로그인되었습니다.")



            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        else
            toast(this, "ID 혹은 PW가 잘못되었습니다.")

        //if (perm == 1) {

        //    Id.id = id

        //    toast(this, "로그인되었습니다.")
        //    val intent = Intent(this, MainActivity::class.java)
        //    startActivity(intent)
        //    finish()
        //} else {
        //    toast(this, "ID 혹은 PW가 잘못되었습니다.")
        //}
    }

    fun join(){
        val intent = Intent(this, JoinActivity::class.java)
        startActivityForResult(intent, 1)   // intent로 id, pw 값을 받기위해
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode  == 1){
            toast(this, "ActivityResult")

            var id:String = data.getStringExtra("id")
            var pw:String = data.getStringExtra("pw")
            login_id.setText(id)
            login_pw.setText(pw)
        }
    }

    private val PERMISSIONS_REQUEST_RESULT = 100 // 콜백함수 호출시 requestCode로 넘어가는 구분자

    fun requestPermissionCamera(): Boolean {
        val sdkVersion = Build.VERSION.SDK_INT

        // 해당 단말기의 안드로이드 OS버전체크
        if (sdkVersion >= Build.VERSION_CODES.M) {
            // 버전 6.0 이상일 경우

            // 해당 퍼미션이 필요한지 체크 - true : 퍼미션 동의가 필요한 권한일 때 , false : 퍼미션 동의가 필요하지 않은 권한일 때.
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // true : 퍼미션 동의가 필요한 권한일 때

                // 사용자가 최초 퍼미션 체크를 했는지 확인한다. - true : 사용자가 최초 퍼미션 요청시 '거부'했을 때, false : 퍼미션 요청이 처음일 경우
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // true : 사용자가 최초 퍼미션 요청시 '거부'해서 재요청일 때
                } else {
                    // false : 퍼미션 요청이 처음일 경우.

                    // 퍼미션의 동의 여부를 다이얼로그를 띄워 요청한다. 이 때 '동의', '거부'의 결과값이 onRequestPermissionsResult 으로 콜백된다.
                    ActivityCompat.requestPermissions(this,
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                            PERMISSIONS_REQUEST_RESULT)
                }
            } else {
                // false : 퍼미션 동의가 필요하지 않은 권한일 때.
            }
        } else {
            // version 6 이하일 때에는 별도의 작업이 필요없다.
        }

        return true

    }
}
