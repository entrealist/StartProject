package com.jeff.startproject.view.file

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jeff.startproject.MyApplication
import com.jeff.startproject.R
import com.jeff.startproject.view.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.io.File

class FileContentViewModel : BaseViewModel() {
    private val appContext = MyApplication.applicationContext()

    private val mContent = MutableLiveData<String>()
    val content: LiveData<String> = mContent

    fun readFile(path: String) {
        viewModelScope.launch {
            flow {
                val file = File(path)
                when (file.exists()) {
                    false -> appContext.getString(R.string.message_file_not_found)
                    true -> {
                        val lines = file.readLines()
                        val sb = StringBuilder()
                        for (line in lines) {
                            sb.appendln(line)
                        }
                        sb.toString()
                    }
                }.also { result ->
                    emit(result)
                }
            }
                .flowOn(Dispatchers.IO)
                .collect {
                    mContent.value = it
                }
        }
    }
}