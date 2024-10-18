package com.example.mytrainingsession

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

class StartActivity : BaseActivity() {

    private lateinit var titleTV: TextView
    private lateinit var exercisesListLV: ListView

    //Названия упражнений
    private val exercises = ExerciseDataBase.exercises.map { it.name }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        setupWindowInsets(R.id.start)
        setupToolbar(R.id.toolbar, false)

        titleTV = findViewById(R.id.titleTV)
        exercisesListLV = findViewById(R.id.exercisesListLV)

        //Заполним listView упражнениями
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, exercises)
        exercisesListLV.adapter = adapter

        exercisesListLV.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("EXERCISE_INDEX", position)
            }
            startActivity(intent)
        }
    }
}