package com.example.t54zhao.hiittracker.edit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.widget.LinearLayout
import com.example.t54zhao.hiittracker.MainActivity
import com.example.t54zhao.hiittracker.models.Constants
import com.example.t54zhao.hiittracker.models.Workout
import com.example.t54zhao.hiittracker.R
import com.example.t54zhao.hiittracker.models.Period
import com.google.gson.Gson

class EditWorkoutActivity : Activity() , OnStartDragListener{

    var workout: Workout? = null
    var workoutGson: String? = null
    var adapter: EditRecyclerViewAdapter? = null
    lateinit var emptyStateView: LinearLayout
    lateinit var editRecyclerView: RecyclerView
    lateinit var touchHelper : ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_workout)

        val sharedprefs = this.getSharedPreferences(Constants.CONTEXT_MAIN_ACTIVITY, Context.MODE_PRIVATE);

        workoutGson = sharedprefs.getString(Constants.VAR_REF_GETWORKOUT, null);
        emptyStateView = findViewById<LinearLayout>(R.id.mainActivityEmptyStateView);
        editRecyclerView = findViewById<RecyclerView>(R.id.editRecyclerView);

        if (workoutGson == null) {
            emptyStateView.visibility = View.VISIBLE;
            editRecyclerView.visibility = View.GONE;
        } else {
            emptyStateView.visibility = View.GONE;
            editRecyclerView.visibility = View.VISIBLE;
            val gson = Gson()
            workout = gson.fromJson(workoutGson, Workout::class.java);
            setupAdapter()
            editRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        }

        if (workout == null) {
            workout = Workout()
        }
    }

    fun setupAdapter(){
        editRecyclerView.setHasFixedSize(true)
        adapter = EditRecyclerViewAdapter(workout!!, this,this);
        editRecyclerView.adapter = adapter

        var callback:ItemTouchHelper.Callback = TouchCallback(adapter!!)
        touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(editRecyclerView)

    }

    fun onClickQuitEdit(view: View) {
        if (view.id != R.id.editQuitButton) {
            return
        }
        // return to main page in new task
        quit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        quit()
    }

    fun onClickStartNewWorkout(view: View){
        if(view.id != R.id.createWorkoutButton){
            return
        }

        workout?.appendWorkoutComponents(Period(1))
        setupAdapter()
    }

    fun quit() {
        //TODO make an alert dialog to discard unsaved changes if there are unsaved changes
        if(workout != adapter?.workout){
            //TODO HERES THE DIALOG -> Save and quit, quit, cancel
        }
        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent);
        finish()
    }

    fun onClickSaveEdit(view: View) {
        if (view.id != R.id.editSaveButton) {
            return
        }
        workout = adapter?.workout
        //TODO save the new workout into the shared preferences

    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        touchHelper.startDrag(viewHolder)
    }


}

