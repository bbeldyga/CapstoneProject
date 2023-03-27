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
    private val feedSource = MutableList(7) {NewsResponse()}
    private val apiKeys = List(1) {"5e9f7c5f70c441378877ab85830ecdc2"}
    private var _textValue = MutableLiveData<String>("Loading your feed...")
    val textValue: LiveData<String>
        get() = _textValue
    private var _imageValue = MutableLiveData<String>("")
    val imageValue: LiveData<String>
        get() = _imageValue

    init {
        viewModelScope.launch(Dispatchers.Default) {
            withTimeout(5000) {
                getNews(apiKeys[0])
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

    private suspend fun getNews(apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val generalNews =
                async { connectToAPI("us", "general", apiKey) }
            val technologyNews =
                async { connectToAPI("us", "technology", apiKey) }
            val entertainmentNews =
                async { connectToAPI("us", "entertainment", apiKey) }
            val sportsNews =
                async { connectToAPI("us", "sports", apiKey) }
            val businessNews =
                async { connectToAPI("us", "business", apiKey) }
            val healthNews =
                async { connectToAPI("us", "health", apiKey) }
            val scienceNews =
                async { connectToAPI("us", "science", apiKey) }

            feedSource[0] = generalNews.await()
            feedSource[1] = technologyNews.await()
            feedSource[2] = entertainmentNews.await()
            feedSource[3] = sportsNews.await()
            feedSource[4] = businessNews.await()
            feedSource[5] = healthNews.await()
            feedSource[6] = scienceNews.await()
        }
            .join()
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

    companion object {
        fun provideFactory(
            newsAPI: NewsAPI,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FeedViewModel(newsAPI) as T
            }
        }
    }
}


//            withTimeout(5000){
//                feedSource += connectToAPI("us", "general","5e9f7c5f70c441378877ab85830ecdc2")
//                feedSource += connectToAPI("us", "technology","5e9f7c5f70c441378877ab85830ecdc2")
//                feedSource += connectToAPI("us", "entertainment","5e9f7c5f70c441378877ab85830ecdc2")
//                feedSource += connectToAPI("us", "sports","5e9f7c5f70c441378877ab85830ecdc2")
//                feedSource += connectToAPI("us", "business","5e9f7c5f70c441378877ab85830ecdc2")
//                feedSource += connectToAPI("us", "health","5e9f7c5f70c441378877ab85830ecdc2")
//                feedSource += connectToAPI("us", "science","5e9f7c5f70c441378877ab85830ecdc2")
//            }



