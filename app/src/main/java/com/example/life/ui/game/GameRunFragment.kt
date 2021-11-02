package com.example.life.ui.game

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.life.R
import com.example.life.databinding.GameRunFragmentBinding

// TODO fix hamburger menu not showing
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

        binding.conwayView.setOnClickListener{
            findNavController().navigate(R.id.action_game_run_to_edit)}

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.game_overflow, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.game_settings_fragment -> {
                findNavController().navigate(R.id.nav_game_settings)
                true
            }
            else -> false
        }
}