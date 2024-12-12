package com.example.kiddoshine.api.response

data class Article(
    val id: Int,
    val title: String,
    val content: String,
    val author: String,
    val publishedAt: String
)