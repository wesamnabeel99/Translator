package com.example.translator.data.network

import android.util.Log
import com.example.translator.data.Status
import com.example.translator.data.response.TranslatedText
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.wait

object Client {
    private val okHttpClient = OkHttpClient()

    private val gson = Gson()

    fun getTranslation(sentence : String) : Status<TranslatedText> {
        val formBody = FormBody.Builder()
            .add("q","hello")
            .add("source","en")
            .add("target","ar").build()
        val request = Request.Builder().url("https://translate.argosopentech.com/translate").post(
        formBody
        ).build()
        val response = okHttpClient.newCall(request).execute()
        return if (response.isSuccessful) {
            val jsonParser = gson.fromJson(response.body?.string(),TranslatedText::class.java)
            Status.Success(jsonParser)
        } else {
            Status.Error(response.message)
        }
    }
//    fun getLangauesList() : Status<ArrayList<TranslatedText>> {
//        val request = Request.Builder.url().build()
//        val response = okHttpClient.newCall(request).execute()
//        return if (response.isSuccessful) {
//            val jsonParser = gson.fromJson(
//                response.body?.string(),
//                ArrayList<TranslatedText>::class.java
//            )
//            Status.Success(jsonParser)
//        } else {
//            Status.Error(response.message)
//        }
//    }

}