package com.example.cocktails.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.cocktails.databinding.FragmentEditCocktailsBinding
import com.example.cocktails.utils.navController
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCocktailsFragment : Fragment() {
    private var _binding: FragmentEditCocktailsBinding? = null
    private val binding get() = _binding!!

    private val args: EditCocktailsFragmentArgs by navArgs()
    private val editVm: EditCocktailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditCocktailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editVm.setCocktail(args.CocktailBdl)

        editVm.cocktail.observe(viewLifecycleOwner){ cocktail ->
            binding.etEditName.setText(cocktail.title)
            binding.etEditImageurl.setText(cocktail.imageUrl)
            binding.etEditIngredients.setText(cocktail.ingredients)
            binding.etEditInstructions.setText(cocktail.instructions)
            Picasso.get()
                .load(cocktail.imageUrl)
                .placeholder(android.R.drawable.btn_dialog)
                .into(binding.ivEditImage)
        }

        binding.btnDetailsEdit.setOnClickListener {
            val updatedTitle = binding.etEditName.text.toString().trim()
            val updatedInstructions = binding.etEditInstructions.text.toString().trim()
            val updatedIngredients = binding.etEditIngredients.text.toString().trim()
            val updatedImgUrl = binding.etEditImageurl.text.toString().trim()
            editVm.updateCocktail(updatedTitle, updatedInstructions, updatedIngredients, updatedImgUrl)
        }

        binding.btnDetailsDelete.setOnClickListener {
            editVm.deleteCocktail()
        }

        editVm.operationSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                navController.popBackStack()
            }
        }

        editVm.messageLiveData.observe(viewLifecycleOwner) { message ->
            if (message.isNotEmpty()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
