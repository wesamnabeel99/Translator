package com.example.translator.data.response

import com.google.gson.annotations.SerializedName

data class TranslatedText(
     @SerializedName("translatedText") val translatedText:String
)
