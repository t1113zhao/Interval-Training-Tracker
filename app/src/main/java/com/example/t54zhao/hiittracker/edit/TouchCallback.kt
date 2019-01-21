package com.example.t54zhao.hiittracker.edit

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

class TouchCallback(val mAdapter: EditRecyclerViewAdapter): ItemTouchHelper.Callback( ) {

    override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
        var dragFlag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        var swipeFlag = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(dragFlag,swipeFlag)
    }

    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
        mAdapter.onItemMove(viewHolder?.adapterPosition!!, target?.adapterPosition!!)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
        mAdapter.onItemSwipe(viewHolder?.adapterPosition!!)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }
}