package com.example.t54zhao.hiittracker.edit

import android.support.v7.widget.RecyclerView

interface OnStartDragListener {
    /**
     * Called when view is requesting a drag event
     *
     * @param viewHolder The view requesting the drag
     */
     fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
}