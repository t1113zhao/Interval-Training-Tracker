package com.example.t54zhao.hiittracker.edit

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.example.t54zhao.hiittracker.R
import com.example.t54zhao.hiittracker.models.ComponentType
import com.example.t54zhao.hiittracker.models.Period

class EditPeriodHolder(itemView: View?) : RecyclerView.ViewHolder(itemView), EditRowHolder {

    override var rowTypeTV: TextView = itemView!!.findViewById(R.id.edit_period_row_name_textView)
    override var rowTimeET: EditText = itemView!!.findViewById(R.id.edit_period_row_time_editText)
    override var rowHandle: ImageButton = itemView!!.findViewById(R.id.edit_period_row_handle)

    fun bindData(period: Period){
        when(period.componentType){
            ComponentType.WARMUP -> rowTypeTV.setText(R.string.warmupRowString)
            ComponentType.COOLDOWN -> rowTypeTV.setText(R.string.cooldownRowString)
            ComponentType.HIGH -> rowTypeTV.setText(R.string.highRowString)
            ComponentType.LOW -> rowTypeTV.setText(R.string.lowRowString)
        }
        rowTimeET.setText(StringBuilder().append(period.time).append("s"));
    }
}