package com.example.capstoneproject

import java.net.HttpURLConnection
import java.net.URL

class NewsAPI {
    fun connectToAPI(): String {
        val url = URL("https://newsapi.org/v2/everything?q=Apple&apiKey=5e9f7c5f70c441378877ab85830ecdc2")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        connection.setRequestProperty("User-Agent", "Mozilla/5.0")

        val responseCode = connection.responseCode
        println("Response code: $responseCode")

        val inputStream = connection.inputStream
        val responseBody = inputStream.bufferedReader().use { it.readText() }
        println("Response body: $responseBody")

        connection.disconnect()

        return responseBody
    }
}