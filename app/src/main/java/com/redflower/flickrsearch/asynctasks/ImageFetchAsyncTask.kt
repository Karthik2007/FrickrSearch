package com.redflower.flickrsearch.asynctasks

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.redflower.flickrsearch.interfaces.AsyncListener
import com.redflower.flickrsearch.utils.ApiUtils
import org.json.JSONArray
import org.json.JSONObject

class ImagefetchAsynctask(var searchstring: String,var page: Int,var asyncListener: AsyncListener): AsyncTask<Unit, Unit, String?>() {
    override fun doInBackground(vararg params: Unit?): String? {
        return  ApiUtils.createhttprequest(ApiUtils.buildUrl(searchstring,page))
    }

    override fun onPostExecute(result: String?) {
        asyncListener.onResponse(result)
        super.onPostExecute(result)
    }
}