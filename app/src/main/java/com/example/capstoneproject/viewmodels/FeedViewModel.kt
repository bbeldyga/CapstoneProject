package com.example.capstoneproject.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.capstoneproject.interfaces.NewsAPI
import com.example.capstoneproject.dataobjects.NewsResponse
import com.example.capstoneproject.dataobjects.UserPreferences
import com.example.capstoneproject.interfaces.UserPreferencesDAO
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*
import kotlin.random.Random

/**
 * Feed Screen Data and Logic
 */
class FeedViewModel(private val newsAPI: NewsAPI,
                    private val userPreferencesDAO: UserPreferencesDAO)
    : ViewModel() {

    private val email = FirebaseAuth.getInstance().currentUser?.email
    private val userPreferences = mutableListOf(3f, 3f, 3f, 3f, 3f, 3f, 3f)
    private val preferences = email?.let { userPreferencesDAO.get(it).map { preferences ->
                                                    savePreferences(preferences) } }

    private val articleIndexCount = mutableListOf(0, 0, 0, 0, 0, 0, 0)
    var categoryValue = -1
    var exploreCheck = true

    private val feedSource = MutableList(7) {NewsResponse()}
    private val apiKeys = mutableListOf("5e9f7c5f70c441378877ab85830ecdc2")

    private var _descriptionValue = MutableLiveData<String>("Loading your feed...")
    val descriptionValue: LiveData<String> get() = _descriptionValue

    private var _imageValue = MutableLiveData<String>()
    val imageValue: LiveData<String> get() = _imageValue

    private var _titleValue = MutableLiveData<String>()
    val titleValue: LiveData<String> get() = _titleValue

    private var _urlValue = MutableLiveData<String>()
    val urlValue: LiveData<String> get() = _urlValue

    private var _articleCount = MutableLiveData(0)
    val articleCount: LiveData<Int> get() = _articleCount

    init {
        viewModelScope.launch(Dispatchers.IO) {
            withTimeout(5000) {
                getNews(apiKeys[0])
            }

            withContext(Dispatchers.Main) {
                updateUI(0f)
            }
        }
    }

    fun updateUI(weight: Float) {
        val index = pickNextCategoryIndex()

        if (articleCount.value == 40) {
            return
        } else if (feedSource[index].articles[articleIndexCount[index]].url != null &&
            feedSource[index].articles[articleIndexCount[index]].title != null &&
            feedSource[index].articles[articleIndexCount[index]].urlToImage != null &&
            feedSource[index].articles[articleIndexCount[index]].description != null &&
            articleIndexCount[index] < 20) {

            _urlValue.value = feedSource[index].articles[articleIndexCount[index]].url!!
            _titleValue.value = feedSource[index].articles[articleIndexCount[index]].title!!
            _imageValue.value = feedSource[index].articles[articleIndexCount[index]].urlToImage!!
            _descriptionValue.value = feedSource[index].articles[articleIndexCount[index]++].description!!
            updatePreferences(index, weight)
            exploreCheck = true
            _articleCount.value = _articleCount.value?.plus(1)
            return
        } else if (articleIndexCount[index] == 20){
            updateUI(weight)
        } else {
            articleIndexCount[index]++
            updateUI(weight)
        }
    }

    fun updatePreferences(index: Int, value: Float) {
            userPreferences[index] += value
            if (userPreferences[index] > 10f)  {
                userPreferences[index] = 10f
            } else if (userPreferences[index] < 0f) {
                userPreferences[index] = 0f
            }
            val newPreferences = email?.let { UserPreferences(it, userPreferences[0], userPreferences[1],
                userPreferences[2], userPreferences[3], userPreferences[4],userPreferences[5], userPreferences[6]) }

        viewModelScope.launch(Dispatchers.IO) {
            if (newPreferences != null) {
                userPreferencesDAO.update(newPreferences)
            }
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
        return try {
            val response = newsAPI.getArticles(country, category, apiKey)
            if (response.isSuccessful) {
                response.body()!!
            } else {
                NewsResponse()
            }
        } catch (e: Exception) {
            Log.e("NewsAPI", "Failed to fetch news: ${e.message}")
            NewsResponse()
        }
    }

    private fun savePreferences(preferences: UserPreferences) {
        userPreferences[0] = preferences.generalPreference
        userPreferences[1] = preferences.technologyPreference
        userPreferences[2] = preferences.entertainmentPreference
        userPreferences[3] = preferences.sportsPreference
        userPreferences[4] = preferences.businessPreference
        userPreferences[5] = preferences.healthPreference
        userPreferences[6] = preferences.sciencePreference
    }

    private fun pickNextCategoryIndex(): Int {
        val end = (userPreferences.sum() * 10).toInt()
        val adjustedPreferences = listOf((userPreferences[0] * 10), (userPreferences[1] * 10),
            (userPreferences[2] * 10), (userPreferences[3] * 10), (userPreferences[4] * 10),
            (userPreferences[5] * 10), (userPreferences[6] * 10))

        val random = Random.nextInt(1, end)

        val intervalOne = adjustedPreferences[0].toInt()
        val intervalTwo = intervalOne + adjustedPreferences[1].toInt()
        val intervalThree = intervalTwo + adjustedPreferences[2].toInt()
        val intervalFour = intervalThree + adjustedPreferences[3].toInt()
        val intervalFive = intervalFour + adjustedPreferences[4].toInt()
        val intervalSix = intervalFive + adjustedPreferences[5].toInt()

        val categoryIndex = when (random) {
            in 1 .. (intervalOne) -> 0
            in (intervalOne + 1) .. (intervalTwo) -> 1
            in (intervalTwo + 1) .. (intervalThree) -> 2
            in (intervalThree + 1) .. (intervalFour) -> 3
            in (intervalFour + 1) .. (intervalFive) -> 4
            in (intervalFive + 1) .. (intervalSix) -> 5
            in (intervalSix + 1) .. (end) -> 6
            else -> 0
        }

        return categoryIndex
    }

    companion object {
        fun provideFactory(
            newsAPI: NewsAPI,
            userPreferencesDAO: UserPreferencesDAO
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FeedViewModel(newsAPI, userPreferencesDAO) as T
            }
        }
    }
}