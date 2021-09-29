package com.example.translator.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.translator.data.Status
import com.example.translator.data.repository.TranslationRepository
import com.example.translator.data.response.TranslatedText
import com.example.translator.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getTranslation()
    }

    fun getTranslation() {
        lifecycleScope.launch {
            TranslationRepository.getTranslation("hello").onCompletion {
                Log.i("HELLO", "Results Are Available")
            }.catch {
                Log.i("HELLO", it.message.toString())
            }.collect { getResult(it) }
        }
    }


    private fun getResult(response: Status<TranslatedText>) {
        when (response) {
            is Status.Error -> Log.i("HELLO", "error ${response.message} , ${response}")

            is Status.Loading -> Log.i("HELLO", "loading")

            is Status.Success -> Log.i("HELLO", "result is ${response.data.translatedText}")
        }
    }
}


