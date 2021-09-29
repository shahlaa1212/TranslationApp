package com.example.translationtask.data


import com.google.gson.annotations.SerializedName

//class Languages : ArrayList<LanguagesItem>()
data class Languages ( @SerializedName("code")
                       val code: String?,
                       @SerializedName("name")
                       val name: String?)