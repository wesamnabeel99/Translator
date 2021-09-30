package com.example.translator.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.translator.data.Status
import com.example.translator.data.repository.TranslationRepository
import com.example.translator.data.response.Language
import com.example.translator.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    val listOfLanguages = mutableListOf<Language>()
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getTranslation()
        getLanguage()
    }

    fun getLanguage() {
        lifecycleScope.launch {
            TranslationRepository.getLanguages().onCompletion{
                Log.i("HELLO", "list is $listOfLanguages")
            }.catch {
                Log.i("HELLO", it.message.toString())
            }.collect {
                when (it) {
                    is Status.Error -> Log.i("HELLO", "error ${it.message} , ${it}")

                    is Status.Loading -> Log.i("HELLO", "loading")

                    is Status.Success -> it.data.forEach {
                        listOfLanguages.add(Language(it.code,it.name))
                    }
                }
            }
        }
    }
    fun getTranslation() {
        lifecycleScope.launch {
            TranslationRepository.getTranslation("hello").onCompletion {
                Log.i("HELLO", "Results Are Available")
            }.catch {
                Log.i("HELLO", it.message.toString())
            }.collect {
                when (it) {
                is Status.Error -> Log.i("HELLO", "error ${it.message} , ${it}")

                is Status.Loading -> Log.i("HELLO", "loading")

                is Status.Success -> Log.i("HELLO", "result is ${it.data.translatedText}")
            }
            }
        }
    }



}


