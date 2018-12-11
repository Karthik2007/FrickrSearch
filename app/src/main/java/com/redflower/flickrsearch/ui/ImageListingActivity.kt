package com.redflower.flickrsearch.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.ThemedSpinnerAdapter
import android.util.Log
import android.view.View
import android.widget.Toast
import com.redflower.flickrsearch.R
import com.redflower.flickrsearch.adapter.ImageListingAdapter
import com.redflower.flickrsearch.interfaces.ImageListingActivityListener
import com.redflower.flickrsearch.model.ImageItem
import com.redflower.flickrsearch.utils.FlickrSearchConstants
import com.redflower.flickrsearch.utils.NetworkUtil
import com.redflower.flickrsearch.viewmodel.ImageListingViewModel
import kotlinx.android.synthetic.main.image_listing_view.*
import org.json.JSONArray
import org.json.JSONObject

class ImageListingActivity : AppCompatActivity(), ImageListingActivityListener {

    lateinit var imagelistingVM: ImageListingViewModel
    lateinit var imagelistRecycler: RecyclerView
    lateinit var gridlayoutmanager: RecyclerView.LayoutManager
    lateinit var imgadapter: ImageListingAdapter
    var pastVisiblesItems: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image_listing_view)
        val imagearray = intent.getStringExtra(FlickrSearchConstants.EXTRA_IMAGEDATA)
        val searchString = intent.getStringExtra(FlickrSearchConstants.EXTRA_SEARCHSTRING)
        imagelistingVM = ImageListingViewModel(imagearray, searchString, this)
        initizeRecyclerview()
    }

    fun initizeRecyclerview() {
        imagelistRecycler = findViewById(R.id.imagelisting) as RecyclerView
        gridlayoutmanager = GridLayoutManager(applicationContext, 3)
        imgadapter = ImageListingAdapter(imagelistingVM.imagesarray, applicationContext)

        imagelistRecycler.apply {
            layoutManager = gridlayoutmanager
            adapter = imgadapter
            addOnScrollListener(onScrollListeners)
        }
    }


    //to check Recyclerview reached bottom
    var onScrollListeners: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            visibleItemCount = recyclerView.layoutManager?.childCount as Int
            totalItemCount = recyclerView.layoutManager?.itemCount as Int
            pastVisiblesItems = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                if (!imagelistingVM.isLoading) {
                    loadMore()
                }
            }
        }
    }

    override fun notifydatasetchange() {
        loading_next_page_layout.visibility = View.GONE
        imgadapter.notifyDataSetChanged()
    }

    fun loadMore() {
        if (NetworkUtil.isNetworkAvailable(applicationContext)) {
            loading_next_page_layout.bringToFront()
            loading_next_page_layout.visibility = View.VISIBLE
            imagelistingVM.loadMore()
        } else {
            loading_next_page_layout.bringToFront()
            loading_next_page_layout.visibility = View.VISIBLE
            showAlert(FlickrSearchConstants.NO_NETWORK)
        }
    }

    fun showAlert(alertstring: String) {
        Toast.makeText(applicationContext, alertstring, Toast.LENGTH_SHORT).show()
    }


}
