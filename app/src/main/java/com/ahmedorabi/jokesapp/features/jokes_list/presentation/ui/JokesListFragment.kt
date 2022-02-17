package com.ahmedorabi.jokesapp.features.jokes_list.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ahmedorabi.jokesapp.R
import com.ahmedorabi.jokesapp.databinding.FragmentJokesListBinding
import com.ahmedorabi.jokesapp.features.jokes_list.presentation.ui.adapter.JokeAdapter
import com.ahmedorabi.jokesapp.features.jokes_list.presentation.viewmodel.JokesListEvents
import com.ahmedorabi.jokesapp.features.jokes_list.presentation.viewmodel.JokesListViewState
import com.ahmedorabi.jokesapp.features.jokes_list.presentation.viewmodel.JokesListViewModel
import com.ahmedorabi.jokesapp.features.utils.EspressoIdlingResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class JokesListFragment : Fragment() {

    private var _binding: FragmentJokesListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: JokesListViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentJokesListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getJokes()
        observeViewModel()
        setupSwipeToRefresh()
    }

    private fun getJokes() {
        EspressoIdlingResource.increment()
        lifecycleScope.launch {
            viewModel.jokeIntent.send(JokesListEvents.GetAllJokes)
        }
    }

    private fun setupSwipeToRefresh() {
        binding.swipeToRefresh.setOnRefreshListener {
            binding.swipeToRefresh.isRefreshing = false
            getJokes()
        }
    }

    private fun observeViewModel() {

        lifecycleScope.launch {
            viewModel.state.collect { userState ->
                when (userState) {
                    JokesListViewState.Idle -> {

                    }
                    is JokesListViewState.Loading -> {
                        showLoading()
                    }
                    is JokesListViewState.SuccessResponse -> {
                        hideLoading()

                        EspressoIdlingResource.decrement()
                        val adapter = JokeAdapter { joke ->
                            val bundle = Bundle()
                            bundle.putParcelable("joke_item", joke)
                            findNavController().navigate(
                                R.id.action_jokesListFragment_to_jokeDetailsFragment,
                                bundle
                            )
                        }
                        adapter.submitList(userState.data.jokes)
                        binding.recyclerViewMain.adapter = adapter

                        binding.swipeToRefresh.isRefreshing = false
                    }
                    is JokesListViewState.Error -> {
                        hideLoading()
                        Toast.makeText(activity, userState.errorMsg, Toast.LENGTH_LONG).show()
                    }

                }
            }
        }

    }


    private fun showLoading() {
        binding.progressbar.visibility = View.VISIBLE
        binding.recyclerViewMain.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.progressbar.visibility = View.GONE
        binding.recyclerViewMain.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}