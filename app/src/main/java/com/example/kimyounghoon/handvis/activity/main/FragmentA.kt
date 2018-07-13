package com.example.kimyounghoon.handvis.activity.main

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_a.*
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.CookieSyncManager
import com.example.kimyounghoon.handvis.*


@Suppress("DEPRECATION")
/**
 * Created by KIMYOUNGHOON on 3/14/2018.
 */

class FragmentA : Fragment() {
    lateinit var mainWebView:WebView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {    //상속받은 함수를 재정의
        return if (inflater != null) {
            //fragment_a 리소스를 가져와서 배치함.
            inflater.inflate(R.layout.fragment_a,container,false)
        }else{
            super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //try {
         //   mainWebView = web_view
         //   CookieSyncManager.createInstance(getActivity())
        //    var cookieManager = CookieManager.getInstance()
        //    cookieManager.removeSessionCookie()

        //    cookieManager.setCookie("JSESSIONID", User.cookie!!["JSESSIONID"])
        //    CookieSyncManager.getInstance().sync()
            //var headers:Map<String, String>  = HashMap<>()
            //headers.put("name", "value");
       //     mainWebView.loadUrl(Server.url_index())

            //도메인 url, 쿠키
       //     val cookieString = "JSESSIONID" + "=" + User.cookie!!["JSESSIONID"]
       //     Log.e("cooking", cookieString)

       //     cookieManager.setCookie(Server.url_index(), cookieString)
       //     CookieSyncManager.getInstance().startSync()
       // }
       // catch(e:Exception){
       //     Log.e("err", e.toString())
       // }

        //Thread.sleep(500)

        mainWebView = web_view

        mainWebView.getSettings().setJavaScriptEnabled(true)
        CookieManager.getInstance().setCookie(Server.url_index(), "JSESSIONID=" + User.cookie!!["JSESSIONID"]);
        mainWebView.loadUrl(Server.url_index())

    }

    override fun onStart() {
        super.onStart()
        CookieSyncManager.getInstance().startSync();
    }

    override fun onResume() {
        super.onResume()
        CookieSyncManager.getInstance().startSync();
    }

    override fun onPause() {
        super.onPause()
        CookieSyncManager.getInstance().startSync();
    }
}