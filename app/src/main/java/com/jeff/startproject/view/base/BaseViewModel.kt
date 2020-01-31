package com.jeff.startproject.view.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import org.koin.core.KoinComponent

abstract class BaseViewModel : ViewModel(), KoinComponent {
    protected val mProcessing = MutableLiveData<Boolean>()
    val processing: LiveData<Boolean> = mProcessing

    private var activeTask: Deferred<Any>? = null

    protected suspend fun cancelPreviousThenRun(block: suspend () -> Any): Any {
        // 如果當前有正在執行的 cachedTask，可以直接取消並改成執行最新的請求
        activeTask?.cancelAndJoin()

        return coroutineScope {
            // 建立一個 async 並且 suspend
            val newTask = async {
                block()
            }

            // newTask 執行完畢時清除舊的 activeTask 任務
            newTask.invokeOnCompletion {
                activeTask = null
            }

            // newTask 完成後交給 activeTask
            activeTask = newTask
            // newTask 恢復狀態並開始執行
            newTask.await()
        }
    }

    protected suspend fun joinPreviousOrRun(block: suspend () -> Any): Any {
        // 如果當前有正在執行的 activeTask ，直接返回
        activeTask?.let {
            return it.await()
        }

        // 否則建立一個新的 async
        return coroutineScope {
            val newTask = async {
                block()
            }

            newTask.invokeOnCompletion {
                activeTask = null
            }

            activeTask = newTask
            newTask.await()
        }
    }

    protected fun cancelActiveTask() {
        activeTask?.cancel("Cancel active task", RuntimeException("Cancel active task"))
    }
}