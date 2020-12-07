package com.manoilo.testgame.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.manoilo.testgame.R
import com.manoilo.testgame.viewmodel.SplashScreenViewModel
import kotlinx.android.synthetic.main.splash_scree_fragment.*

class SplashScreenFragment : Fragment(R.layout.splash_scree_fragment) {

    private lateinit var viewModel: SplashScreenViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SplashScreenViewModel::class.java)

        val progressLevelObserver = Observer<Int> {
            it?.let {
                progressBar.progress = it
                if (it == 100) {
                    val action =
                        SplashScreenFragmentDirections.actionSplashScreenFragmentToMenuFragment()
                    Navigation.findNavController(progressBar).navigate(action)
                }
            }
        }
        viewModel.progressLevel.observe(viewLifecycleOwner, progressLevelObserver)

        viewModel.loadProgressBar()
    }
}