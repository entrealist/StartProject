package com.jeff.startproject.view.db

import android.os.Bundle
import com.jeff.startproject.R
import com.jeff.startproject.model.db.User
import com.jeff.startproject.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_db.*
import org.koin.androidx.viewmodel.ext.android.viewModel

const val METHOD = 2

class DbActivity : BaseActivity() {

    private val viewModel by viewModel<DbViewModel>()

    override fun getLayoutId(): Int {
        return R.layout.activity_db
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn_insert.setOnClickListener {
            mutableListOf<User>().also {
                it.add(User(userId = "001", userName = "Jeff", age = 35))
                it.add(User(userId = "002", userName = "Android", age = 10))
                it.add(User(userId = "003", userName = "iOS", age = 10))
            }.also {
                viewModel.insertUsers(it)
            }
        }

        btn_query_all.setOnClickListener {
            when (METHOD) {
                1 -> viewModel.queryUsers()
                2 -> viewModel.queryUsers2()
            }
        }

        btn_query_by.setOnClickListener {
            edit_query.text.toString().also {
                if (it.isNotBlank()) {
                    when (METHOD) {
                        1 -> viewModel.queryUserByName(it)
                        2 -> viewModel.queryUserByName2(it)
                    }
                }
            }
        }

        btn_query_like.setOnClickListener {
            edit_query.text.toString().also {
                if (it.isNotBlank()) {
                    when (METHOD) {
                        1 -> viewModel.queryUserLikeName(it)
                        2 -> viewModel.queryUserLikeName2(it)
                    }
                }
            }
        }
    }
}
