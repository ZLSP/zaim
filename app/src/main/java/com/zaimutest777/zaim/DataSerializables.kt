package com.zaimutest777.zaim

import kotlinx.serialization.Serializable

@Serializable
data class CheckLink(
    val flag: String,
    val url: String,
    val ip: String
)
@Serializable
data class Cards(
    val title: String,
    val imageUrl: String,
    val url: String,
    val summText: String,
    val betText: String,
    val score: String,
    val mastercard: String,
    val mir: String,
    val visa: String,
    val qiwi: String,
    val yandex: String,
    val cash: String,
    val description: String,
)
@Serializable
data class Phones(
    val number: String
)