package com.example.life.ui.wiki

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.life.ConwayArray
import com.example.life.R
import com.example.life.databinding.ActivityHomescreenBinding
import com.example.life.databinding.WikiFragmentBinding

class WikiFragment : Fragment() {

    private lateinit var binding: WikiFragmentBinding
    private lateinit var viewModel: WikiViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.wiki_fragment, container, false)
        viewModel = ViewModelProvider(this).get(WikiViewModel::class.java)
        binding = WikiFragmentBinding.bind(view).apply {
            this.viewmodel = viewModel
        }

        return view
    }
}