package com.example.translationtask.repository

import com.example.translationtask.data.Languages
import com.example.translationtask.data.Translate
import com.example.translationtask.network.Client
import com.example.translationtask.util.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object TranslateRepository {
    fun getInfoTrans(q: String, source: String, target: String) = flow<Status<Translate>>{
        emit(Status.Loading)
        emit(Client.initRequest(q, source, target))
    }.flowOn(Dispatchers.IO)

    fun getInfoLanguage() = flow<Status<Languages>>{
        val result = Client.initRequestLanguages()
        // emit(result)
        //emit(Status.Loading)
        //emit(Client.initRequestLanguages())
    }.flowOn(Dispatchers.IO)

    lateinit var sourceLanguage : String
    lateinit var TargetLanguage : String

    val languageList = mutableListOf<Languages>()

    fun initLanguageList(languages: List<Languages>){
    languageList.addAll(languages)
    }
    val languages get() = languageList

}