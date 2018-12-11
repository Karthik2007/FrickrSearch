package com.redflower.flickrsearch.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.redflower.flickrsearch.BR
import com.redflower.flickrsearch.asynctasks.ImagefetchAsynctask
import com.redflower.flickrsearch.interfaces.AsyncListener
import com.redflower.flickrsearch.interfaces.FlickrSearchActivityListener
import com.redflower.flickrsearch.utils.FlickrSearchConstants
import org.json.JSONException
import org.json.JSONObject
import java.net.URLEncoder

class SearchViewModel(var listener: FlickrSearchActivityListener) : BaseObservable(), AsyncListener {
    var searchkeys: String = ""

    override fun onResponse(string: String?) {
        listener.dismisLoading()

        if (validateAvailablity(string)) {
            listener.onImagesResponse(string as String)
        } else {
            listener.showAlert(FlickrSearchConstants.NO_IMAGES_FOUND)
        }
    }

    fun validateAvailablity(string: String?): Boolean {
        string ?: return false
        try {
            val basejson: JSONObject = JSONObject(string)
            val photosjson = basejson.getJSONObject(FlickrSearchConstants.KEY_PHOTO_JSON)
            val totalimages: Int = photosjson.getInt(FlickrSearchConstants.KEY_PHOTO_TOTAL_IMAGES_COUNT)
            if (totalimages > 0) return true
        } catch (e: JSONException) {
            e.printStackTrace()
            return false
        }
        return false
    }

    override fun onError() {
        listener.showAlert(FlickrSearchConstants.SOMETHING_WENT_WRONG)
    }

    @Bindable
    fun getsearchkeys(): String {
        return searchkeys
    }

    fun setsearchkeys(searchkeys: String) {
        this.searchkeys = searchkeys
        notifyPropertyChanged(BR.searchkeys)
    }

    fun validate() {
        if (searchkeys == "") {
            listener.showAlert(FlickrSearchConstants.ENTER_TEXT)
        } else {
            if (listener.hasInternetConnection()) {
                ImagefetchAsynctask(URLEncoder.encode(searchkeys, "UTF-8"), 1, this).execute()
                listener.showLoading()
            } else {
                listener.showAlert(FlickrSearchConstants.NO_NETWORK)
            }
        }
    }

}
