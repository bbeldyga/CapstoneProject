package com.example.capstoneproject
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Feed Screen Data and Logic
 */
class FeedViewModel(private val newsAPI: NewsAPI): ViewModel() {

    val technologyURL =
        "?country=us&category=technology&apiKey=5e9f7c5f70c441378877ab85830ecdc2"
    val sportsURL =
        "https://newsapi.org/v2/top-headlines?country=us&category=sports&apiKey=5e9f7c5f70c441378877ab85830ecdc2"
    val generalURL =
        "https://newsapi.org/v2/top-headlines?country=us&category=general&apiKey=5e9f7c5f70c441378877ab85830ecdc2"
    val entertainmentURL =
        "https://newsapi.org/v2/top-headlines?country=us&category=entertainment&apiKey=5e9f7c5f70c441378877ab85830ecdc2"
    val businessURL =
        "https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=5e9f7c5f70c441378877ab85830ecdc2"
    val healthURL =
        "https://newsapi.org/v2/top-headlines?country=us&category=health&apiKey=5e9f7c5f70c441378877ab85830ecdc2"
    val scienceURL =
        "https://newsapi.org/v2/top-headlines?country=us&category=science&apiKey=5e9f7c5f70c441378877ab85830ecdc2"
    var count = 0
    var feedSource = NewsResponse()

    private var _textValue = MutableLiveData<String>("")
    val textValue: LiveData<String>
        get() = _textValue
    private var _imageValue = MutableLiveData<String>("")
    val imageValue: LiveData<String>
        get() = _imageValue

    init {
        connectToAPI("us", "general", "5e9f7c5f70c441378877ab85830ecdc2")
    }

    fun updateUI() {
        if (feedSource.articles[count].urlToImage != null ||
            feedSource.articles[count++].description != null) {
            _imageValue.value = feedSource.articles[count].urlToImage
            _textValue.value = feedSource.articles[count++].description
        } else {
            count++
        }
    }

    private fun connectToAPI(country: String, category: String, apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = newsAPI.getArticles(country, category, apiKey)
                if (response.isSuccessful) {
                    feedSource = response.body()!!
                    _imageValue.postValue(response.body()!!.articles[count].urlToImage)
                    _textValue.postValue(response.body()!!.articles[count++].description)
                }
            } catch (e: Exception) {
                Log.e("NewsAPI", "Failed to fetch news: ${e.message}")
            }
        }
    }

//    private fun sortFeed() {
//        val removeArticles = intArrayOf()
//        for (i in 1..feedSource.articles.count()) {
//            if (feedSource.articles[i].description == null ||
//                    feedSource.articles[i].urlToImage == null) {
//                removeArticles.plus(i)
//            }
//        }
//
//        for (article in removeArticles) {
//            feedSource.articles.
//        }
//    }
}

//    fun connectToAPI(country: String, category: String, apiKey: String) {
//        //var newsResponse = NewsResponse()
//        val call: Call<NewsResponse> = newsAPI.getArticles(country, category, apiKey)
//        call.enqueue(object : Callback<NewsResponse> {
//            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
//                if (response.isSuccessful) {
//                    feedSource = response.body()!!
//                } else {
//                    // Handle the error
//                }
//            }
//
//            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
//                // Handle the error
//            }
//        })
//    }
//val response = try {
//    newsAPI.getArticles(country, category, apiKey)
//} catch (e: Exception) {
//    Log.e("NewsAPI", "Failed to fetch news: ${e.message}")
//}
//when (response) {
//    is Response.isSuccessful<NewsResponse> -> _textValue.postValue(response.body()!!.articles[count++].description!!)
//
//}
//    companion object {
//
//        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
//            @Suppress("UNCHECKED_CAST")
//            override fun <T : ViewModel> create(
//                modelClass: Class<T>,
//                extras: CreationExtras
//            ): T {
//
//            }
//        }
//    }



