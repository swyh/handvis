package com.example.kimyounghoon.handvis.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.kimyounghoon.handvis.Model.Article
import com.example.kimyounghoon.handvis.R
import com.example.kimyounghoon.handvis.Services.DataService.articles

/**
 * Created by KIMYOUNGHOON on 3/29/2018.
 */

/* adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,DataService.categories)에서
* Adapter가 CategoryAdapter로 this와 DataService.categories를 맵핑하여준다*/
public class ArticleAdapter(context: Context, articles: List<Article>) : BaseAdapter(){
    val context = context
    val devices = articles

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val articleView: View
//xml레이아웃을 맵핑하여 주는 코드
        articleView = LayoutInflater.from(context).inflate(R.layout.article_list_item, null)
//이름과 맵핑하여주기
        val title : TextView = articleView.findViewById(R.id.notification_title) //전편에 만들었던 category_list.item.xml의 이미지 id를 categoryImage로 변경할 것
        val content : TextView = articleView.findViewById(R.id.notification_content)
        val date : TextView = articleView.findViewById(R.id.notification_date)
        val layout : LinearLayout = articleView.findViewById(R.id.notification_liner)
//데이터를 가져온 후
        val article = articles[position]
        var open = false
//데이터 연결시켜주기
        title.text = article.title
        date.text = article.date

//리스너 등록 ~!!
        layout.setOnClickListener{open = notificationExecute(position, open, article.content, content)}

        return articleView
    }


    fun notificationExecute(idx:Int, open:Boolean, content_str:String,content:TextView) : Boolean{   // 디바이스 업데이트창 띄움
        //val intent = Intent(context, UpdateDeviceActivity::class.java)
        //intent.putExtra("idx", idx)
        //context.startActivity(intent)
        if(open == false) {
            content.text = content_str
            return true
        }
        else {
            content.text = ""
            return false
        }
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