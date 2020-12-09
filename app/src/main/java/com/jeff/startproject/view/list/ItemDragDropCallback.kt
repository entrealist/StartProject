package com.jeff.startproject.view.list

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow

class ItemDragDropCallback : ItemTouchHelper.Callback() {

    private val onRowMoved = BroadcastChannel<Pair<Int, Int>>(Channel.BUFFERED)
    fun onRowMoved() = onRowMoved.asFlow()

    private val onRowSelected = BroadcastChannel<RecyclerView.ViewHolder>(Channel.BUFFERED)
    fun onRowSelected() = onRowSelected.asFlow()

    private val onRowClear = BroadcastChannel<RecyclerView.ViewHolder>(Channel.BUFFERED)
    fun onRowClear() = onRowClear.asFlow()

    override fun isLongPressDragEnabled(): Boolean {
        return false
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        onRowMoved.offer(Pair(viewHolder.absoluteAdapterPosition, target.absoluteAdapterPosition))
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        viewHolder?.also {
            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                onRowSelected.offer(it)
            }
        }

        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)

        onRowClear.offer(viewHolder)
    }
}