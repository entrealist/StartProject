package com.jeff.startproject.view.file

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jeff.startproject.MyApplication
import com.jeff.startproject.R
import com.jeff.startproject.enums.ModelResult
import com.view.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File

class FileContentViewModel : BaseViewModel() {
    private val appContext = MyApplication.applicationContext()

    private val _status = MutableLiveData<ModelResult<String>>()
    val result: LiveData<ModelResult<String>> = _status

    fun readFile(path: String, isInit: Boolean = false) {
        viewModelScope.launch {
            flow {
                if (isInit) {
                    delay(1000L)
                }

                val file = File(path)
                when (file.exists()) {
                    false -> ModelResult.Failure(appContext.getString(R.string.message_file_not_found))
                    true -> {
                        val lines = file.readLines()
                        val sb = StringBuilder()
                        for (line in lines) {
                            sb.appendln(line)
                        }
                        ModelResult.Success(sb.toString())
                    }
                }.also { result ->
                    emit(result)
                }
            }
                .flowOn(Dispatchers.IO)
                .onStart {
                    updateProcessing(true)
                    _status.value = ModelResult.Progressing
                }
                .onCompletion {
                    updateProcessing(false)
                }
                .collect {
                    _status.value = it
                }
        }
    }
}