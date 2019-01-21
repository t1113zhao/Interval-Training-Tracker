package com.example.t54zhao.hiittracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.t54zhao.hiittracker.edit.EditWorkoutActivity
import com.example.t54zhao.hiittracker.models.*
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    var workout: Workout? = null
    var workoutGson: String? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedprefs = this.getSharedPreferences(Constants.CONTEXT_MAIN_ACTIVITY, Context.MODE_PRIVATE);

        workoutGson = sharedprefs.getString(Constants.VAR_REF_GETWORKOUT, null)
        val emptyStateView = findViewById<LinearLayout>(R.id.mainActivityEmptyStateView)
        val workoutView = findViewById<RecyclerView>(R.id.workoutRecyclerView)
        val editOrRunButtons = findViewById<LinearLayout>(R.id.editOrRunButtons)

        if (workoutGson == null) {
            emptyStateView.visibility = View.VISIBLE;
            workoutView.visibility = View.GONE;
            editOrRunButtons.visibility = View.GONE
        } else {
            emptyStateView.visibility = View.GONE;
            workoutView.visibility = View.VISIBLE;
            val gson = Gson()
            workout = gson.fromJson(workoutGson, Workout::class.java);
        }
        workoutView.adapter = WorkoutViewAdapter(workout, this);
        workoutView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val titleTextView = findViewById<TextView>(R.id.mainActivityTopBarTV)
        titleTextView.setText(workout?.name)
    }

    fun onClickEdit(view: View) {
        if(view.id == R.id.editButton || view.id == R.id.createWorkoutButton){
            val openEditIntent = Intent(this,EditWorkoutActivity::class.java)
            startActivity(openEditIntent);
            finish()
        }
        return
    }

    fun onClickRun(view: View) {
        if(view.id == R.id.runButton){

        }
        finish()
    }

    fun onClickQuit(view: View) {
        finish()
    }

    fun onClickSettings(view: View) {
        val openSettingsIntent = Intent(this, SettingsActivity::class.java)
        startActivity(openSettingsIntent)
        this.finish()
    }
}
