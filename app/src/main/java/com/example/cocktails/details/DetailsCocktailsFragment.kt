package com.example.cocktails.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.example.cocktails.R
import com.example.cocktails.databinding.FragmentDetailsCocktailsBinding
import com.example.cocktails.databinding.FragmentEditCocktailsBinding
import com.example.cocktails.edit.EditCocktailsViewModel
import com.example.cocktails.main.MainFragmentDirections
import com.example.cocktails.utils.CocktailAll
import com.example.cocktails.utils.navController
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsCocktailsFragment : Fragment() {
    private var _binding: FragmentDetailsCocktailsBinding? = null
    private val binding get() = _binding!!

    private val args: DetailsCocktailsFragmentArgs by navArgs()
    private val detailsVm: DetailsCocktailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsCocktailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailsVm.setCocktail(args.CocktailBdl)

        with(binding) {
            detailsVm.cocktail.observe(viewLifecycleOwner) { cocktail ->

                tvDetailsTitle.text = cocktail.title
                tvDetailsIngredientsContent.text = cocktail.ingredients
                tvDetailsInstructionsContent.text = cocktail.instructions

                Picasso.get()
                    .load(cocktail.imageUrl)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(ivDetailsImage)

                btnDetailsModifier.visibility = if (cocktail.id == null) View.VISIBLE else View.GONE

                btnDetailsModifier.setOnClickListener {
                    val navDir =
                        DetailsCocktailsFragmentDirections
                            .actionDetailsCocktailsFragmentToEditCocktailsFragment2(cocktail)
                    navController.navigate(navDir)
                }
            }
        }

        detailsVm.navigationDestination.observe(viewLifecycleOwner) { destination ->
            destination?.let { navController.navigate(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}