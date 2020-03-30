package com.nikhil.omnicuris.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikhil.omnicuris.R
import com.nikhil.omnicuris.databinding.FragmentQuizBinding
import com.nikhil.omnicuris.ui.repository.model.Question
import org.json.JSONObject
import java.io.IOException

/**
 * Nikhil
 */
class QuizFragment : Fragment() {

    private lateinit var questions: MutableList<Question>
    private var questionIndex = 0
    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentQuizBinding>(
            inflater,
            R.layout.fragment_quiz,
            container,
            false
        )
        questions = mutableListOf()
        readQuestionsFromFile()
        binding.submitButton.setOnClickListener { view: View ->
            val checkedIdOfRadioButton = binding.questionRadioGroup.checkedRadioButtonId
            if (-1 != checkedIdOfRadioButton) {
                var answerIndex = 0
                when (checkedIdOfRadioButton) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                    R.id.fourthAnswerRadioButton -> answerIndex = 3
                }
                //First answer is the correct one
                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    questionIndex++
                    // Advance to the next question
                    if (questionIndex < 4) {
                        currentQuestion = questions[questionIndex]
                        setQuestion()
                        binding.invalidateAll()
                    } else {
                        view.findNavController().navigate(
                            QuizFragmentDirections.actionQuizFragmentToResultFragment(
                                4,
                                questionIndex
                            )
                        )
                    }
                } else {
                    //in case for lost condition you want to navigate to different screen
                    view.findNavController()
                        .navigate(
                            QuizFragmentDirections.actionQuizFragmentToResultFragment(
                                4,
                                questionIndex
                            )
                        )

                }
            }

        }
        randomTheQuestions()
        binding.quiz = this
        (activity as AppCompatActivity).supportActionBar?.title = "Questions"


        return binding.root
    }

    private fun readQuestionsFromFile() {
        val jsonFileString = getJsonDataFromAsset(requireContext(), "question.json")
        val gson = Gson()
        val arrayQuestionType = object : TypeToken<Array<Question>>() {}.type

        var questionFromJson: Array<Question> = gson.fromJson(jsonFileString, arrayQuestionType)

        questionFromJson.forEachIndexed { index, question -> questions.add(index, question) }

    }


    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    private fun randomTheQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
        answers = currentQuestion.answers.toMutableList()
        answers.shuffle()
    }
}
