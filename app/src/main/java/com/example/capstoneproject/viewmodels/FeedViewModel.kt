package com.example.capstoneproject.viewmodels
import android.util.Log
import androidx.lifecycle.*
import com.example.capstoneproject.interfaces.NewsAPI
import com.example.capstoneproject.dataobjects.NewsResponse
import kotlinx.coroutines.*
import kotlin.random.Random

/**
 * Feed Screen Data and Logic
 */
class FeedViewModel(private val newsAPI: NewsAPI): ViewModel() {

    private var count = 0
    private val feedSource = mutableListOf<NewsResponse>()

    private var _textValue = MutableLiveData<String>("")
    val textValue: LiveData<String>
        get() = _textValue
    private var _imageValue = MutableLiveData<String>("")
    val imageValue: LiveData<String>
        get() = _imageValue

    init {
        viewModelScope.launch(Dispatchers.IO) {
            withTimeout(5000){
                feedSource += connectToAPI("us", "general","5e9f7c5f70c441378877ab85830ecdc2")
                feedSource += connectToAPI("us", "technology","5e9f7c5f70c441378877ab85830ecdc2")
                feedSource += connectToAPI("us", "entertainment","5e9f7c5f70c441378877ab85830ecdc2")
                feedSource += connectToAPI("us", "sports","5e9f7c5f70c441378877ab85830ecdc2")
                feedSource += connectToAPI("us", "business","5e9f7c5f70c441378877ab85830ecdc2")
                feedSource += connectToAPI("us", "health","5e9f7c5f70c441378877ab85830ecdc2")
                feedSource += connectToAPI("us", "science","5e9f7c5f70c441378877ab85830ecdc2")
            }
            withContext(Dispatchers.Main) {
                updateUI()
            }
        }

    }

    fun updateUI() {
        val index = Random.nextInt(0, 6)
        if (feedSource[index].articles[count].urlToImage != null &&
            feedSource[index].articles[count].description != null
        ) {
            _imageValue.value = feedSource[index].articles[count].urlToImage!!
            _textValue.value = feedSource[index].articles[count++].description!!
            return
        } else {
            count++
            updateUI()
        }
    }

    private suspend fun connectToAPI(country: String, category: String, apiKey: String): NewsResponse {
        try {
            val response = newsAPI.getArticles(country, category, apiKey)
            return if (response.isSuccessful) {
                response.body()!!
            } else {
                NewsResponse()
            }
        } catch (e: Exception) {
            Log.e("NewsAPI", "Failed to fetch news: ${e.message}")
        }
        return NewsResponse()
    }
}





