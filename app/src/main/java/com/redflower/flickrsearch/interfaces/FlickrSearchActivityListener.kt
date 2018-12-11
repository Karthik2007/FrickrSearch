package com.redflower.flickrsearch.interfaces

interface FlickrSearchActivityListener{
    fun showAlert(message: String)
    fun onSearchClick()
    fun onImagesResponse(value: String)
    fun showLoading()
    fun hasInternetConnection(): Boolean
    fun dismisLoading()
}