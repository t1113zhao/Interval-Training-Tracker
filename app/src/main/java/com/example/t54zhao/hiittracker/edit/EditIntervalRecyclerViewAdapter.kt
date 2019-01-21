package com.example.t54zhao.hiittracker.edit

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.t54zhao.hiittracker.models.Interval

class EditIntervalRecyclerViewAdapter (val interval:Interval, val context: EditWorkoutActivity, val onStartDragListener: OnStartDragListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() , EditItemTouchAdapter{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSwipe(itemPosition: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}