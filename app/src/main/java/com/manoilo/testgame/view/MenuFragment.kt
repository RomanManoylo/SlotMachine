package com.manoilo.testgame.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.manoilo.testgame.R
import kotlinx.android.synthetic.main.menu_fragment.*
import kotlin.system.exitProcess

class MenuFragment : Fragment(R.layout.menu_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        exitButton.setOnClickListener { exitProcess(0) }
        playButton.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToPlayFragment()
            Navigation.findNavController(it).navigate(action)
        }
        super.onViewCreated(view, savedInstanceState)
    }
}