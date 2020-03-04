package com.jeff.startproject.view.flowcontrol

import androidx.lifecycle.viewModelScope
import com.jeff.startproject.view.base.LogcatViewModel
import com.log.JFLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class FlowControlViewModel : LogcatViewModel() {

    private fun fetchSamples() = flow {
        for (i in 0..99) {
            emit("Step $i")
            delay(1000L)

            if (i == 30) {
                throw RuntimeException("Stop")
            }
        }

        emit("Finish")
    }

    fun tryCancelPreviousThenRun() {
        viewModelScope.launch {
            cancelPreviousThenRun {
                fetchSamples()
                    .flowOn(Dispatchers.IO)
                    .catch { e ->
                        JFLog.e(e).also {
                            appendMessage(it)
                        }
                    }
                    .collect {
                        JFLog.d(it).also {
                            appendMessage(it)
                        }
                    }
            }
        }
    }

    fun tryJoinPreviousOrRun() {
        viewModelScope.launch {
            joinPreviousOrRun {
                fetchSamples()
                    .flowOn(Dispatchers.IO)
                    .catch { e ->
                        JFLog.e(e).also {
                            appendMessage(it)
                        }
                    }
                    .collect {
                        JFLog.d(it).also {
                            appendMessage(it)
                        }
                    }
            }
        }
    }

    fun cancelTask() {
        cancelActiveTask()
    }
}