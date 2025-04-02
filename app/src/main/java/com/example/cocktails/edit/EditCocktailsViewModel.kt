package com.example.cocktails.edit

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails.R
import com.example.cocktails.data.local.Cocktail
import com.example.cocktails.data.repository.CocktailRepository
import com.example.cocktails.utils.CocktailAll
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EditCocktailsViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val cocktailRepository: CocktailRepository
) : ViewModel() {

    private val _cocktail = MutableLiveData<CocktailAll>()
    val cocktail: LiveData<CocktailAll> get() = _cocktail

    private val _operationSuccess = MutableLiveData<Boolean>()
    val operationSuccess: LiveData<Boolean> get() = _operationSuccess

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> get() = _messageLiveData

    fun updateCocktail(
        updatedTitle: String,
        updatedInstructions: String,
        updatedIngredients: String,
        updatedImgUrl: String
    ) {
        if (updatedTitle.isEmpty() ||
            updatedInstructions.isEmpty() ||
            updatedIngredients.isEmpty() ||
            updatedImgUrl.isEmpty()
        ) {
            _messageLiveData.value = context.getString(R.string.remplissez_tout_les_champs)
            return
        }
        val currentCocktail = _cocktail.value
        if (currentCocktail == null) {
            _messageLiveData.value = context.getString(R.string.cocktail_introuvable)
            return
        }
        val updatedCocktail = currentCocktail.uuid?.let {
            Cocktail(
                uuid = it,
                name = updatedTitle,
                url = updatedImgUrl,
                instructions = updatedInstructions,
                ingredients = updatedIngredients
            )
        }
        viewModelScope.launch {
            try {
                val result = updatedCocktail?.let { cocktailRepository.updateLocalCocktail(it) }
                if (result == "ok") {
                    _operationSuccess.value = true
                    _messageLiveData.value = context.getString(R.string.modification_enregistr_e)
                } else {
                    _messageLiveData.value = context.getString(R.string.erreur_creation_cocktail)
                }
            } catch (e: Exception) {
                _messageLiveData.value = e.message ?: context.getString(R.string.erreur_creation_cocktail)
            }
        }
    }

    fun deleteCocktail() {
        val currentCocktail = _cocktail.value
        if (currentCocktail == null) {
            _messageLiveData.value = context.getString(R.string.cocktail_introuvable)
            return
        }
        val cocktailToDelete = currentCocktail.uuid?.let {
            Cocktail(
                uuid = it,
                name = currentCocktail.title,
                url = currentCocktail.imageUrl,
                instructions = currentCocktail.instructions,
                ingredients = currentCocktail.ingredients
            )
        }
        viewModelScope.launch {
            try {
                val result = cocktailToDelete?.let { cocktailRepository.deleteLocalCocktail(it) }
                if (result == "ok") {
                    _operationSuccess.value = true
                    _messageLiveData.value = context.getString(R.string.cocktail_supprim)
                } else {
                    _messageLiveData.value = context.getString(R.string.erreur_supression_cocktail)
                }
            } catch (e: Exception) {
                _messageLiveData.value = e.message ?: context.getString(R.string.erreur_supression_cocktail)
            }
        }
    }

    fun setCocktail(cocktail: CocktailAll) {
        _cocktail.value = cocktail
    }
}
