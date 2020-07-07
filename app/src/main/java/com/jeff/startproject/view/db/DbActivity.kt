package com.jeff.startproject.view.db

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.jeff.startproject.databinding.ActivityDbBinding
import com.jeff.startproject.model.db.User
import com.view.base.BaseActivity

class DbActivity : BaseActivity<ActivityDbBinding>() {

    private val viewModel: DbViewModel by viewModels()

    override fun getViewBinding(): ActivityDbBinding {
        return ActivityDbBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.editLayoutErrorMessage.observe(this, Observer {
            binding.layoutQuery.error = it
        })

        binding.btnInsert.setOnClickListener {
            mutableListOf<User>().also {
                it.add(User(userId = "001", userName = "Jeff", age = 35))
                it.add(User(userId = "002", userName = "Android", age = 10))
                it.add(User(userId = "003", userName = "iOS", age = 10))
            }.also {
                viewModel.insertUsers(it)
            }
        }

        binding.btnQueryAll.setOnClickListener {
            when (METHOD) {
                1 -> viewModel.queryUsers()
                2 -> viewModel.queryUsers2()
            }
        }

        binding.btnQueryBy.setOnClickListener {
            binding.editQuery.text.toString().also {
                viewModel.queryUserByName(it)
            }
        }

        binding.btnQueryLike.setOnClickListener {
            binding.editQuery.text.toString().also {
                viewModel.queryUserLikeName(it)
            }
        }
    }
}
