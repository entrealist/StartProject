package com.jeff.startproject.view.eventbus

import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import org.greenrobot.eventbus.EventBus
import java.lang.ref.WeakReference

fun ComponentActivity.eventBusRegisterAuto() {
    val weakRef = WeakReference(this)

    lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onStart(owner: LifecycleOwner) {
            weakRef.get()?.also {
                EventBus.getDefault().register(it)
            }
        }

        override fun onStop(owner: LifecycleOwner) {
            weakRef.get()?.also {
                EventBus.getDefault().unregister(it)
            }
        }
    })
}