package com.redflower.flickrsearch.viewmodel

import android.os.AsyncTask
import com.redflower.flickrsearch.asynctasks.ImagefetchAsynctask
import com.redflower.flickrsearch.interfaces.AsyncListener
import com.redflower.flickrsearch.interfaces.ImageListingActivityListener
import com.redflower.flickrsearch.model.ImageItem
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class ImageListingViewModel(var data: String,var searchstring: String, var listener: ImageListingActivityListener):  AsyncListener{

    var totalimage: Int = 0
    var current_page: Int = 1
    var totalpage = 1
    var imagesarray: ArrayList<ImageItem> = ArrayList()
    var isLoading: Boolean = false
    var isreachedend: Boolean = false
    init {
        try {
            parseData(data)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun parseData(responsedata: String){
        val basejson: JSONObject = JSONObject(responsedata)
        val photosjson = basejson.getJSONObject("photos")
        totalimage = photosjson.getInt("total")
        totalpage= photosjson.getInt("pages")
        val photosarray = photosjson.getJSONArray("photo")
        val photojsonarray: JSONArray = photosarray
        for (i in 0..(photojsonarray.length() - 1)) {
            var tempphotoobject: JSONObject = photojsonarray.getJSONObject(i)
            imagesarray.add(
                ImageItem(
                    tempphotoobject.getString("id"),
                    tempphotoobject.getString("owner"),
                    tempphotoobject.getString("secret"),
                    tempphotoobject.getString("server"),
                    tempphotoobject.getString("farm"),
                    tempphotoobject.getString("title")
                )
            )
        }
    }

    fun loadMore(){
        if(totalpage>current_page) {
            isLoading = true
            ImagefetchAsynctask(searchstring, current_page++, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        }else{
            isreachedend = true
        }
    }

    override fun onResponse(responsestring: String?) {
        parseData(responsestring as String)
        listener.notifydatasetchange()
        isLoading = false
    }

    override fun onError() {
    }
}
