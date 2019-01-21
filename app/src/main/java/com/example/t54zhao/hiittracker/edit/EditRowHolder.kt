package com.example.t54zhao.hiittracker.edit

import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView

interface EditRowHolder {
    var rowHandle: ImageButton
    var rowTypeTV: TextView
    var rowTimeET: EditText
}