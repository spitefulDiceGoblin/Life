package com.example.life.ui.game.edit

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.example.life.R
import com.example.life.databinding.GameEditFragmentBinding

class GameEditFragment : Fragment() {

    private lateinit var binding: GameEditFragmentBinding

    private lateinit var viewModel: GameEditViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        val view = inflater.inflate(R.layout.game_edit_fragment, container, false)

        viewModel = ViewModelProvider(this).get(GameEditViewModel::class.java)
        binding = GameEditFragmentBinding.bind(view).apply {
            this.viewmodel = viewModel
        }

        val preferences = PreferenceManager.getDefaultSharedPreferences(context)

        val listener = OnSharedPreferenceChangeListener { preference, key ->
            binding.conwayView.isScrollingCounter = preferences.getBoolean("SCROLLING_COUNTER", true)
        }

        preferences.registerOnSharedPreferenceChangeListener(listener)

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
            R.id.action_clear -> {
                binding.conwayView.clear()
                true
            }
            else -> false
        }

}