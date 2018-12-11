package com.redflower.flickrsearch.interfaces

interface FlickrSearchActivityListener{
    fun showAlert(message: String)
    fun onSearchClick()
    fun callBack(value: String)
    fun showLoading()
    fun hasInternetConnection(): Boolean
    fun dismisLoading()
}