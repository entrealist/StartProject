package com.jeff.startproject.view.table

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jeff.startproject.R
import com.jeff.startproject.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_table.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TableActivity : BaseActivity() {

    private val viewModel by viewModel<TableViewModel>()

    override fun getLayoutId(): Int {
        return R.layout.activity_table
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userInfoListAdapter = UserInfoListAdapter()

        recycler_view.let {
            it.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            it.adapter = userInfoListAdapter
        }

        viewModel.processing.observe(this, Observer {
            if (it) {
                progressHUD.show()
            } else {
                progressHUD.dismiss()
            }
        })

        viewModel.userListData.observe(this, Observer {
            userInfoListAdapter.submitList(it)
        })

        viewModel.getUsers()
    }
}
