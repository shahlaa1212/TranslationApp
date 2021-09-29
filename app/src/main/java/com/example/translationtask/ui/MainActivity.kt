package com.example.translationtask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.translationtask.data.Languages
import com.example.translationtask.data.Translate
import com.example.translationtask.databinding.ActivityMainBinding
import com.example.translationtask.repository.TranslateRepository
import com.example.translationtask.util.Status
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import android.view.View
import android.widget.*
import com.example.translationtask.R
import com.example.translationtask.network.Client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.inputWord.doOnTextChanged { text, start, before, count -> 
            makeRequest(text.toString(),TranslateRepository.sourceLanguage,TranslateRepository.TargetLanguage)
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

    //private fun makeRequestLanguage() {
//        lifecycleScope.async{
//            TranslateRepository.getInfoLanguage().collect(::getResultLanguage)
//        }
    //}

    fun makeRequestLanguage(){
        val flow = flow<Status<List<Languages>>>{
            val result = Client.initRequestLanguages()
            emit(result)
        }.flowOn(Dispatchers.Default)
        lifecycleScope.launch{
            flow.collect{
                when (it){
                    is Status.Error ->Log.i("RESUIT","fail")
                    Status.Loading -> Log.i("RESUIT","loading")
                    is Status.Success -> setSpinnerLang()
                        //Log.i("RESUIT","success")

                }
            }
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
               //setSpinnerLang(response.data)
            }
        }
    }


    private fun bindData(data: Translate) {
    binding.dataText.text = data.translatedText
    }

    private fun setSpinnerLang(){
        val langugeItem = TranslateRepository.languageList.map { it.name }
        //Log.i("RESUIT",data.toString())
        val madapter = ArrayAdapter(this, R.layout.spinner_item, langugeItem)
        binding.spinner.apply {
            adapter = madapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    TranslateRepository.sourceLanguage = TranslateRepository.languageList[p2].code.toString()
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
        }
        binding.spinner2.apply {
            adapter = madapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    TranslateRepository.TargetLanguage = TranslateRepository.languageList[p2].code.toString()
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
        }

    }


}