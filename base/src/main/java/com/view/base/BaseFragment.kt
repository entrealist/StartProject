package com.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.*

abstract class BaseFragment<out B : ViewBinding, out VM : BaseViewModel> : Fragment() {

    private var mViewBinding: B? = null
    val binding: B get() = mViewBinding!!

    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): B

    abstract fun fetchViewModel(): VM?

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getViewBinding(inflater, container).run {
            mViewBinding = this
            root
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mViewBinding = null
    }

    open fun navigateTo(item: NavigateItem) {
        lifecycleScope.launch {
            navigationTaskJoinOrRun {
                findNavController().also { navController ->
                    when (item) {
                        NavigateItem.Up -> navController.navigateUp() //.popBackStack()
                        is NavigateItem.PopBackStack -> navController.popBackStack(item.fragmentId, item.inclusive)
                        is NavigateItem.Destination -> {
                            if (item.bundle == null) {
                                navController.navigate(item.action)
                            } else {
                                navController.navigate(item.action, item.bundle)
                            }
                        }
                    }
                }
                delay(1500L)
            }
        }
    }

    private var navigationTask: Deferred<Any>? = null

    private suspend fun navigationTaskJoinOrRun(block: suspend () -> Any): Any {
        // 如果當前有正在執行的 activeTask ，直接返回
        navigationTask?.let {
            return it.await()
        }

        // 否則建立一個新的 async
        return coroutineScope {
            val newTask = async {
                block()
            }

            newTask.invokeOnCompletion {
                navigationTask = null
            }

            navigationTask = newTask
            newTask.await()
        }
    }

}