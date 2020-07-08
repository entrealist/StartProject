package com.jeff.startproject.view.file

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jeff.startproject.R
import com.jeff.startproject.enums.ModelResult
import com.jeff.startproject.utils.SingleEvent
import com.view.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.inject
import java.io.File

class FileContentViewModel : BaseViewModel() {
    private val appContext: Context by inject()

    private val _status = MutableLiveData<SingleEvent<ModelResult<String>>>()
    val status: LiveData<SingleEvent<ModelResult<String>>> = _status

    fun readFile(path: String, isInit: Boolean = false) {
        viewModelScope.launch {
            flow {
                if (isInit) {
                    delay(1000L)
                }

                val file = File(path)
                when (file.exists()) {
                    false -> ModelResult.failure(RuntimeException(appContext.getString(R.string.message_file_not_found)))
                    true -> {
                        val lines = file.readLines()
                        val sb = StringBuilder()
                        for (line in lines) {
                            sb.appendln(line)
                        }
                        ModelResult.success(sb.toString())
                    }
                }.also { result ->
                    emit(result)
                }
            }
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    emit(ModelResult.failure(e))
                }
                .onStart {
                    updateProcessing(true)
                    emit(ModelResult.loading())
                }
                .onCompletion {
                    updateProcessing(false)
                    emit(ModelResult.loaded())
                }
                .collect {
                    _status.value = SingleEvent(it)
                }
        }
    }
}