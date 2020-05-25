package com.example.onebyteassign.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.onebyteassign.R
import com.example.onebyteassign.custom.HorizontalView
import com.example.onebyteassign.supports.BaseFragment
import com.example.onebyteassign.viewmodels.HomeViewModel
import java.util.Observer


class HomeFragment : BaseFragment() {

    val viewModel by activityViewModels<HomeViewModel>()

    lateinit var categoryView: HorizontalView
    lateinit var latestView: HorizontalView
    lateinit var popularView: HorizontalView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_home, container, false)

        categoryView = view.findViewById(R.id.category)
        categoryView.textView.text = "Categories"

        latestView = view.findViewById(R.id.latest)
        latestView.textView.text = "Latest"

        popularView = view.findViewById(R.id.popular)
        popularView.textView.text = "Popular"

        viewModel.movies.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {

                latestView.loadMoviesData(viewModel.movies.value!!)

                var popular = viewModel.movies.value!!.filter {
                    it.rating > 8
                } as ArrayList
                popularView.loadMoviesData(popular)
            }
        )
        viewModel.categories.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                categoryView.loadCategoriesData(viewModel.categories.value!!)
            }
        )
        return view
    }

    override fun onStart() {
        super.onStart()

        viewModel.fetchAndSubscribeToMovies()
        viewModel.fetchAndSubscribeToCattegory()

    }
}
