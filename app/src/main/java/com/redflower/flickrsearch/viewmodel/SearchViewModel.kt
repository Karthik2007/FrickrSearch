package com.redflower.flickrsearch.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.redflower.flickrsearch.BR
import com.redflower.flickrsearch.asynctasks.ImagefetchAsynctask
import com.redflower.flickrsearch.interfaces.AsyncListener
import com.redflower.flickrsearch.interfaces.FlickrSearchActivityListener
import com.redflower.flickrsearch.utils.FlickrSearchConstants
import org.json.JSONObject
import java.net.URLEncoder

class SearchViewModel(var listener: FlickrSearchActivityListener) : BaseObservable(), AsyncListener {
    var searchkeys: String = ""


    override fun onResponse(string: String?) {
        listener.dismisLoading()

        if(validateAvailablity(string)) {
            listener.onImages(string as String)
        }else{
            listener.showAlert(FlickrSearchConstants.NO_IMAGES_FOUND)
        }
    }
    fun validateAvailablity(string: String?): Boolean{
        string?:return false
        val basejson: JSONObject = JSONObject(string)
        val photosjson = basejson.getJSONObject("photos")
        val totalimages: Int = photosjson.getInt("total")
        if(totalimages>0) return true
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
