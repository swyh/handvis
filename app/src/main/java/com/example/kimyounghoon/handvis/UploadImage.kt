package com.example.kimyounghoon.handvis

import android.provider.MediaStore
import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.util.Log
import android.widget.ImageView
import android.content.CursorLoader
import android.net.Uri


/**
 * Created by KIMYOUNGHOON on 5/15/2018.
 */

public class UploadImage(var image_view: ImageView, var activity: Activity){

    var GET_PICTURE_URI = 100
    lateinit var intent:Intent

    public fun onScreenGellery(){
        GET_PICTURE_URI = 100
        intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(activity, intent,GET_PICTURE_URI, null)
    }

    public fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) : String{
        var image_path:String = ""
        if (requestCode == GET_PICTURE_URI) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), data.data)
                    // 배치해놓은 ImageView에 이미지를 넣어봅시다.
                    image_view.setImageBitmap(bitmap)
                    //Glide.with(mContext).load(data.getData()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView); // OOM 없애기위해 그레들사용
                    image_path = getRealPathFromURI(data.data)
                    //image_path = data.data.toString()

                    Log.e("uri", image_path)

                } catch (e: Exception) {
                    Log.e("test", e.message)
                }
            }
        }
        return image_path
    }

    private fun getRealPathFromURI(contentUri: Uri): String {
        val proj = arrayOf(MediaStore.Images.Media.DATA)

        val cursorLoader = CursorLoader(activity, contentUri, proj, null, null, null)
        val cursor = cursorLoader.loadInBackground()

        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }

}