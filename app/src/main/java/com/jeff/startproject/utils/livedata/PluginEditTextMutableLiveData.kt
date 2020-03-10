package com.jeff.startproject.utils.livedata

class PluginEditTextMutableLiveData(value: String? = null) : PluginEditTextLiveData(value) {

    public override fun setValue(value: String?) {
        super.setValue(value)
    }

    public override fun postValue(value: String?) {
        super.postValue(value)
    }
}