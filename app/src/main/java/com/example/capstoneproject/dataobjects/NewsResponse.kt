package com.example.capstoneproject.dataobjects

import java.io.Serializable

data class NewsResponse (
    val status: String?,
    val totalResults: String?,
    val articles: List<Article>
) : Serializable {
    constructor() : this("", "", listOf(Article()))
}

data class Article (
    val source: Source,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
) : Serializable {
    constructor() : this(Source(), "","","","","","","")
}

data class Source (
    val id: String?,
    val name: String?
) : Serializable {
    constructor() : this("", "")
}