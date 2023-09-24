
package com.blumer.msu.lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.blumer.msu.lifecycle.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

private const val TAG = "MainActivity"
private var correctAnswersCount = 0

class MainActivity : AppCompatActivity() {

    private lateinit var binding :ActivityMainBinding

    private val questionBank = mutableListOf(
        Question(R.string.question_australia, answer = true),
        Question(R.string.question_oceans, answer = true),
        Question(R.string.question_mideast, answer = false),
        Question(R.string.question_africa, answer = false),
        Question(R.string.question_americas, answer = true),
        Question(R.string.question_asia,answer = true)
    )
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate (Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
        }

        binding.questionTextView.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        binding.nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        binding.previousButton.setOnClickListener {
            currentIndex = (currentIndex - 1 + questionBank.size) % questionBank.size
            updateQuestion()
        }

        updateQuestion()
    }

    private fun showQuizScore() {
        val score = (correctAnswersCount.toDouble() / questionBank.size.toDouble()) * 100
        val formattedScore = String.format("%.1f%%", score)

        Toast.makeText(this, "Quiz Score: $formattedScore", Toast.LENGTH_SHORT).show()

        correctAnswersCount = 0

        for (question in questionBank) {
            question.answered = false
        }
        currentIndex = 0
        updateQuestion()
    }


    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)

        if (!questionBank[currentIndex].answered) {
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
        } else {
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
        }
    }
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        if (!questionBank[currentIndex].answered) {
            questionBank[currentIndex].answered = true

            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false

            if (userAnswer == correctAnswer) {
                correctAnswersCount++
                Snackbar.make(
                    binding.root,
                    R.string.correct_snackbar,
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                Snackbar.make(
                    binding.root,
                    R.string.incorrect_snackbar,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            if (currentIndex == questionBank.size - 1) {
                showQuizScore()
            }
        }
    }

    override fun onStart(){
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume(){
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause(){
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    override fun onStop(){
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy(){
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}