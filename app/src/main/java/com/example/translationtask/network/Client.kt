package com.example.translationtask.network

import android.util.Log
import com.example.translationtask.data.Languages
import com.example.translationtask.data.Translate
import com.example.translationtask.ui.Status
import com.example.translationtask.util.Constant
import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

object Client {
    private val okHttpClient = OkHttpClient()
    private val gson = Gson()

    fun initRequest(q: String, source: String, target: String):  Status<Translate> {

        //  val urlokhttp = "https://translate.argosopentech.com/translate?q=book&source=en&target=ar"

        val url = HttpUrl.Builder()
            .scheme(Constant.SCHEME)
            .host(Constant.HOST)
            .addPathSegment(Constant.PATHSEGMENT)
            .addQueryParameter(Constant.QUERY, q)
            .addQueryParameter(Constant.SOURCE, source)
            .addQueryParameter(Constant.TARGET, target)
            .build()

        val request = Request.Builder().url(url).post(FormBody.Builder().build()).build()
        val response = okHttpClient.newCall(request).execute()

        return if (response.isSuccessful) {
            val parserResponse = gson.fromJson(
                response.body?.string(),
                Translate::class.java
            )
            Status.Success(parserResponse)
        } else {
            Status.Error(response.message)
        }
    }

    fun initRequestLanguages():  Status<Languages> {

        //  val urlokhttp = https://translate.argosopentech.com/languages

        val url = HttpUrl.Builder()
            .scheme(Constant.SCHEME)
            .host(Constant.HOST)
            .addPathSegment(Constant.PATHSEGMENTLANGUAGE)
            .build()

        val request = Request.Builder().url(url).build()
        val response = okHttpClient.newCall(request).execute()

        return if (response.isSuccessful) {
            val parserResponse = gson.fromJson(
                response.body?.string(),
                Languages::class.java
            )
            Status.Success(parserResponse)
        } else {
            Status.Error(response.message)
        }
    }

}