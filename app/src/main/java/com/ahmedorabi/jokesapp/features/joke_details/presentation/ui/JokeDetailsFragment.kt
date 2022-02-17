package com.ahmedorabi.jokesapp.features.joke_details.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.ahmedorabi.jokesapp.databinding.FragmentJokeDetailsBinding
import com.ahmedorabi.jokesapp.domain.Joke


class JokeDetailsFragment : DialogFragment() {

    private var _binding: FragmentJokeDetailsBinding? = null
    private val binding get() = _binding!!
    private var joke: Joke? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        joke = arguments?.getParcelable("joke_item")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJokeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showJokeDetails()
    }

    @SuppressLint("SetTextI18n")
    private fun showJokeDetails() {
        joke?.let {
            binding.jokeIdTV.text = "ID : " + it.id
            binding.jokeTypeTV.text = "Type : " + it.type
            binding.jokeSetupTV.text = "Setup : " + it.setup
            binding.jokePunchlineTV.text = "Punchline : " + it.delivery
        }
    }

    override fun onResume() {
        super.onResume()
        val params: WindowManager.LayoutParams = dialog?.window?.attributes!!
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog!!.window?.attributes = params
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}