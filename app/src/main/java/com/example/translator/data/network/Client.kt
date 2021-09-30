package com.example.translator.data.network

import android.util.Log
import androidx.core.graphics.PathSegment
import com.example.translator.data.Status
import com.example.translator.data.response.Language
import com.example.translator.data.response.LanguagesResponse
import com.example.translator.data.response.TranslatedText
import com.example.translator.data.util.Constants
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.wait
import java.net.URL

object Client {
    private val okHttpClient = OkHttpClient()
    private val baseUrl = "https://translate.argosopentech.com/translate"
    private val gson = Gson()

    fun getTranslation(sentence : String,sourceLanguage:String,targetLanguage:String) : Status<TranslatedText> {
        val formBody = FormBody.Builder()
            .add(Constants.Q,sentence)
            .add(Constants.SOURCE,sourceLanguage)
            .add(Constants.TARGET,targetLanguage).build()

        val request = Request.Builder().url(urlBuilder(pathSegment = Constants.TRANSLATE)).post(formBody).build()
        val response = okHttpClient.newCall(request).execute()
        if (response.isSuccessful) {
            val jsonParser = gson.fromJson(response.body?.string(),TranslatedText::class.java)
            return Status.Success(jsonParser)
        } else {
            return Status.Error(response.message)
        }
    }



    fun getLangauesList() : Status<ArrayList<Language>> {
        val request = Request.Builder().url(urlBuilder(Constants.LANGUAGES)).build()
        val response = okHttpClient.newCall(request).execute()
        return if (response.isSuccessful) {
            val jsonParser = gson.fromJson(response.body?.string(), LanguagesResponse::class.java)
            Status.Success(jsonParser)
        } else {
            Status.Error(response.message)
        }
    }

private fun urlBuilder(pathSegment : String): HttpUrl {
    return HttpUrl.Builder()
        .scheme(Constants.SCHEME)
        .host(Constants.BASE_URl)
        .addPathSegment(pathSegment)
        .build()
}
}