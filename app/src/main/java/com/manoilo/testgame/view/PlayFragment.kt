package com.manoilo.testgame.view

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.manoilo.testgame.R
import com.manoilo.testgame.model.Fruit
import com.manoilo.testgame.provider.FruitProvider
import com.manoilo.testgame.provider.PlayViewModelFactory
import com.manoilo.testgame.util.formatAsCurrency
import com.manoilo.testgame.viewmodel.PlayViewModel
import kotlinx.android.synthetic.main.play_fragment.*


class PlayFragment : Fragment(R.layout.play_fragment) {

    private lateinit var viewModel: PlayViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        initOnClickListeners()
    }

    private fun initViewModel() {
        val creditObserver = Observer<Int> { credit ->
            creditValue.text = credit.formatAsCurrency()
        }

        val winObserver = Observer<Int> { win ->
            winValue.text = win.formatAsCurrency()
            setButtonEnableState(true)
            if (win != 0)
                MediaPlayer.create(context, R.raw.win_sound).start()
            if (viewModel.credit.value!! < viewModel.betSize.value!!)
                spin.isEnabled = false
        }

        val betObserver = Observer<Int> { bet ->
            betValue.text = bet.formatAsCurrency()
        }

        val fistWheelObserver = createWheelObserver(firstWheel)
        val secondWheelObserver = createWheelObserver(secondWheel)
        val thirdWheelObserver = createWheelObserver(thirdWheel)

        val isSpinButtonEnableObserver = Observer<Boolean> { isEnabled ->
            spin.isEnabled = isEnabled
        }

        val newViewModel by viewModels<PlayViewModel> {
            PlayViewModelFactory(FruitProvider())
        }
        viewModel = newViewModel
        viewModel.credit.observe(viewLifecycleOwner, creditObserver)
        viewModel.betSize.observe(viewLifecycleOwner, betObserver)
        viewModel.winSize.observe(viewLifecycleOwner, winObserver)

        viewModel.firstWheel.observe(viewLifecycleOwner, fistWheelObserver)
        viewModel.secondWheel.observe(viewLifecycleOwner, secondWheelObserver)
        viewModel.thirdWheel.observe(viewLifecycleOwner, thirdWheelObserver)
        viewModel.isSpinButtonEnable.observe(viewLifecycleOwner, isSpinButtonEnableObserver)
    }

    private fun initOnClickListeners() {
        betOne.setOnClickListener {
            viewModel.increaseBet()
        }

        betMax.setOnClickListener {
            viewModel.setMaxBet()
        }

        spin.setOnClickListener {
            viewModel.spin()
            setButtonEnableState(false)
        }

        payTable.setOnClickListener {
            val action = PlayFragmentDirections.actionPlayFragmentToPayTableFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun setButtonEnableState(isEnable: Boolean) {
        betOne.isEnabled = isEnable
        betMax.isEnabled = isEnable
        spin.isEnabled = isEnable
    }

    private fun createWheelObserver(imageView: ImageView) =
        Observer<Fruit> { fruit ->
            imageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), fruit.drawable))
        }

}