package com.example.cocktails.create

import android.content.Context
import androidx.lifecycle.*
import androidx.navigation.NavDirections
import com.example.cocktails.R
import com.example.cocktails.data.local.Cocktail
import com.example.cocktails.data.repository.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateCocktailsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val cocktailRepository: CocktailRepository
) : ViewModel() {

    private val _userMessageLiveData = MutableLiveData<String>()
    val userMessageLiveData: LiveData<String>
        get() = _userMessageLiveData

    private val _navigationDestination = MutableLiveData<NavDirections>()
    val navigationDestination: LiveData<NavDirections>
        get() = _navigationDestination

    fun checkFormAdd(
        title: String,
        ingredients: String,
        imageUrl: String,
        instructions: String
    ) {
        if (title.isNotEmpty() && ingredients.isNotEmpty() && imageUrl.isNotEmpty() && instructions.isNotEmpty()) {
            addCocktail(title, ingredients, imageUrl, instructions)
        } else {
            _userMessageLiveData.value = context.getString(R.string.remplissez_tout_les_champs)
        }
    }

    fun addCocktail(
        title: String,
        ingredients: String,
        imageUrl: String,
        instructions: String
    ) {
        viewModelScope.launch {
            try {
                val cocktail = Cocktail(
                    uuid = UUID.randomUUID(),
                    name = title,
                    url = imageUrl,
                    instructions = instructions,
                    ingredients = ingredients
                )
                val result = cocktailRepository.createLocalCocktail(cocktail)
                if (result == "ok") {
                    //_navigationDestination.value =
                        //CreateCocktailsFragmentDirections.actionCreateCocktailsFragmentToMainFragment()
                } else {
                    _userMessageLiveData.value = context.getString(R.string.erreur_creation_cocktail)
                }
            } catch (e: Exception) {
                _userMessageLiveData.value = e.message ?: context.getString(R.string.une_erreur_est_survenue)
            }
        }
    }
}
