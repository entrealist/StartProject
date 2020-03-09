package com.jeff.startproject.view.runtimeexec

import android.os.Bundle
import com.jeff.startproject.databinding.ActivityRuntimeExecBinding
import com.jeff.startproject.view.base.BaseActivity
import com.log.JFLog
import java.io.*

class RuntimeExecActivity : BaseActivity<ActivityRuntimeExecBinding>() {
    override fun getViewBinding(): ActivityRuntimeExecBinding {
        return ActivityRuntimeExecBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // listProcesses(true)

        isProcessRunning("com.eg.android.AlipayGphone").let {
            JFLog.d("Alipay: $it")
        }

        JFLog.d("MyPid: ${android.os.Process.myPid()}")

        Thread(Runnable {
            Thread.sleep(1000)
            killProcess("com.jeff.runtimeexec")
            // android.os.Process.killProcess(android.os.Process.myPid())
        }).start()
    }

    @Deprecated("Not working")
    private fun killProcess(name: String) {
        val pid = Runtime.getRuntime().exec("su").let {
            val opt = DataOutputStream(it.outputStream)
            opt.writeBytes("ps -A | grep '$name'\n")
            opt.writeBytes("exit\n")
            opt.flush()

            val reader = InputStreamReader(it.inputStream)
            val br = BufferedReader(reader)
            val regex = "\\s+".toRegex()

            var line: String?
            while (br.readLine().also { line = it } != null) {
                if (!line.isNullOrBlank()) {
                    val list = line!!.split(regex)
                    val size = list.size
                    if (size > 1) {
                        if (list[size - 1] == name) {
                            return@let list[1]
                        }
                    }
                }
            }

            ""
        }

        if (pid.isNotBlank()) {
            Runtime.getRuntime().exec("su").let {
                val cmd = "kill $pid"
                JFLog.d(cmd)

                val opt = DataOutputStream(it.outputStream)
                opt.writeBytes(cmd)
                opt.writeBytes("exit\n")
                opt.flush()

                it.waitFor()

                printInputStream(it.inputStream)
                printErrorStream(it.errorStream)
            }
        }
    }

    private fun listProcesses(all: Boolean) {
        if (all) {

            val p = Runtime.getRuntime().exec("su")
            val opt = DataOutputStream(p.outputStream)
            opt.writeBytes("ps -A\n")
            opt.writeBytes("exit\n")
            opt.flush()

            val exitValue = p.waitFor()
            JFLog.d("Exit: $exitValue")


            printErrorStream(p.errorStream)
            printInputStream(p.inputStream)
        } else {
            val p = Runtime.getRuntime().exec("ps -A")
            printErrorStream(p.errorStream)
            printInputStream(p.inputStream)
        }
    }

    private fun printInputStream(inputStream: InputStream) {
        val reader = InputStreamReader(inputStream)
        val br = BufferedReader(reader)
        var line: String? = null

        while (br.readLine().also { line = it } != null) {
            // myPrint(line)
            JFLog.d(line!!)
        }
    }

    fun printErrorStream(inputStream: InputStream) {
        val reader = InputStreamReader(inputStream)
        val br = BufferedReader(reader)
        var line: String? = null

        while (br.readLine().also { line = it } != null) {
            JFLog.e(line)
        }
    }

    private fun myPrint(content: String?) {
        val regex = "\\s+".toRegex()

        if (!content.isNullOrBlank()) {
            content.split(regex).also { list ->

                list.size.let { size ->
                    if (size > 1) {
                        JFLog.d(list[size - 1])
                    }
                }
            }
        }
    }

    // 0: 沒有執行, 1: 執行中, 2: 錯誤
    data class ProcessResult(val resultCode: Int, val message: String)

    private fun isProcessRunning(name: String): ProcessResult {
        val p = Runtime.getRuntime().exec("su")
        val opt = DataOutputStream(p.outputStream)
        opt.writeBytes("ps -A | grep 'com.eg.android.AlipayGphone'\n")
        opt.writeBytes("exit\n")
        opt.flush()

        val exitValue = p.waitFor()

        if (exitValue == 0) {
            return when (isProcessRunning(name, p.inputStream)) {
                true -> ProcessResult(1, "")
                false -> ProcessResult(0, "")
            }
        } else {
            return ProcessResult(2, getErrorMessage(p.errorStream))
        }
    }

    private fun isProcessRunning(name: String, inputStream: InputStream): Boolean {
        val reader = InputStreamReader(inputStream)
        val br = BufferedReader(reader)
        val regex = "\\s+".toRegex()
        var result = false

        var line: String?
        while (br.readLine().also { line = it } != null) {
            if (!line.isNullOrBlank()) {
                val list = line!!.split(regex)
                val size = list.size
                if (size > 1) {
                    if (list[size - 1] == name) {
                        JFLog.w(line!!)
                        result = true
                        break
                    }
                }

                JFLog.d(line!!)
            }
        }

        return result
    }

    private fun getErrorMessage(inputStream: InputStream): String {
        val sb = StringWriter()
        val reader = InputStreamReader(inputStream)
        val br = BufferedReader(reader)
        var line: String? = null

        while (br.readLine().also { line = it } != null) {
            sb.appendln(line)
        }

        return sb.toString()
    }
}