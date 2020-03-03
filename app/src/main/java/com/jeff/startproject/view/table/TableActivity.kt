package com.jeff.startproject.view.table

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jeff.startproject.R
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

        window.statusBarColor = Color.TRANSPARENT

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_share -> {
                    Toast.makeText(this, R.string.text_table_recycler_view, Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

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
