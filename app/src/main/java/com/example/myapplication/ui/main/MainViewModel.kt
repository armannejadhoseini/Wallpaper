package com.example.myapplication.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val search_word =  MutableLiveData<String>().apply { value = "https://pixabay.com/api/?key=5303976-fd6581ad4ac165d1b75cc15b3&q=flower&image_type=photo&pretty=true" }
    private lateinit var webinfo: Array<String>
    private lateinit var weburl: Array<String>

    fun getbaseurl(): MutableLiveData<String> {
        return search_word
    }
    fun initweb() {
        webinfo = Array(20) {""}
        weburl = Array(20) {""}


    }
    fun getwebinfo(): Array<String> {
        return webinfo
    }
    fun getweburl(): Array<String> {
        return weburl
    }

}