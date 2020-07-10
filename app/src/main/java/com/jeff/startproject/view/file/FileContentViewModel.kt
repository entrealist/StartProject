package com.jeff.startproject.view.file

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jeff.startproject.R
import com.jeff.startproject.enums.ModelResult
import com.utils.lifecycle.SingleEvent
import com.view.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.File

class FileContentViewModel : BaseViewModel(), KoinComponent {
    private val context: Context by inject()

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
                    false -> {
                        val message = context.getString(R.string.message_file_not_found)
                        throw RuntimeException(message)
                    }
                    true -> {
                        val lines = file.readLines()
                        val sb = StringBuilder()
                        for (line in lines) {
                            sb.appendln(line)
                        }
                        emit(ModelResult.success(sb.toString()))
                    }
                }
            }
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    emit(ModelResult.failure(e))
                }
                .onStart {
                    emit(ModelResult.loading())
                }
                .onCompletion {
                    emit(ModelResult.loaded())
                }
                .collect {
                    _status.value = SingleEvent(it)
                }
        }
    }
}