package com.zarinraim.quizapp

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.zarinraim.quizapp.databinding.ActivityMainBinding
import com.zarinraim.quizapp.databinding.ActivityQuizQuestionsActitityBinding
import org.w3c.dom.Text

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityQuizQuestionsActitityBinding

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
    }

    private fun setQuestion() {
        val question = mQuestionsList!![mCurrentPosition]

        defaultOptionsView()

        binding.tvQuestion.text = question.question

        binding.progressBar.progress = mCurrentPosition + 1
        binding.tvProgress.text = "${mCurrentPosition + 1}/${mQuestionsList!!.size}"

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
                R.drawable.default_option_border_bg)
        }
    }


    private fun selectedOptionsView(tv: TextView, selectedOptionNumber: Int) {
        defaultOptionsView()

        mSelectedOptionPosition = selectedOptionNumber

        tv.setTextColor(ContextCompat.getColor(this, R.color.selected_option))
        tv.typeface = Typeface.DEFAULT_BOLD
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_option_one ->
                selectedOptionsView(binding.tvOptionOne, 1)
            R.id.tv_option_two ->
                selectedOptionsView(binding.tvOptionTwo, 2)
            R.id.tv_option_three->
                selectedOptionsView(binding.tvOptionThree, 3)
            R.id.tv_option_four ->
                selectedOptionsView(binding.tvOptionFour, 4)
        }
    }
}