package com.redflower.flickrsearch.model


class ImageItem {
    var id: String
    var ower: String
    var secret: String
    var server: String
    var farm: String
    var title: String

    constructor(id: String, ower: String, secret: String, server: String, farm: String, title: String) {
        this.id = id
        this.ower = ower
        this.secret = secret
        this.server = server
        this.farm = farm
        this.title = title
    }

    //images for fetch single image
    fun getimageurl(): String {
        return "https://farm$farm.staticflickr.com/$server/${id}_${secret}_n.jpg"
    }
}