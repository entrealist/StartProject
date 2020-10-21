package com.jeff.startproject

import android.app.Application
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import android.util.DisplayMetrics
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.jeff.startproject.di.apiModule
import com.jeff.startproject.di.appModule
import com.jeff.startproject.di.dbModule
import com.jeff.startproject.view.blur.BlurActivity
import com.log.JFLog
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application(), LifecycleObserver {

    companion object {
        lateinit var self: Application
        fun applicationContext(): Application {
            return self
        }

        fun getStatusBarHeight(): Int =
            applicationContext().resources.getIdentifier("status_bar_height", "dimen", "android")
                .takeIf { it > 0 }?.let { applicationContext().resources.getDimensionPixelSize(it) } ?: 0

        private val display: DisplayMetrics
            get() = self.resources.displayMetrics

        fun getScreenWidthPx() = display.widthPixels
        fun getScreenHeightPx() = display.heightPixels
    }

    init {
        self = this
    }

    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(listOf(appModule, dbModule, apiModule))
        }

        addDynamicShortcut(this, "com.jeff.blur")
    }

    private fun addDynamicShortcut(application: Application, shortcutId: String) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
            return
        }

        val shortcutManager = application.getSystemService(ShortcutManager::class.java)

        if (shortcutManager.dynamicShortcuts.count() + shortcutManager.manifestShortcuts.count() > shortcutManager.maxShortcutCountPerActivity) {
            return
        }

        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        mainIntent.setPackage(application.packageName)
        val activities = application.packageManager.queryIntentActivities(mainIntent, 0)
        if (activities.isEmpty()) {
            return
        }

        val intent = Intent(this, BlurActivity::class.java)
        intent.action = Intent.ACTION_VIEW

        val shortcut = ShortcutInfo.Builder(application, shortcutId)
            .setShortLabel("Blur!")
            .setLongLabel("BlurActivity")
            .setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
            .setIntent(intent)
            .build()

        //shortcutManager.dynamicShortcuts = listOf(shortcut)
        shortcutManager.addDynamicShortcuts(listOf(shortcut))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreateApplication() {
        JFLog.d("onCreateApplication: ")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStartApplication() {
        JFLog.d("onStartApplication: ")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResumeApplication() {
        JFLog.d("onResumeApplication: ")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPauseApplication() {
        JFLog.d("onPauseApplication: ")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStopApplication() {
        JFLog.d("onStopApplication: ")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyApplication() {
        JFLog.d("onDestroyApplication: ")
    }
}