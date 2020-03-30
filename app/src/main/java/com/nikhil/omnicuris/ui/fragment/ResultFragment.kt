package com.nikhil.omnicuris.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation

import com.nikhil.omnicuris.R
import com.nikhil.omnicuris.databinding.FragmentResultBinding

/**
 * Nikhil
 */
class ResultFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding = DataBindingUtil.inflate<FragmentResultBinding>(
            inflater,
            R.layout.fragment_result,
            container,
            false
        )

        binding.tryAgain.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_resultFragment_to_quizFragment)
        )
        val args = ResultFragmentArgs.fromBundle(arguments!!)
        binding.invalidateAll()
        binding.correctAnswers.text = args.numberOfCorrectAnswers.toString()
        binding.invalidAnswers.text = (4 - args.numberOfCorrectAnswers).toString()
        if (args.numberOfCorrectAnswers == args.numberOfQuestions) {
            binding.lottieAnimationView.setAnimation("win.json")
            binding.message.text = "Great! Winner"
            binding.tryAgain.text = "Play Again"
        } else {
            binding.lottieAnimationView.setAnimation("lose.json")
            binding.message.text = "Sorry you lost the game"
        }
        // Toast.makeText(context, "NumCorrect: ${args.numberOfCorrectAnswers}, NumQuestions: ${args.numberOfQuestions}", Toast.LENGTH_LONG).show()
        (activity as AppCompatActivity).supportActionBar?.title = "Your Result"

        return binding.root
    }

}
