package com.example.capstoneproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newsGetter = NewsAPI()

        //val parseThis = newsGetter.connectToAPI()

        //We Would Parse the API Response and Find The Image URL for Below
        val imageUrl = "https://media.wired.com/photos/627af5ce194f8820f344ac62/191:100/w_1280,c_limit/Pixel-6a-SOURCE-Google-Gear.jpg"
        val myImageView = findViewById<ImageView>(R.id.newsImage)
        loadImageFromUrl(imageUrl, myImageView)
    }

    fun loadImageFromUrl(url: String, imageView: ImageView) {
        Picasso.get().load(url).into(imageView)
    }
}