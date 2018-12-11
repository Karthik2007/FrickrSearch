package com.redflower.flickrsearch.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.util.LruCache
import android.widget.ImageView
import java.net.URL
import java.net.URLConnection

object ImageUtil {
    private lateinit var mMemoryCache: LruCache<String, Bitmap>

    init{
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()

        // Use 1/8th of the available memory for this memory cache.
        val cacheSize = maxMemory / 8
        Log.d("memorych"," $cacheSize")
        mMemoryCache = object : LruCache<String, Bitmap>(cacheSize) {

            override fun sizeOf(key: String, bitmap: Bitmap): Int {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.byteCount / 1024
            }
        }
    }

    fun setBitmap(imageview: ImageView, url: String) {
        if(getBitmapFromMemCache(url)!=null){
            var bitmap: Bitmap = getBitmapFromMemCache(url) as Bitmap
            imageview.setImageBitmap(bitmap)
        } else{
            Imagefetchtask(url, imageview).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        }
    }

    fun addBitmapToMemoryCache(key: String, bitmap: Bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap)
        }
    }

    fun getBitmapFromMemCache(key: String): Bitmap? {
        return mMemoryCache.get(key)
    }

    class Imagefetchtask(var url: String, var imageview: ImageView) : AsyncTask<Unit, Unit, Bitmap>() {
        lateinit var bitmap: Bitmap
        override fun doInBackground(vararg params: Unit?): Bitmap {
                var urls: URL = URL(url)
                var urlConnection: URLConnection = urls.openConnection()
                bitmap = BitmapFactory.decodeStream(urlConnection.getInputStream())
                addBitmapToMemoryCache(url,bitmap)
                return bitmap
        }

        override fun onPostExecute(result: Bitmap?) {
            imageview.setImageBitmap(result)
            super.onPostExecute(result)
        }
    }


}
