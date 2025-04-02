package com.example.cocktails.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktails.databinding.FragmentMainBinding
import com.example.cocktails.utils.navController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CocktailAdapter
    private val mainVm : MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listAdapter.layoutManager = LinearLayoutManager(requireContext())
        adapter = CocktailAdapter(
            onItemClick = { cocktail ->
                if (mainVm.whereDataFrom(cocktail)) {
                    val navDir =
                        MainFragmentDirections.actionMainFragmentToDetailsCocktailsFragment(cocktail)
                    navController.navigate(navDir)
                } else {
                    val navDir =
                        MainFragmentDirections.actionMainFragmentToDetailsCocktailsFragment(cocktail)
                    navController.navigate(navDir)
                }
            }
        )
        binding.listAdapter.adapter = adapter

        mainVm.listMerged.observe(viewLifecycleOwner) { cocktails ->
            adapter.submitList(cocktails)
        }

        binding.btnMainAdd.setOnClickListener {
            mainVm.navigateToAddCocktail()
        }

        mainVm.navigationDestination.observe(viewLifecycleOwner) { destination ->
            destination?.let { navController.navigate(it) }
        }

    }

    override fun onResume() {
        super.onResume()
        mainVm.loadAllCocktails()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
