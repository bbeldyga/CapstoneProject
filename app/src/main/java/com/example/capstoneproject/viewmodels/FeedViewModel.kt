package com.example.capstoneproject.viewmodels

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import com.example.capstoneproject.dataobjects.Article
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
                    private val userPreferencesDAO: UserPreferencesDAO,
                    private val sharedPreferences: SharedPreferences
                    )
    : ViewModel() {

    private val email = FirebaseAuth.getInstance().currentUser?.email
    private val userPreferences = mutableListOf(3f, 3f, 3f, 3f, 3f, 3f, 3f)
    private val apiKeys = listOf("5e9f7c5f70c441378877ab85830ecdc2", "5006c6365e864383a603490ef274ebbd",
        "6826b8a5e9354246b702550b97f7af23")
    private val articleIndexCount = mutableListOf(0, 0, 0, 0, 0, 0, 0)
    private val feedSource = MutableList(7) {NewsResponse()}
    private val feedFiltered = mutableListOf(listOf<Article?>())
    private val categoryNames = listOf("General", "Technology", "Entertainment", "Sports", "Business", "Health", "Science")
    private val preferences = email?.let { userPreferencesDAO.get(it).map { preferences ->
                                                    savePreferences(preferences) } }

    private val errorHandler = CoroutineExceptionHandler {_, exception ->
        exception.printStackTrace() }
    private var categoryValue = -1
    private var countKey = "Count"

    private var _descriptionValue = MutableLiveData<String?>("Loading your feed...")
    val descriptionValue: LiveData<String?> get() = _descriptionValue

    private var _imageValue = MutableLiveData<String?>()
    val imageValue: LiveData<String?> get() = _imageValue

    private var _titleValue = MutableLiveData<String?>()
    val titleValue: LiveData<String?> get() = _titleValue

    private var _urlValue = MutableLiveData<String?>()
    val urlValue: LiveData<String?> get() = _urlValue

    private var _articleCount = MutableLiveData(0)
    val articleCount: LiveData<Int?> get() = _articleCount

    private var _home = MutableLiveData(false)
    val home: LiveData<Boolean> get() = _home

    private var _share = MutableLiveData(false)
    val share: LiveData<Boolean> get() = _share

    init {
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            withTimeout(5000) {
                getNews()
            }

            withContext(Dispatchers.Default) {
                filterNews()
            }

            withContext(Dispatchers.Main) {
                restoreIndexes()
                updateUI(0f)
            }
        }
    }

    fun updateUI(weight: Float) {
        val index = pickNextCategoryIndex()

        if (articleCount.value == 40) {
            return
        } else if (articleIndexCount[index] < feedFiltered[index].size) {

            _urlValue.value = feedFiltered[index][articleIndexCount[index]]?.url
            _titleValue.value = feedFiltered[index][articleIndexCount[index]]?.title
            _imageValue.value = feedFiltered[index][articleIndexCount[index]]?.urlToImage
            _descriptionValue.value = feedFiltered[index][articleIndexCount[index]++]?.description
            categoryValue = index
            updatePreferences(weight)
            _articleCount.value = _articleCount.value?.plus(1)
        } else {
            articleIndexCount[index]++
            updateUI(weight)
        }
    }

    fun updatePreferences(value: Float) {
            saveIndexes()

            userPreferences[categoryValue] += value
            if (userPreferences[categoryValue] > 10f)  {
                userPreferences[categoryValue] = 10f
            } else if (userPreferences[categoryValue] < 0f) {
                userPreferences[categoryValue] = 0f
            }
            val newPreferences = email?.let { UserPreferences(it, userPreferences[0], userPreferences[1],
                userPreferences[2], userPreferences[3], userPreferences[4],userPreferences[5], userPreferences[6]) }

        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            if (newPreferences != null) {
                userPreferencesDAO.update(newPreferences)
            }
        }
    }

    private fun filterNews() {
        for (i in 0 .. 6) {
            feedFiltered.add(i, feedSource[i].articles.filter { article ->
                    !article?.title.isNullOrBlank() && !article?.url.isNullOrBlank() &&
                            !article?.urlToImage.isNullOrBlank() && !article?.description.isNullOrBlank()
            })
        }
    }

    private fun saveIndexes() {
        val indexToSave = articleIndexCount[categoryValue]
        val count = _articleCount.value ?: 0

        sharedPreferences.edit().putInt(countKey, count).apply()
        sharedPreferences.edit().putInt(categoryNames[categoryValue],
            indexToSave).apply()
    }

    private fun restoreIndexes() {
        for (i in 0..6) {
            val indexToRestore = sharedPreferences.getInt(categoryNames[i], 0)
            articleIndexCount[i] = indexToRestore
        }

        _articleCount.value = sharedPreferences.getInt(countKey, 0)
    }

    private suspend fun getNews() {
        viewModelScope.launch(Dispatchers.IO+ errorHandler) {
            val generalNews =
                async { connectToAPI("us", "general", apiKeys.random()) }
            val technologyNews =
                async { connectToAPI("us", "technology", apiKeys.random()) }
            val entertainmentNews =
                async { connectToAPI("us", "entertainment", apiKeys.random()) }
            val sportsNews =
                async { connectToAPI("us", "sports", apiKeys.random()) }
            val businessNews =
                async { connectToAPI("us", "business", apiKeys.random()) }
            val healthNews =
                async { connectToAPI("us", "health", apiKeys.random()) }
            val scienceNews =
                async { connectToAPI("us", "science", apiKeys.random()) }

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

    fun shareButtonClicked() {
        _share.value = true
    }

    companion object {
        fun provideFactory(
            newsAPI: NewsAPI,
            userPreferencesDAO: UserPreferencesDAO,
            sharedPreferences: SharedPreferences
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FeedViewModel(newsAPI, userPreferencesDAO, sharedPreferences) as T
            }
        }
    }
}