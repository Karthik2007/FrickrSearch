package com.redflower.flickrsearch.adapter

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.redflower.flickrsearch.R
import com.redflower.flickrsearch.model.ImageItem
import com.redflower.flickrsearch.utils.ImageUtil
import java.util.*


class ImageListingAdapter(val imageArray: ArrayList<ImageItem>, var context: Context) :
    RecyclerView.Adapter<ImageListingAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        val relativeLayout: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_listing_item, parent, false) as View
        return MyViewHolder(relativeLayout)
    }

    override fun getItemCount(): Int = imageArray.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        GlideApp.with(context)
//            .load(URL(imageArray.get(position).getimageurl()))
//            .placeholder(R.drawable.gridimagebg)
//            .into(holder.imageView)
        holder.loadImage(imageArray.get(position))

    }

    class MyViewHolder(val option_item: View) : RecyclerView.ViewHolder(option_item) {
        lateinit var imageView: ImageView
        private lateinit var imageItem: ImageItem

        init {
            imageView = option_item.findViewById(R.id.img) as ImageView
        }

        fun loadImage(imageItem: ImageItem) {
            this.imageItem = imageItem
            imageView.setImageBitmap(null)
            ImageUtil.setBitmap(imageView, this.imageItem.getimageurl())
        }
    }


}
