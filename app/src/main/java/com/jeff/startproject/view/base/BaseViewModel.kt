package com.jeff.startproject.view.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.koin.core.KoinComponent

abstract class BaseViewModel : ViewModel(), KoinComponent {
    protected val _processing = MutableLiveData<Boolean>()
    val processing: LiveData<Boolean> = _processing
}