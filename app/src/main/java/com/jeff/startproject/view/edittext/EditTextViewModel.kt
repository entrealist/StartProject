package com.jeff.startproject.view.edittext

import com.jeff.startproject.utils.livedata.PluginEditTextLiveData
import com.jeff.startproject.utils.livedata.PluginEditTextMutableLiveData
import com.jeff.startproject.view.base.BaseViewModel

class EditTextViewModel : BaseViewModel() {
    private val mPluginEditTextMutableLiveData = PluginEditTextMutableLiveData().also {
        it.setTextListener(CurrencyEditLiveDataListener())
    }

    val pluginEditTextLiveData: PluginEditTextLiveData = mPluginEditTextMutableLiveData
}