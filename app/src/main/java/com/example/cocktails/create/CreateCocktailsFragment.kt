package com.example.cocktails.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cocktails.databinding.FragmentCreateCocktailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCocktailsFragment : Fragment() {
    private var _binding: FragmentCreateCocktailsBinding? = null
    private val binding get() = _binding!!

    private val createVm : CreateCocktailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateCocktailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
