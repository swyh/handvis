package com.example.kimyounghoon.handvis.activity.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kimyounghoon.handvis.R
import com.example.kimyounghoon.handvis.Server
import com.example.kimyounghoon.handvis.toast
import khttp.responses.Response
import kotlinx.android.synthetic.main.activity_join.*
import org.json.JSONObject
import java.util.concurrent.Semaphore
import kotlin.concurrent.thread

class JoinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        join_succ_btn.setOnClickListener { join() }
        join_fail_btn.setOnClickListener{ cancel()}
    }

    fun join(){
        val id:String = join_et_id.text.toString()
        val pw:String = join_et_pw.text.toString()
        val pw2:String = join_et_pw2.text.toString()

        if(check(id,pw,pw2))
            sucess(id, pw)
    }

    fun check(id:String, pw:String, pw2:String):Boolean{

        if(id.length < 4 && pw.length < 4) {
            toast(this, "ID와 PW는 4글자 이상이여야합니다.")
            return false
        }
        else if(pw.equals(pw2)) {
            toast(this, "비밀번호가 일치하지 않습니다.")
            return false
        }
        else{
            var sem = Semaphore(1)
            var perm:Int = 0;

            sem.acquire()
            thread {
                val response: Response = khttp.post(
                        url = Server.url_join(),
                        data = mapOf("userId" to id, "password" to pw))
                val obj: JSONObject = response.jsonObject
                perm = obj.getInt("perm")

                sem.release()
            }
            sem.acquire()
            Log.e("login", id)
            Log.e("login", pw)
            Log.e("login", perm.toString())

            if (perm != 1) {
                toast(this, "이미 존재하는 ID입니다.")
                return false
            }
        }

        toast(this, "가입이 완료되었습니다.")
        return true
    }

    fun sucess(id:String, pw:String){
        var data:Intent = Intent()
        data.putExtra("id", id)
        data.putExtra("pw", pw)
        setResult(1,data)
        finish()
    }

    fun cancel(){
        toast(this, "가입이 취소되었습니다.")
        var data:Intent = Intent()
        setResult(-1,data)
        finish()
    }
    public override fun onBackPressed() {
        cancel();
    }
}
