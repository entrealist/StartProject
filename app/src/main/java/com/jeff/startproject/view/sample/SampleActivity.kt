package com.jeff.startproject.view.sample

import android.os.Bundle
import com.jeff.startproject.databinding.ActivitySampleBinding
import com.jeff.startproject.enums.MyResult
import com.jeff.startproject.view.base.BaseActivity
import com.log.JFLog

class SampleActivity : BaseActivity<ActivitySampleBinding>() {

    override fun getViewBinding(): ActivitySampleBinding {
        return ActivitySampleBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MyResult.Success("Happy").also {
            handleStringResult(it)
            transformResult(it)
        }

        MyResult.Success(999).also {
            handleIntResult(it)
            transformResult(it)
        }

        MyResult.Error.FirstError(Exception("Help!")).also {
            handleStringResult(it)
            handleIntResult(it)
            transformResult(it)
        }

        MyResult.IntProgress.also {
            handleStringResult(it)
            handleIntResult(it)
            transformResult(it)
        }
    }

    private fun handleStringResult(myResult: MyResult<String>) {
        when (myResult) {
            is MyResult.Success -> {
                JFLog.d("Data: ${myResult.data}")
            }
            is MyResult.Error.FirstError -> {
                JFLog.e("First error: ${myResult.exception.message}")
            }
            is MyResult.Error.SecondError -> TODO()
            MyResult.IntProgress -> {
                JFLog.d("IntProgress")
            }
        }
    }

    private fun handleIntResult(myResult: MyResult<Int>) {
        when (myResult) {
            is MyResult.Success -> {
                JFLog.d("Data: ${myResult.data}")
            }
            is MyResult.Error.FirstError -> {
                JFLog.e("First error: ${myResult.exception.message}")
            }
            is MyResult.Error.SecondError -> TODO()
            MyResult.IntProgress -> {
                JFLog.d("IntProgress")
            }
        }
    }

    private fun transformResult(myResult: MyResult<Any>) {
        myResult.transform.also {
            JFLog.d(it)
        }
    }

    private val <T> T.everything: T
        get() = this

    private val <T> T.transform: String
        get() = when (this) {
            is MyResult.Success<Any> -> "Success"
            is MyResult.Error.FirstError -> "FirstError"
            is MyResult.Error.SecondError -> "SecondError"
            MyResult.IntProgress -> "IntProgress"
            else -> "?"
        }
}