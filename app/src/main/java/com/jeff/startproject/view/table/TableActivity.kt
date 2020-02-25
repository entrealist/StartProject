package com.jeff.startproject.view.table

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jeff.startproject.databinding.ActivityTableBinding
import com.jeff.startproject.view.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class TableActivity : BaseActivity<ActivityTableBinding>() {

    private val viewModel by viewModel<TableViewModel>()

    override fun getViewBinding(): ActivityTableBinding {
        return ActivityTableBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userInfoListAdapter = UserInfoListAdapter()

        binding.recyclerView.let {
            it.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            it.adapter = userInfoListAdapter
        }

        binding.layoutRefresh.setOnRefreshListener {
            viewModel.getUsers()
        }

        viewModel.processing.observe(this, Observer {
            binding.layoutRefresh.isRefreshing = it
        })

        viewModel.userListData.observe(this, Observer {
            userInfoListAdapter.submitList(it)
        })

        viewModel.getUsers()
    }
}
