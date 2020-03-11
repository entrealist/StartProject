package com.jeff.startproject.view.file

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.jeff.startproject.databinding.ActivityFileBinding
import com.jeff.startproject.view.base.BaseActivity

class FileActivity : BaseActivity<ActivityFileBinding>() {

    override fun getViewBinding(): ActivityFileBinding {
        return ActivityFileBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val needPermissions = mutableListOf<String>()

        for (permission in permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, permission)) {
                needPermissions.add(permission)
            }
        }

        if (needPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, needPermissions.toTypedArray(), 100)
        }
    }
}