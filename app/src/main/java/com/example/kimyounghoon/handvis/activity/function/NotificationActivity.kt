package com.example.kimyounghoon.handvis.activity.function

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kimyounghoon.handvis.Adapters.ArticleAdapter
import com.example.kimyounghoon.handvis.Model.Article
import com.example.kimyounghoon.handvis.R
import com.example.kimyounghoon.handvis.Server
import com.example.kimyounghoon.handvis.Services.DataService
import khttp.get
import khttp.responses.Response
import kotlinx.android.synthetic.main.activity_notification.*
import org.json.JSONObject
import java.util.concurrent.Semaphore

class NotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        getNotify()

        var adapter = ArticleAdapter(this, DataService.articles)
        articleListView.adapter = adapter
    }
    fun getNotify() : Boolean{
        var error = false;
        var sem = Semaphore(1)

        sem.acquire()
        Thread() {
            DataService.articles = listOf()
            try {   // 서버->안드로이드
                val response: Response = get(Server.url_notify())
                val obj: JSONObject = response.jsonObject
                val arrJson = obj.getJSONArray("notification")
                for (i in 0..arrJson!!.length() - 1) {
                    val title = arrJson.getJSONObject(i).getString("title")
                    val content = arrJson.getJSONObject(i).getString("content")
                    val date = arrJson.getJSONObject(i).getString("datetime")
                    DataService.articles += Article(title, content, date)
                    Log.e("공지", title);
                }
            }catch(e:Exception){
                Log.v("err", e.toString())
                error = true
            }

            sem.release()
        }.start() // DB에서 데이터 받기

        sem.acquire()
        return error
    }

}
