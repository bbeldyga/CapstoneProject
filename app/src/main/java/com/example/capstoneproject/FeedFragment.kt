package com.example.capstoneproject

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.capstoneproject.databinding.FragmentFeedBinding
import com.squareup.picasso.Picasso
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

/**
 * Feed Screen
 */
class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: FeedViewModel
    private lateinit var sharedPreferences: SharedPreferences //API Response Storage

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)


        //API Code
        var count = 0
        sharedPreferences = requireActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        val image = binding.newsImage
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://newsapi.org/v2/everything?q=Apple&apiKey=5e9f7c5f70c441378877ab85830ecdc2")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("NewsAPI", "Failed to fetch news: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body()?.string()
                sharedPreferences.edit().putString("lastResponse", responseData).apply()

                if (responseData != null) {
                    val jsonObject = JSONObject(responseData)
                    val articles = jsonObject.getJSONArray("articles")

                    if (articles.length() > 0) {
                        val firstArticle = articles.getJSONObject(count++)
                        val imageUrl = firstArticle.getString("urlToImage")

                        activity?.runOnUiThread {
                            Picasso.get().load(imageUrl).into(image)
                        }
                    }
                }
            }
        }) //End of API Code

        binding.homeButton.setOnClickListener {
            val savedResponse = sharedPreferences.getString("lastResponse", null)

            if (savedResponse != null) {
                val jsonObject = JSONObject(savedResponse)
                val articles = jsonObject.getJSONArray("articles")

                if (articles.length() > 0) {
                    val firstArticle = articles.getJSONObject(count++)
                    val imageUrl = firstArticle.getString("urlToImage")

                    activity?.runOnUiThread {
                        Picasso.get().load(imageUrl).into(image)
                    }
                }

            /*val action = FeedFragmentDirections.actionFeedFragmentToHomeFragment()
            view.findNavController().navigate(action)*/
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}