package com.example.life.ui.game.run

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.life.ConwayArray
import com.example.life.R
import com.example.life.databinding.GameRunFragmentBinding
import java.util.*

class GameRunFragment : Fragment() {

    private lateinit var binding: GameRunFragmentBinding
    private lateinit var viewModel: GameRunViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        val view =  inflater.inflate(R.layout.game_run_fragment, container, false)

        viewModel = ViewModelProvider(this).get(GameRunViewModel::class.java)
        binding = GameRunFragmentBinding.bind(view).apply {
            this.viewmodel = viewModel
        }

        binding.lifecycleOwner = this.viewLifecycleOwner

        binding.conwayView.setOnClickListener{
            findNavController().navigate(GameRunFragmentDirections.actionGameRunToEdit())}

        viewModel.refresh.observe(viewLifecycleOwner, { refresh ->
            if (refresh) binding.conwayView.liveLife()
        })

        ConwayArray.aliveCells.observe(viewLifecycleOwner, {
            viewModel.updateAlive()
        })

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.game_overflow, menu)
    }

    override fun onResume() {
        super.onResume()
        viewModel.startTimer()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopTimer()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.game_settings_fragment -> {
                findNavController().navigate(R.id.nav_game_settings)
                true
            }
            R.id.action_clear -> {
                binding.conwayView.clear()
                true
            }
            else -> false
        }
}