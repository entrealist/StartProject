package com.jeff.startproject.utils

class EditTextMutableLiveData(value: String? = null) : EditTextLiveData(value) {

    public override fun setValue(value: String?) {
        super.setValue(value)
    }

    public override fun postValue(value: String?) {
        super.postValue(value)
    }
}