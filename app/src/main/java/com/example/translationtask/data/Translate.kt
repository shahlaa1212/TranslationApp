package com.example.translationtask.data


import com.google.gson.annotations.SerializedName

data class Translate(
    @SerializedName("translatedText")
    val translatedText: String?
)