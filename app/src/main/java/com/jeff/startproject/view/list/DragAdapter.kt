package com.jeff.startproject.view.list

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.jeff.startproject.R
import com.jeff.startproject.databinding.ItemViewDragBinding
import java.util.*

class DragAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: MutableList<String>? = null
    fun setList(list: MutableList<String>?) {
        this.list = list
        notifyDataSetChanged()
    }

    private var itemTouchHelper: ItemTouchHelper? = null
    fun setItemTouchHelper(helper: ItemTouchHelper) {
        this.itemTouchHelper = helper
    }

    fun swapItem(current: Int, target: Int) {
        list?.also {
            Collections.swap(it, current, target)
        }

        notifyItemMoved(current, target)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = inflateWithLayout(parent, R.layout.item_view_drag)
        return ViewHolder(view)
    }

    private fun inflateWithLayout(viewGroup: ViewGroup, @LayoutRes layoutRes: Int): View {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return layoutInflater.inflate(layoutRes, viewGroup, false)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        list?.get(position)?.also {
            (holder as? ViewHolder)?.bind(it)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemViewDragBinding.bind(view)
        fun bind(string: String) {
            binding.tvCenter.text = string
        }

        init {
            binding.tvCenter.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    itemTouchHelper?.startDrag(this)
                }

                false
            }
        }
    }
}