package com.example.translationtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.translationtask.data.Languages
import com.example.translationtask.data.Translate
import com.example.translationtask.databinding.ActivityMainBinding
import com.example.translationtask.repository.TranslateRepository
import com.example.translationtask.ui.Status
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.inputWord.doOnTextChanged { text, start, before, count -> 
            makeRequest(binding.inputWord.text.toString(),"en","ar")
        }
        makeRequestLanguage()
    }

    private fun makeRequest(q: String, source: String, target: String) {
     lifecycleScope.async{
         TranslateRepository.getInfoTrans(q, source, target).collect(::getResultTrans)
     }
    }

    fun getResultTrans(response: Status<Translate>) {
        return when (response) {
            is Status.Error -> {
                Toast.makeText(
                    this@MainActivity,
                    "error can't access sorry ",
                    Toast.LENGTH_SHORT
                ).show()
            }
            is Status.Loading -> {
                Toast.makeText(
                    this@MainActivity,
                    "Loading access  ",
                    Toast.LENGTH_SHORT
                ).show()
            }
            is Status.Success -> {
                Toast.makeText(
                    this@MainActivity,
                    "error can't access sorry ",
                    Toast.LENGTH_SHORT
                ).show()
                bindData(response.data)
            }
        }
    }

    private fun makeRequestLanguage() {
        lifecycleScope.async{
            TranslateRepository.getInfoLanguage().collect(::getResultLanguage)
        }
    }

    fun getResultLanguage(response: Status<Languages>) {
        return when (response) {
            is Status.Error -> {
                Toast.makeText(
                    this@MainActivity,
                    "error can't access sorry ",
                    Toast.LENGTH_SHORT
                ).show()
            }
            is Status.Loading -> {
                Toast.makeText(
                    this@MainActivity,
                    "Loading access  ",
                    Toast.LENGTH_SHORT
                ).show()
            }
            is Status.Success -> {
                Toast.makeText(
                    this@MainActivity,
                    "error can't access sorry ",
                    Toast.LENGTH_SHORT
                ).show()
                setSpinnerLang(response.data)
            }
        }
    }


    private fun bindData(data: Translate) {
    binding.dataText.text = data.translatedText
    }

    private fun setSpinnerLang(data: Languages){
     Log.i("RESUIT",data.toString())
    }


}