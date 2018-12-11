package com.redflower.flickrsearch.ui

import android.content.Intent
import android.databinding.DataBindingUtil
import android.media.Image
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.redflower.flickrsearch.R
import com.redflower.flickrsearch.interfaces.FlickrSearchActivityListener
import com.redflower.flickrsearch.databinding.ActivityFlickrSearchBinding
import com.redflower.flickrsearch.utils.NetworkUtil
import com.redflower.flickrsearch.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_flickr_search.*
import org.json.JSONObject

class FlickrSearchActivity : AppCompatActivity(), FlickrSearchActivityListener {
    override fun hasInternetConnection(): Boolean {
        return NetworkUtil.isNetworkAvailable(applicationContext)
    }

    override fun showAlert(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        loading_spinner.visibility = View.VISIBLE
        component_layout.visibility = View.GONE
    }

    override fun dismisLoading() {
        loading_spinner.visibility = View.GONE
        component_layout.visibility = View.VISIBLE
    }

    override fun callBack(value: String) {
        Log.d("kotlintest", value)
        val basejson: JSONObject = JSONObject(value)
        val photosjson = basejson.getJSONObject("photos")
        val photosarray = photosjson.getJSONArray("photo")
        val intent = Intent(this, ImageListingActivity::class.java)
        intent.putExtra("imgdata", value)
        intent.putExtra("page", 1)
        intent.putExtra("searchstring", searchmodel.searchkeys)
        startActivity(intent)
    }

    override fun onSearchClick() {
        searchmodel.validate()
    }

    lateinit var searchmodel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var flickractivitynbiding =
            DataBindingUtil.setContentView<ActivityFlickrSearchBinding>(this, R.layout.activity_flickr_search)
        searchmodel = SearchViewModel(this)
        flickractivitynbiding.search = searchmodel
        flickractivitynbiding.listener = this

    }
}
