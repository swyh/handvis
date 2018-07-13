package com.example.kimyounghoon.handvis.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.kimyounghoon.handvis.Model.Device
import com.example.kimyounghoon.handvis.R
import com.example.kimyounghoon.handvis.activity.function.UpdateDeviceActivity
import de.hdodenhof.circleimageview.CircleImageView
import android.net.Uri
import com.example.kimyounghoon.handvis.User
import com.example.kimyounghoon.handvis.Server
import khttp.post
import khttp.responses.Response
import java.util.concurrent.Semaphore
import kotlin.concurrent.thread


/**
 * Created by KIMYOUNGHOON on 3/29/2018.
 */

/* adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,DataService.categories)에서
* Adapter가 CategoryAdapter로 this와 DataService.categories를 맵핑하여준다*/
public class DeviceAdapter(context: Context, devices: List<Device>) : BaseAdapter(){
    val context = context
    val devices = devices

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val deviceView: View
//xml레이아웃을 맵핑하여 주는 코드
        deviceView = LayoutInflater.from(context).inflate(R.layout.device_list_item, null)
//이름과 맵핑하여주기
        val fingerImage : CircleImageView = deviceView.findViewById(R.id.fingerImage) //전편에 만들었던 category_list.item.xml의 이미지 id를 categoryImage로 변경할 것
        val deviceImage : ImageView = deviceView.findViewById(R.id.deviceImage) //전편에 만들었던 category_list.item.xml의 이미지 id를 categoryImage로 변경할 것
        val deviceName: TextView = deviceView.findViewById(R.id.deviceName)
        val deviceSwitch: Switch = deviceView.findViewById(R.id.deviceSwitch)
//데이터를 가져온 후
        val device = devices[position]
//데이터 연결시켜주기
        deviceName.text = device.title

        var resourceId = context.resources.getIdentifier("hand_" + device.fingerNumber.toString(), "drawable", context.packageName) //각각 파일명, 파일이 있는 위치, 파일이 소속된 패키지명을 받아 구분자를 리턴함(리소스 네임)
        fingerImage.setImageResource(resourceId) //이미지 맵핑

        val path = device.background_image
        deviceImage.setImageURI(Uri.parse(path))


        deviceSwitch.setChecked(device.switch)


//리스너 등록 ~!!
        deviceSwitch.setOnClickListener{switchChange(device.fingerNumber, context)}
        deviceName.setOnClickListener{updateExecute(position, context)}

        return deviceView
    }

    fun updateExecute(idx:Int, context: Context){   // 디바이스 업데이트창 띄움
        val intent = Intent(context, UpdateDeviceActivity::class.java)
        intent.putExtra("idx", idx)
        context.startActivity(intent)
    }

    fun switchChange(fingerNumber:Int, context:Context){  // 스위치 동작
        var sem = Semaphore(1)
        sem.acquire()
        thread { // android -> server -> android
            val response: Response = post(
                    url = Server.url_recognition(),
                    //json = mapOf("userId" to User.id, "currentAction" to fingerNumber))
                    json = mapOf("userId" to User.id, "currentAction" to fingerNumber),
                    cookies = User.cookie
                    )


            sem.release()
        }
        sem.acquire()
    }

    override fun getItem(position: Int): Any {
//카테고리를 리턴
        return devices[position]
    }
    override fun getItemId(position: Int): Long {
//사용하지 않을것임 유니크 id를 받아 처리하는 메서드
        return 0
    }
    override fun getCount(): Int {
//전체 개수 반환 ios에서의 numberOfSection
        return devices.count()
    }

}