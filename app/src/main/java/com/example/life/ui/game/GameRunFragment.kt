package com.example.life.ui.game

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.life.R
import com.example.life.custom.ShiftingConwayView

// TODO fix hamburger menu not showing
class GameRunFragment : Fragment() {

    companion object {
        fun newInstance() = GameRunFragment()
    }

    private lateinit var viewModel: GameRunViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        val view: View? =  inflater.inflate(R.layout.game_run_fragment, container, false)
        val game: ShiftingConwayView = view!!.findViewById(R.id.conwayView)

        game.setOnClickListener{ view -> findNavController().navigate(R.id.action_game_run_to_edit)}

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GameRunViewModel::class.java)
        // TODO: Use the ViewModel
    }

}