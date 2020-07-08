package com.jeff.startproject.view.sample

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.jeff.startproject.databinding.ActivitySampleBinding
import com.jeff.startproject.enums.ModelResult
import com.log.JFLog
import com.view.base.BaseActivity

class SampleActivity : BaseActivity<ActivitySampleBinding>() {

    private val viewModel: SampleViewModel by viewModels()

    override fun getViewBinding(): ActivitySampleBinding {
        return ActivitySampleBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.recordLog.observe(this, Observer { message ->
            binding.textLog.text = message
        })

        ModelResult.Success("Happy").also {
            handleStringResult(it)
            transformResult(it)
        }

        ModelResult.Success(999).also {
            handleIntResult(it)
            transformResult(it)
        }

        ModelResult.Failure(Exception("Help!")).also {
            handleStringResult(it)
            handleIntResult(it)
            transformResult(it)
        }

        ModelResult.Failure(RuntimeException("Crash!")).also {
            handleStringResult(it)
            handleIntResult(it)
            transformResult(it)
        }

        ModelResult.Loading.also {
            handleStringResult(it)
            handleIntResult(it)
            transformResult(it)
        }
    }

    private fun handleStringResult(modelResult: ModelResult<String>) {
        when (modelResult) {
            is ModelResult.Success -> {
                JFLog.d("Data: ${modelResult.data}").also {
                    viewModel.appendMessage(it)
                }
            }
            is ModelResult.Failure -> {
                JFLog.e("Failure: ${modelResult.throwable.message}").also {
                    viewModel.appendMessage(it)
                }
            }
            ModelResult.Loading -> {
                JFLog.d("Loading").also {
                    viewModel.appendMessage(it)
                }
            }
        }
    }

    private fun handleIntResult(modelResult: ModelResult<Int>) {
        when (modelResult) {
            is ModelResult.Success -> {
                JFLog.d("Data: ${modelResult.data}").also {
                    viewModel.appendMessage(it)
                }
            }
            is ModelResult.Failure -> {
                JFLog.e("Failure: ${modelResult.throwable.message}").also {
                    viewModel.appendMessage(it)
                }
            }
            ModelResult.Loading -> {
                JFLog.d("Loading").also {
                    viewModel.appendMessage(it)
                }
            }
        }
    }

    private fun transformResult(modelResult: ModelResult<Any>) {
        modelResult.transform.also {
            JFLog.d(it).also {
                viewModel.appendMessage(it)
            }
        }
    }

    private val <T> T.everything: T
        get() = this

    private val <T> T.transform: String
        get() = when (this) {
            is ModelResult.Success<*> -> "Success"
            ModelResult.SuccessNoContent -> "SuccessNoContent"
            is ModelResult.Failure -> "Failure"
            ModelResult.Loading -> "Loading"
            ModelResult.Loaded -> "Loaded"
            else -> "?"
        }
}