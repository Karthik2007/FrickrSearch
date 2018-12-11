package com.redflower.flickrsearch.interfaces

interface FlickrSearchActivityListener{
    fun showAlert(message: String)
    fun onSearchClick()
    fun onImages(value: String)
    fun showLoading()
    fun hasInternetConnection(): Boolean
    fun dismisLoading()
}