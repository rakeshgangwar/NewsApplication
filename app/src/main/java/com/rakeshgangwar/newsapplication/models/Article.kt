package com.rakeshgangwar.newsapplication.models

import androidx.room.Entity

@Entity(primaryKeys = ["publishedAt", "title"])
class Article(
    val author: String? = "",
    val title: String,
    val description: String? = "",
    val url: String? = "",
    val urlToImage: String? = "",
    val publishedAt: String,
    val content: String? = ""
)