package com.redflower.flickrsearch.interfaces

interface AsyncListener{
    fun onResponse(string: String?)
    fun onError()
}