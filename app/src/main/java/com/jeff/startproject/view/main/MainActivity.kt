package com.jeff.startproject.view.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.jeff.startproject.MyApplication
import com.jeff.startproject.databinding.ActivityMainBinding
import com.jeff.startproject.view.adbcmd.AdbCmdActivity
import com.jeff.startproject.view.blur.BlurActivity
import com.jeff.startproject.view.chain.ChainActivity
import com.jeff.startproject.view.datastructure.DataStructureActivity
import com.jeff.startproject.view.db.DbActivity
import com.jeff.startproject.view.draw.DrawActivity
import com.jeff.startproject.view.edittext.EditTextActivity
import com.jeff.startproject.view.eventbus.EventBusActivity
import com.jeff.startproject.view.eventbus.LiveEventBusActivity
import com.jeff.startproject.view.file.FileActivity
import com.jeff.startproject.view.floating.OpenFloatingActivity
import com.jeff.startproject.view.flowcontrol.FlowControlActivity
import com.jeff.startproject.view.login.LoginActivity
import com.jeff.startproject.view.navigation.NavigationActivity
import com.jeff.startproject.view.pager.PagerActivity
import com.jeff.startproject.view.preferences.PreferencesActivity
import com.jeff.startproject.view.runtimeexec.RuntimeExecActivity
import com.jeff.startproject.view.sample.SampleActivity
import com.jeff.startproject.view.vector.VectorActivity
import com.jeff.startproject.view.websocket.WebSocketActivity
import com.view.base.BaseActivity

/*
 * https://developer.android.google.cn/kotlin/ktx
 *
 * https://developer.android.google.cn/kotlin/coroutines
 *
 * https://source.android.com/setup/contribute/code-style#follow-field-naming-conventions
 *
 * https://material.io/develop/android/components/material-card-view
 *
 * https://material.io/develop/android/components/material-button
 *
 * View binding:
 * https://developer.android.com/topic/libraries/view-binding
 * https://blog.csdn.net/u010976213/article/details/104501830
 *
 * CollapsingToolbarLayout:
 * https://www.jianshu.com/p/c6a6d08f4a2b
 * https://www.itread01.com/content/1549919007.html
 * app:layout_scrollFlags="scroll|exitUntilCollapsed|enterAlways"
 *
 * LiveData:
 * https://juejin.im/post/5cdff0de5188252f5e019bea
 */

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by viewModels()

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnEventBus.setOnClickListener {
            Intent(this, EventBusActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnLiveEventBus.setOnClickListener {
            Intent(this, LiveEventBusActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnEdit.setOnClickListener {
            Intent(this, EditTextActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnRoom.setOnClickListener {
            Intent(this, DbActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnFlow.setOnClickListener {
            Intent(this, FlowControlActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnVector.setOnClickListener {
            Intent(this, VectorActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnSample.setOnClickListener {
            Intent(this, SampleActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnLogin.setOnClickListener {
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnChain.setOnClickListener {
            Intent(this, ChainActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnWebsocket.setOnClickListener {
            Intent(this, WebSocketActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnNavigation.setOnClickListener {
            Intent(this, NavigationActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnDraw.setOnClickListener {
            Intent(this, DrawActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnAdbCmd.setOnClickListener {
            Intent(this, AdbCmdActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnRuntimeExec.setOnClickListener {
            Intent(this, RuntimeExecActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnFile.setOnClickListener {
            Intent(this, FileActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnPager.setOnClickListener {
            Intent(this, PagerActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnDataStructure.setOnClickListener {
            Intent(this, DataStructureActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnPreference.setOnClickListener {
            Intent(this, PreferencesActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnBlur.setOnClickListener {
            Intent(this, BlurActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnFloating.setOnClickListener {
            Intent(this, OpenFloatingActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnVibrate.setOnClickListener {
            MyApplication.self.startVibrate()
        }
    }
}
