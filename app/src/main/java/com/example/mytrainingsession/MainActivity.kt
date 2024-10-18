package com.example.mytrainingsession

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import java.util.Locale

class MainActivity : BaseActivity() {

    private val exercises = ExerciseDataBase.exercises
    private lateinit var titleTV: TextView
    private lateinit var timerTV: TextView
    private lateinit var quickViewCB: CheckBox
    private lateinit var startButton: Button
    private lateinit var exerciseTV: TextView
    private lateinit var descriptionTV: TextView
    private lateinit var nextButton: Button
    private lateinit var imageViewIV: ImageView

    private var exerciseIndex = 0
    private var exerciseCount = 0

    private lateinit var currentExercise: Exercise
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupWindowInsets(R.id.main)
        setupToolbar(R.id.toolbar, true)

        titleTV = findViewById(R.id.titleTV)
        timerTV = findViewById(R.id.timerTV)
        quickViewCB = findViewById(R.id.quickViewCB)
        startButton = findViewById(R.id.startButton)
        exerciseTV = findViewById(R.id.exerciseTV)
        descriptionTV = findViewById(R.id.descriptionTV)
        nextButton = findViewById(R.id.nextButton)
        imageViewIV = findViewById(R.id.imageViewIV)

        exerciseIndex = intent.getIntExtra("EXERCISE_INDEX", 0)

        startButton.setOnClickListener { startWorkout() }
        nextButton.setOnClickListener { nextExercise() }
    }

    private fun nextExercise() {
        timer.cancel()
        nextButton.isEnabled = false
        startNextExercise()
    }

    private fun startWorkout() {
        //exerciseIndex = 0
        titleTV.text = getString(R.string.startWorkoutText)
        quickViewCB.isEnabled = false
        startButton.isEnabled = false
        startButton.text = getString(R.string.workoutStartButtonText)
        startNextExercise()
    }

    private fun startNextExercise() {
        if (exerciseCount < exercises.size) {
            //Начнем с первого упражнения, если дошли до конца
            if (exerciseIndex >= exercises.size) exerciseIndex = 0
            currentExercise = exercises[exerciseIndex]
            exerciseTV.text = currentExercise.name
            descriptionTV.text = currentExercise.description
            imageViewIV.setImageResource(currentExercise.gifImage)
            val duration =
                if (quickViewCB.isChecked) 3 * 1000L else currentExercise.durationInSeconds * 1000L
            timerTV.text = formatTime(duration.toInt())
            timer = object : CountDownTimer(duration, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timerTV.text = formatTime((millisUntilFinished / 1000).toInt())
                }

                override fun onFinish() {
                    timerTV.text = getString(R.string.finishTimerTVtext)
                    imageViewIV.visibility = View.VISIBLE
                    nextButton.isEnabled = true
                    imageViewIV.setImageResource(0)
                }
            }.start()
            exerciseIndex++
            exerciseCount++
        } else {
            exerciseTV.text = getString(R.string.exerciseText)
            descriptionTV.text = getString(R.string.empty)
            timerTV.text = getString(R.string.empty)
            nextButton.isEnabled = false
            quickViewCB.isEnabled = true
            startButton.isEnabled = true
            startButton.text = getString(R.string.startBTNtxt)
            exerciseCount = 0
        }
    }

    private fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, remainingSeconds)
    }
}