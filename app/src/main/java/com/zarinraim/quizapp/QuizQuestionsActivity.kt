package com.zarinraim.quizapp

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.zarinraim.quizapp.databinding.ActivityQuizQuestionsActitityBinding

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityQuizQuestionsActitityBinding

    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsActitityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mQuestionsList = Constants.getQuestions()

        setQuestion()

        binding.tvOptionOne.setOnClickListener(this)
        binding.tvOptionTwo.setOnClickListener(this)
        binding.tvOptionThree.setOnClickListener(this)
        binding.tvOptionFour.setOnClickListener(this)
        binding.tvOptionFour.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)
    }

    private fun setQuestion() {
        val question = mQuestionsList!![mCurrentPosition -1]

        defaultOptionsView()

        binding.tvQuestion.text = question.question

        binding.progressBar.progress = mCurrentPosition
        binding.tvProgress.text = getString(R.string.progress, mCurrentPosition, mQuestionsList!!.size)

        binding.ivImage.setImageResource(question.image)

        binding.tvOptionOne.text = question.option1
        binding.tvOptionTwo.text = question.option2
        binding.tvOptionThree.text = question.option3
        binding.tvOptionFour.text = question.option4
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        options.add(0, binding.tvOptionOne)
        options.add(1, binding.tvOptionTwo)
        options.add(2, binding.tvOptionThree)
        options.add(3, binding.tvOptionFour)

        for (option in options) {
            option.setTextColor(ContextCompat.getColor(this, R.color.options_text_color))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }

    private fun selectedOptionsView(tv: TextView, selectedOptionNumber: Int) {
        defaultOptionsView()

        mSelectedOptionPosition = selectedOptionNumber

        tv.setTextColor(ContextCompat.getColor(this, R.color.selected_option))
        tv.typeface = Typeface.DEFAULT_BOLD
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> binding.tvOptionOne.background = ContextCompat.getDrawable(
                this, drawableView
            )
            2 -> binding.tvOptionTwo.background = ContextCompat.getDrawable(
                this, drawableView
            )
            3 -> binding.tvOptionThree.background = ContextCompat.getDrawable(
                this, drawableView
            )
            4 -> binding.tvOptionFour.background = ContextCompat.getDrawable(
                this, drawableView
            )
        }
    }

    private fun checkAnswer() {
        if (mSelectedOptionPosition == 0) {
            mCurrentPosition++
            when {
                mCurrentPosition <= mQuestionsList!!.size -> {
                    setQuestion()
                    binding.btnSubmit.text = getString(R.string.submit)
                }
                else -> {
                    Toast.makeText(
                        this,
                        "You've successfully finished the Quiz",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            val question = mQuestionsList!![mCurrentPosition-1]

            if (mSelectedOptionPosition != question.correctAnswer) {
                answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
            }
            answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

            if (mCurrentPosition == mQuestionsList!!.size) {
                binding.btnSubmit.text = getString(R.string.finish)
            } else {
                binding.btnSubmit.text = getString(R.string.next_question)

            }
            mSelectedOptionPosition = 0
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_option_one ->
                selectedOptionsView(binding.tvOptionOne, 1)
            R.id.tv_option_two ->
                selectedOptionsView(binding.tvOptionTwo, 2)
            R.id.tv_option_three ->
                selectedOptionsView(binding.tvOptionThree, 3)
            R.id.tv_option_four ->
                selectedOptionsView(binding.tvOptionFour, 4)
            R.id.btn_submit ->
                checkAnswer()
        }
    }
}