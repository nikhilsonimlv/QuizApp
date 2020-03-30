package com.nikhil.omnicuris.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation

import com.nikhil.omnicuris.R
import com.nikhil.omnicuris.databinding.FragmentHomeBinding

/**
 * Nikhil
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )
        binding.startButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_quizFragment)
        )
        binding.homeIcon.setAnimation("home.json")
        
        (activity as AppCompatActivity).supportActionBar?.title = "Omnicuris Quiz"
        return binding.root
    }

}
