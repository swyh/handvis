package com.example.kimyounghoon.handvis.activity.function

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.example.kimyounghoon.handvis.*
import com.example.kimyounghoon.handvis.Services.DataService
import com.example.kimyounghoon.handvis.UploadImage
import kotlinx.android.synthetic.main.activity_update_device.*
import kotlin.concurrent.thread

class UpdateDeviceActivity : AppCompatActivity() {
    lateinit var uploadImage: UploadImage
    lateinit var finger_list:List<ImageView>
    var background_image:String = ""
    var fingerNumber:Int = 0
    var idx:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_device)

        uploadImage = UploadImage(update_image, this)
        finger_list = listOf(hand_0, hand_1, hand_2, hand_3, hand_4, hand_5)

        getinfo()
        fingerNumber = DataService.devices[idx].fingerNumber
        background_image = DataService.devices[idx].background_image

        //이미지 매핑
        update_image.setImageURI(Uri.parse(background_image))
        update_image.setOnClickListener{ chageImage()}
        update_succ_btn.setOnClickListener { join() }
        update_fail_btn.setOnClickListener{ cancel()}

        for(i in 0..finger_list.size-1) {   // 손동작마다 리스너
            if(i == fingerNumber ||  DataService.useFinger[i].used == false)// 손동작 할당
                finger_list[i].setOnClickListener { selectFinger(i) }
            else
                used_image_view(getImageVeiw(i))    // 사용중인 손동작은 used 표시
        }
    }
    public override fun onBackPressed() {
        uploadImage.GET_PICTURE_URI = -100
    }

    fun chageImage(){
        uploadImage.onScreenGellery()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {    // updatedeviceactivity에서 받음
        background_image = uploadImage.onActivityResult(requestCode, resultCode, data)
    }


    fun selectFinger(next:Int){
        revise_image_view(getImageVeiw(fingerNumber))
        gray_image_view(getImageVeiw(next))

        fingerNumber = next
    }

    fun getImageVeiw(fingerNumber:Int):ImageView{
        var finger:ImageView = when(fingerNumber){
            0->hand_0
            1->hand_1
            2->hand_2
            3->hand_3
            4->hand_4
            5->hand_5
            else->hand_0
        }
        return finger
    }


    fun getinfo(){
        var intent: Intent = getIntent()
        var idx: Int = intent.getIntExtra("idx", 0)

        var resourceId = this.resources.getIdentifier(DataService.devices[idx].background_image, "drawable", this.packageName) //각각 파일명, 파일이 있는 위치, 파일이 소속된 패키지명을 받아 구분자를 리턴함(리소스 네임)
        update_image.setImageResource(resourceId) //이미지 맵핑

        update_name.setText(DataService.devices[idx].title)   // title
        update_name.setSelection(update_name.length())  // 커서를 끝으로 이동

        //finger
        var finger:ImageView = when(DataService.devices[idx].fingerNumber){
            0->hand_0
            1->hand_1
            2->hand_2
            3->hand_3
            4->hand_4
            5->hand_5
            else->hand_0
        }
        gray_image_view(finger)

        this.idx = idx
    }

    fun join(){
        toast(this, "수정되었습니다.")
        setDivice()
        setList()
        mFragmentB.touchAdapter()
        finish()
    }

    fun cancel(){
        toast(this, "취소되었습니다.")
        finish()
    }

    fun setDivice(){
        DataService.useFinger[DataService.devices[idx].fingerNumber].used = false
        DataService.useFinger[fingerNumber].used = true // 손동작 할당



        DataService.devices[idx].title = update_name.text.toString()
        DataService.devices[idx].fingerNumber = fingerNumber
        DataService.devices[idx].background_image = background_image// 배경 변경

        Log.v("title", DataService.devices[idx].title)
//        Log.v("image", update_image.)

        //device.background_image = update_image // 소스를 update_image로
    }

    fun setList(){
        thread{
            // request
            Log.v("Update", DataService.devices[idx].background_image)

            val state_int = if(DataService.devices[idx].switch == true) 1 else 0
            Log.v("state_int", state_int.toString())
            khttp.post( // 안드로이드 -> 서버
                    url = Server.url_update(),
                    data = mapOf(
                            "userId" to User.id,
                            "deviceNumber" to DataService.devices[idx].deviceNumber.toString(),
                            "fingerNumber" to DataService.devices[idx].fingerNumber.toString(),
                            "name" to DataService.devices[idx].title,
                            "background_image" to DataService.devices[idx].background_image,
                            "state" to state_int))
            Log.e("json_ser", Server.url_update())
            Log.e("json", DataService.devices[idx].title)

            // data <-> json
        }
    }
}
