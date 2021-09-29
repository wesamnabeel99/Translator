package com.example.translator.data.repository

import com.example.translator.data.Status
import com.example.translator.data.network.Client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


object TranslationRepository {
    fun getTranslation (sentence : String ) = flow {
        emit(Status.Loading)
        emit(Client.getTranslation(sentence))
    }.flowOn(Dispatchers.IO)
}