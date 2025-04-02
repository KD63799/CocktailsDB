package com.example.cocktails.create

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cocktails.R
import com.example.cocktails.databinding.FragmentCreateCocktailsBinding
import com.example.cocktails.utils.navController
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCocktailsFragment : Fragment() {
    private var _binding: FragmentCreateCocktailsBinding? = null
    private val binding get() = _binding!!

    private val createVm: CreateCocktailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateCocktailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etCreateImageurl.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(string: Editable?) {
                val imageUrl = string?.toString()?.trim() ?: ""
                if (imageUrl.isNotEmpty()) {
                    Picasso.get()
                        .load(imageUrl)
                        .placeholder(android.R.drawable.btn_dialog)
                        .into(binding.ivCreateImage)
                } else {
                    binding.ivCreateImage.setImageResource(android.R.drawable.btn_dialog)
                }
            }

            override fun beforeTextChanged(
                string: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                string: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }
        })


        binding.btnCreateAdd.setOnClickListener {
            val title = binding.etCreateName.text.toString().trim()
            val ingredients = binding.etCreateIngredients.text.toString().trim()
            val imageUrl = binding.etCreateImageurl.text.toString().trim()
            val instructions = binding.etCreateInstructions.text.toString().trim()

            createVm.checkFormAdd(title, ingredients, imageUrl, instructions)

        }

        createVm.navigationDestination.observe(viewLifecycleOwner) { destination ->
            destination?.let {
                if (it) {
                    navController.popBackStack()
                }
            }
        }

        createVm.userMessageLiveData.observe(viewLifecycleOwner) { message ->
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
