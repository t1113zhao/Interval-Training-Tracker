package com.example.t54zhao.hiittracker.edit

import android.app.AlertDialog
import android.content.DialogInterface
import android.support.v4.view.MotionEventCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.t54zhao.hiittracker.R
import com.example.t54zhao.hiittracker.WorkoutViewAdapter
import com.example.t54zhao.hiittracker.models.*
import kotlinx.android.synthetic.main.activity_edit_workout.*
import java.util.*
import kotlin.collections.ArrayList

class EditRecyclerViewAdapter( val workout:Workout, val context: EditWorkoutActivity,val dragListener: OnStartDragListener) : Adapter<RecyclerView.ViewHolder>() , EditItemTouchAdapter{


    private val IS_INTERVAL = 0
    private val IS_PERIOD = 1

    override fun getItemCount(): Int {
        return workout.components.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is EditRowHolder){
            holder.rowHandle.setOnTouchListener(object : View.OnTouchListener{
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN)
                        dragListener.onStartDrag(holder)
                    return false
                }
            })
            holder.rowTypeTV.setOnLongClickListener(object : View.OnLongClickListener{
                override fun onLongClick(v: View?): Boolean {
                    val builder = AlertDialog.Builder(context)

                    val componentList : ArrayList<String> = ArrayList()
                    componentList.addAll(listOf(
                            ComponentType.WARMUP.name,
                            ComponentType.COOLDOWN.name,
                            ComponentType.HIGH.name,
                            ComponentType.LOW.name,
                            ComponentType.INTERVAL.name))
                    val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(context, android.R.layout.simple_list_item_1,componentList)

                    builder.setTitle("Change Workout Component Type")

                    builder.setAdapter(arrayAdapter, object : DialogInterface.OnClickListener{
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            if(workout.components.get(which).componentType != ComponentType.INTERVAL && which != componentList.indexOf(ComponentType.INTERVAL.name)){
                                when(componentList.get(which)){
                                    ComponentType.WARMUP.name -> workout.components.get(position).componentType = ComponentType.WARMUP
                                    ComponentType.COOLDOWN.name -> workout.components.get(position).componentType = ComponentType.COOLDOWN
                                    ComponentType.LOW.name -> workout.components.get(position).componentType = ComponentType.LOW
                                    ComponentType.HIGH.name -> workout.components.get(position).componentType = ComponentType.HIGH
                                }
                            }
                            else if (workout.components.get(which).componentType == ComponentType.INTERVAL && which != componentList.indexOf(ComponentType.INTERVAL.name)){
                                var compType: ComponentType = ComponentType.WARMUP
                                when(componentList.get(which)) {
                                    ComponentType.WARMUP.name -> compType = ComponentType.WARMUP
                                    ComponentType.COOLDOWN.name -> compType = ComponentType.COOLDOWN
                                    ComponentType.LOW.name -> compType = ComponentType.LOW
                                    ComponentType.HIGH.name -> compType = ComponentType.HIGH
                                }

                                workout.removeWorkOutComponents(position)
                                workout.insertWorkoutComponentAt(Period(0,compType),position)
                            }
                            else{
                                workout.removeWorkOutComponents(position)
                                var blankList : ArrayList<Period> = ArrayList()
                                workout.insertWorkoutComponentAt(Interval(blankList,1),position)
                            }
                            notifyDataSetChanged()
                        }
                    })
                    return false
                }
            })
        }
        if(holder is EditPeriodHolder){
            holder.bindData(workout.components.get(position) as Period)
            holder.rowTimeET.addTextChangedListener(object :TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                    var num: Int = Integer.parseInt(holder.rowTimeET.text.toString())
                    workout.components.get(position).setTime(num)
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
        }
        if(holder is EditIntervalHolder){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType == IS_INTERVAL){
            val holder: EditIntervalHolder = EditIntervalHolder(LayoutInflater.from(context).inflate(R.layout.layout_edit_interval_row,parent,false))
            return holder
        }
        else{
            val holder: EditPeriodHolder = EditPeriodHolder(LayoutInflater.from(context).inflate(R.layout.layout_edit_period_row,parent,false))
            return holder
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(workout.getComponentAt(position).componentType == ComponentType.INTERVAL){
            return IS_INTERVAL
        }
        return IS_PERIOD
    }

    fun addToList(index: Int,component: WorkoutComponent){
        workout.insertWorkoutComponentAt(component,index)
        notifyItemInserted(index)
        notifyItemRangeChanged(index,workout.components.size)
        notifyDataSetChanged()
    }

    fun removeFromList(index:Int){
        workout.removeWorkOutComponents(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index,workout.components.size)
        context.editRecyclerView.removeViewAt(index)
        notifyDataSetChanged()

    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if(fromPosition < toPosition){
            for(i in fromPosition .. toPosition){
                Collections.swap(workout.components,i,i+1)
            }
        }
        else{
            for(i in fromPosition downTo toPosition){
                Collections.swap(workout.components,i,i-1)
            }
        }
        notifyItemMoved(fromPosition,toPosition)
    }

    override fun onItemSwipe(itemPosition: Int) {
        workout.removeWorkOutComponents(itemPosition)
        notifyItemRemoved(itemPosition)
    }

}