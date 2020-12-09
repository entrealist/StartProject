package com.jeff.startproject.view.list

import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeff.startproject.databinding.ActivityDragBinding
import com.view.base.BaseActivity
import kotlinx.coroutines.flow.collectLatest

class DragActivity : BaseActivity<ActivityDragBinding>() {
    override fun getViewBinding() = ActivityDragBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val list = mutableListOf<String>()
        repeat(100) {
            list.add("No. $it")
        }

        val adapter = DragAdapter()
        adapter.setList(list)

        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        val itemDragDropCallback = ItemDragDropCallback()
        val touchHelper = ItemTouchHelper(itemDragDropCallback)
        touchHelper.attachToRecyclerView(binding.recyclerView)

        adapter.setItemTouchHelper(touchHelper)

        lifecycleScope.launchWhenResumed {
            itemDragDropCallback.onRowMoved().collectLatest {
                adapter.swapItem(it.first, it.second)
            }
        }

        lifecycleScope.launchWhenResumed {
            itemDragDropCallback.onRowSelected().collectLatest {
                it.itemView.setBackgroundColor(Color.LTGRAY)
            }
        }

        lifecycleScope.launchWhenResumed {
            itemDragDropCallback.onRowClear().collectLatest {
                it.itemView.setBackgroundColor(Color.TRANSPARENT)
            }
        }
    }
}