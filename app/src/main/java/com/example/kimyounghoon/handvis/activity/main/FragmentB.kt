package com.example.kimyounghoon.handvis.activity.main

import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kimyounghoon.handvis.Adapters.DeviceAdapter
import com.example.kimyounghoon.handvis.Model.Device
import com.example.kimyounghoon.handvis.Services.DataService
import khttp.post
import khttp.responses.Response
import kotlinx.android.synthetic.main.fragment_b.*
import org.json.JSONObject
import java.util.concurrent.Semaphore
import android.support.v4.widget.SwipeRefreshLayout
import com.example.kimyounghoon.handvis.*
import com.example.kimyounghoon.handvis.Services.DataService.devices


/**
 * Created by KIMYOUNGHOON on 3/14/2018.
 */

class FragmentB : Fragment() {
    lateinit var adapter: DeviceAdapter
    var one_time = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {    //상속받은 함수를 재정의
        return if (inflater != null) {
            //fragment_b 리소스를 가져와서 배치함.
            inflater.inflate(R.layout.fragment_b,container,false)
        }else{
            super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {   // 액티비티가 만들어진 후 실행 됨.
        super.onActivityCreated(savedInstanceState)

        mFragmentB = this

        val mSwipeRefreshLayout = swipe_layout as SwipeRefreshLayout    // 새로고침

        mSwipeRefreshLayout.setOnRefreshListener({  // 새로고침 리스너
            mSwipeRefreshLayout.setRefreshing(false);
            // update device
            DataService.devices = listOf()
            getList()
            touchAdapter()
        })


        if(!one_time && devices.isEmpty()) { // 처음 불러오기
            one_time = true
            toast(getActivity(), "반갑습니다.")
            val error = getList()
            if (error) {
                toast(getActivity(), "서버와 연결되지 않습니다.")
                return
            }
        }

        touchAdapter()
    }

    fun touchAdapter(){
        adapter = DeviceAdapter(getActivity(),DataService.devices)
        deviceListView.adapter = adapter
    }


    fun getList() : Boolean{
        var error = false;
        var sem = Semaphore(1)

        sem.acquire()
        Thread() {
            try {   // 서버->안드로이드
                //val response: Response = post(url = Server.url_list(),
                //                            data = mapOf("userId" to User.id))
                val response: Response = post(url = Server.url_list(),
                                            cookies = User.cookie)
                val obj: JSONObject = response.jsonObject
                val arrJson = obj.getJSONArray("device")
                for (i in 0..arrJson!!.length() - 1) {
                    val deviceNumber = arrJson.getJSONObject(i).getInt("deviceNumber")
                    val fingerNumber = arrJson.getJSONObject(i).getInt("fingerNumber")
                    val title = arrJson.getJSONObject(i).getString("name")
                    val backgrond_image = arrJson.getJSONObject(i).getString("background_image")
                    val switch_int = arrJson.getJSONObject(i).getInt("state")
                    var switch: Boolean
                    if (switch_int == 1)
                        switch = true
                    else
                        switch = false
                    Log.v("deivces", deviceNumber.toString() + " and " + fingerNumber.toString() + " and " + title + " last " + switch)
                    DataService.devices += Device(deviceNumber, fingerNumber, title, backgrond_image, switch)
                    Log.v("deivces", DataService.devices[i].title)
                    Log.v("device_back", DataService.devices[i].background_image)

                    DataService.useFinger[fingerNumber].used = true // 손동작 할당
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