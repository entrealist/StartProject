package com.jeff.startproject.view.adbcmd

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.jeff.startproject.databinding.ActivityAdbCmdBinding
import com.log.JFLog
import com.view.base.BaseActivity

class AdbCmdActivity : BaseActivity<ActivityAdbCmdBinding>() {
    override fun getViewBinding(): ActivityAdbCmdBinding {
        return ActivityAdbCmdBinding.inflate(layoutInflater)
    }

    private val TERMUX_SERVICE = "com.termux.app.TermuxService"
    private val ACTION_EXECUTE = "com.termux.service_execute"
    private val EXTRA_EXECUTE_IN_BACKGROUND = "com.termux.execute.background"
    private val EXTRA_ARGUMENTS = "com.termux.execute.arguments"
    private val BOOT_SCRIPT_PATH = "/data/data/com.termux/files/home/boot/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        JFLog.d(filesDir.path)

        if (true) {
            // TODO: Work
            val script =
                Uri.Builder().scheme("com.termux.file").path(BOOT_SCRIPT_PATH + "test.jf").build()

            JFLog.d("Script: $script")

            val intent = Intent(ACTION_EXECUTE, script)

            intent.setClassName("com.termux", TERMUX_SERVICE)
            intent.putExtra(EXTRA_EXECUTE_IN_BACKGROUND, true)
            startService(intent)
        } else {
            val script =
                Uri.Builder().scheme("com.termux.file").path(BOOT_SCRIPT_PATH + "sshd.jf").build()

            JFLog.d("Script: $script")

            val intent = Intent(ACTION_EXECUTE, script)

            intent.setClassName("com.termux", TERMUX_SERVICE)
            intent.putExtra(EXTRA_EXECUTE_IN_BACKGROUND, true)
            intent.putExtra(EXTRA_ARGUMENTS, arrayOf("1", "2", "3cd b"))
            startService(intent)
        }

        val tag = getSystemProperty("debug.jeff", "No")
        JFLog.d("debug.jeff : $tag")
    }

    // FIXME: Not working
    private fun test() {
        // val command = "am startservice -n com.termux/.app.TermuxService  -a com.termux.service_execute  -d /data/data/com.termux/files/home/.termux/boot/services --ez com.termux.execute.background true -e com.termux.execute.arguments"
        val command = "am start -a android.intent.action.VIEW -d http://www.baidu.com"

        // 方法1:
        // Runtime.getRuntime().exec(command)

        // 方法2:
        val pb = ProcessBuilder(command)
        pb.start()
    }

    private fun getSystemProperty(key: String, defaultValue: String): String {
        var value = defaultValue

        try {
            value = Class.forName("android.os.SystemProperties").getMethod(
                "get", String::class.java
            ).invoke(null, key) as String
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return value
    }
}