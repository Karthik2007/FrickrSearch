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
        val string: String? = ApiUtils.createhttprequest(ApiUtils.buildUrl(searchstring,page))
//        var jsonObject = JSONObject(string)
//        var photojson: JSONObject = jsonObject.getJSONObject("photos")
//        var jsonArray: JSONArray = photojson.getJSONArray("photo")
//        for (i in 0..(jsonArray.length()-1)){
//            var jsonObject: JSONObject = jsonArray[i] as JSONObject
//            jsonObject.put("url","https://farm${jsonObject.getInt("farm")}.staticflickr.com/${jsonObject.getString("server")}/${jsonObject.getString("id")}_${jsonObject.getString("secret")}_z.jpg")
//            Log.d("urls", "https://farm${jsonObject.getInt("farm")}.staticflickr.com/${jsonObject.getString("server")}/${jsonObject.getString("id")}_${jsonObject.getString("secret")}_z.jpg")
//        }
//        ApiUtils.jsonArray = jsonArray
        return string
    }

    override fun onPostExecute(result: String?) {
        asyncListener.onResponse(result)
        super.onPostExecute(result)
    }
}