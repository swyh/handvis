package com.example.kimyounghoon.handvis.Services

import com.example.kimyounghoon.handvis.Model.Article
import com.example.kimyounghoon.handvis.Model.Device
import com.example.kimyounghoon.handvis.Model.Finger


/**
 * Created by KIMYOUNGHOON on 3/30/2018.
 */

object DataService {
    var useFinger:List<Finger> = listOf(Finger(), Finger(), Finger(), Finger(), Finger(), Finger(), Finger())
    var devices:List<Device> = listOf()
    var articles:List<Article> = listOf()
}