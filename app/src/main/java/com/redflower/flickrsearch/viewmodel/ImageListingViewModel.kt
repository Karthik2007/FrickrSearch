package com.redflower.flickrsearch.viewmodel

import android.os.AsyncTask
import com.redflower.flickrsearch.asynctasks.ImagefetchAsynctask
import com.redflower.flickrsearch.interfaces.AsyncListener
import com.redflower.flickrsearch.interfaces.ImageListingActivityListener
import com.redflower.flickrsearch.model.ImageItem
import com.redflower.flickrsearch.utils.FlickrSearchConstants
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class ImageListingViewModel(var data: String, var searchstring: String, var listener: ImageListingActivityListener) :
    AsyncListener {

    var totalimage: Int = 0
    var current_page: Int = 1
    var totalpage = 1
    var imagesarray: ArrayList<ImageItem> = ArrayList()
    var isLoading: Boolean = false
    var isreachedend: Boolean = false

    init {
        try {
            parseData(data)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun parseData(responsedata: String) {
        val basejson: JSONObject = JSONObject(responsedata)
        val photosjson = basejson.getJSONObject(FlickrSearchConstants.KEY_PHOTO_JSON)
        totalimage = photosjson.getInt(FlickrSearchConstants.KEY_PHOTO_TOTAL_IMAGES_COUNT)
        totalpage = photosjson.getInt(FlickrSearchConstants.KEY_PHOTO_TOTAL_PAGES_COUNT)
        val photosarray = photosjson.getJSONArray(FlickrSearchConstants.KEY_PHOTO_PHOTO_ARRAY)
        val photojsonarray: JSONArray = photosarray
        for (i in 0..(photojsonarray.length() - 1)) {
            var tempphotoobject: JSONObject = photojsonarray.getJSONObject(i)
            imagesarray.add(
                ImageItem(
                    tempphotoobject.getString(FlickrSearchConstants.KEY_PHOTO_ID),
                    tempphotoobject.getString(FlickrSearchConstants.KEY_PHOTO_OWNER),
                    tempphotoobject.getString(FlickrSearchConstants.KEY_PHOTO_SECRET),
                    tempphotoobject.getString(FlickrSearchConstants.KEY_PHOTO_SERVER),
                    tempphotoobject.getString(FlickrSearchConstants.KEY_PHOTO_FARM),
                    tempphotoobject.getString(FlickrSearchConstants.KEY_PHOTO_TITLE)
                )
            )
        }
    }

    fun loadMore() {
        if (totalpage > current_page) {
            isLoading = true
            ImagefetchAsynctask(searchstring, current_page++, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        } else {
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
