package com.jeff.startproject.view.main

import android.content.Intent
import android.os.Bundle
import com.jeff.startproject.R
import com.jeff.startproject.view.base.BaseActivity
import com.jeff.startproject.view.db.DbActivity
import com.jeff.startproject.view.edittext.EditTextActivity
import com.jeff.startproject.view.eventbus.EventBusActivity
import com.jeff.startproject.view.table.TableActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/*
 * https://developer.android.google.cn/kotlin/ktx
 *
 * https://developer.android.google.cn/kotlin/coroutines
 *
 * https://material.io/develop/android/components/material-card-view
 *
 * https://source.android.com/setup/contribute/code-style#follow-field-naming-conventions
 */

class MainActivity : BaseActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn_event_bus.setOnClickListener {
            Intent(this, EventBusActivity::class.java).also {
                startActivity(it)
            }
        }

        btn_table.setOnClickListener {
            Intent(this, TableActivity::class.java).also {
                startActivity(it)
            }
        }

        btn_edit.setOnClickListener {
            Intent(this, EditTextActivity::class.java).also {
                startActivity(it)
            }
        }

        btn_room.setOnClickListener {
            Intent(this, DbActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}
