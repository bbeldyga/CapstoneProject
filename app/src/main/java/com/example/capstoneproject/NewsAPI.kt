package com.example.capstoneproject

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.capstoneproject.databinding.FragmentFeedBinding
import com.squareup.picasso.Picasso
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class NewsAPI(activity: Activity) {
    private lateinit var imageView: ImageView

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        _binding = FragmentFeedBinding.inflate(inflater, container, false)
//        val view = binding.root
//        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
//
//        binding.homeButton.setOnClickListener {
//            val action = FeedFragmentDirections.actionFeedFragmentToHomeFragment()
//            view.findNavController().navigate(action)
//        }
//
//        val image = binding.newsImage
//        val client = OkHttpClient()
//        val request = Request.Builder()
//            .url("https://newsapi.org/v2/everything?q=Apple&apiKey=5e9f7c5f70c441378877ab85830ecdc2")
//            .build()
//
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                Log.e("NewsAPI", "Failed to fetch news: ${e.message}")
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                val responseData = response.body()?.string()
//                if (responseData != null) {
//                    val jsonObject = JSONObject(responseData)
//                    val articles = jsonObject.getJSONArray("articles")
//
//                    if (articles.length() > 0) {
//                        val firstArticle = articles.getJSONObject(0)
//                        val imageUrl = firstArticle.getString("urlToImage")
//
//                        activity?.runOnUiThread {
//                            Picasso.get().load(imageUrl).into(image)
//                        }
//                    }
//                }
//            }
//        })
//
//        return view
//    }
}
