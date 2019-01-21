package com.example.t54zhao.hiittracker.edit

interface EditItemTouchAdapter {
    fun onItemMove(fromPosition:Int, toPosition:Int)

    fun onItemSwipe(itemPosition:Int)
}