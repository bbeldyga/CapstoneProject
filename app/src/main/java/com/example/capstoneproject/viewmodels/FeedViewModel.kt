package com.example.capstoneproject.viewmodels
import android.util.Log
import androidx.lifecycle.*
import com.example.capstoneproject.interfaces.NewsAPI
import com.example.capstoneproject.dataobjects.NewsResponse
import com.example.capstoneproject.interfaces.UserPreferencesDAO
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*
import kotlin.random.Random

/**
 * Feed Screen Data and Logic
 */
class FeedViewModel(private val newsAPI: NewsAPI,
                    private val userPreferencesDAO: UserPreferencesDAO,
                    private val firebaseAuth: FirebaseAuth): ViewModel() {

    private var count = 0
    private val email = firebaseAuth.currentUser?.email
    private val userPreferences = mutableListOf(3, 3, 3, 3, 3, 3, 3)

    private val feedSource = MutableList(7) {NewsResponse()}
    private val apiKeys = List(1) {"5e9f7c5f70c441378877ab85830ecdc2"}

    private var _descriptionValue = MutableLiveData<String>("Loading your feed...")
    val descriptionValue: LiveData<String>
        get() = _descriptionValue
    private var _imageValue = MutableLiveData<String>("")
    val imageValue: LiveData<String>
        get() = _imageValue
    private var _titleValue = MutableLiveData<String>("")
    val titleValue: LiveData<String>
        get() = _titleValue

    init {
        viewModelScope.launch {
            withTimeout(5000) {
                getNews(apiKeys[0])
            }

            retrieveUserPreferences(email!!)

            withContext(Dispatchers.Main) {
                updateUI()
            }
        }
    }

    fun updateUI() {
        val index = Random.nextInt(0, 6)
        if (feedSource[index].articles[count].title != null &&
            feedSource[index].articles[count].urlToImage != null &&
            feedSource[index].articles[count].description != null
        ) {
            _titleValue.value = feedSource[index].articles[count].title!!
            _imageValue.value = feedSource[index].articles[count].urlToImage!!
            _descriptionValue.value = feedSource[index].articles[count++].description!!
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

    private fun retrieveUserPreferences(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentUserPreferences = userPreferencesDAO.get(email)

            userPreferences[0] = currentUserPreferences.generalPreference
            userPreferences[1] = currentUserPreferences.technologyPreference
            userPreferences[2] = currentUserPreferences.entertainmentPreference
            userPreferences[3] = currentUserPreferences.sportsPreference
            userPreferences[4] = currentUserPreferences.businessPreference
            userPreferences[5] = currentUserPreferences.healthPreference
            userPreferences[6] = currentUserPreferences.sciencePreference
        }
    }

    companion object {
        fun provideFactory(
            newsAPI: NewsAPI,
            userPreferencesDAO: UserPreferencesDAO,
            firebaseAuth: FirebaseAuth
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FeedViewModel(newsAPI, userPreferencesDAO, firebaseAuth) as T
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



