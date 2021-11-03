package com.example.life.ui.wiki

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import com.example.life.R
import com.example.life.databinding.WikiFragmentBinding

class WikiFragment : Fragment() {

    private lateinit var binding: WikiFragmentBinding
    private lateinit var viewModel: WikiViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        val view = inflater.inflate(R.layout.wiki_fragment, container, false)
        viewModel = ViewModelProvider(this).get(WikiViewModel::class.java)
        binding = WikiFragmentBinding.bind(view).apply {
            this.viewmodel = viewModel
        }

        binding.youtubeButton.setOnClickListener{
            context?.startActivity(viewModel.intent)
        }

        binding.wikiView.webViewClient = WebViewClient()
        binding.wikiView.loadUrl(getString(R.string.wiki_link))
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.wiki_overflow, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.about_fragment -> {
                findNavController().navigate(R.id.action_wiki_to_about)
                true
            }
            else -> false
        }
}