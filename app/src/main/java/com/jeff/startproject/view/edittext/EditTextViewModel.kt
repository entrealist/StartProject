package com.jeff.startproject.view.edittext

import com.utils.lifecycle.PluginEditTextLiveData
import com.utils.lifecycle.PluginEditTextMutableLiveData
import com.view.base.BaseViewModel

class EditTextViewModel : BaseViewModel() {
    private val _pluginEditTextMutableLiveData = PluginEditTextMutableLiveData().also {
        it.setTextListener(CurrencyEditLiveDataListener())
    }

    val pluginEditTextLiveData: PluginEditTextLiveData = _pluginEditTextMutableLiveData
}