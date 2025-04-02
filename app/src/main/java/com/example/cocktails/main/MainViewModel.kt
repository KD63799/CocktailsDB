package com.example.cocktails.main

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.example.cocktails.data.local.Cocktail
import com.example.cocktails.data.repository.CocktailRepository
import com.example.cocktails.utils.CocktailAll
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository
) : ViewModel() {

    private var _listMerged = MutableLiveData<List<CocktailAll>>()
    var listMerged: LiveData<List<CocktailAll>> = _listMerged

    private val _navigationDestination = MutableLiveData<NavDirections>()
    val navigationDestination: LiveData<NavDirections>
        get() = _navigationDestination

    init {
        loadAllCocktails()
    }

    fun loadAllCocktails() {
        viewModelScope.launch {
            var fusionList = mutableListOf<CocktailAll>()
            val listAPI = cocktailRepository.getAllCocktailRemote().first
            val listLocal = cocktailRepository.getAllCocktailLocal()


            listAPI.forEach {
                with(it) {
                    fusionList.add(
                        CocktailAll(
                            id = idDrink.toInt(),
                            uuid = null,
                            title = strDrink!!,
                            imageUrl = strDrinkThumb!!,
                            ingredients = (
                                    (it.strIngredient1 ?: "") + " " + (it.strMeasure1
                                        ?: "") + "\n" +
                                            (it.strIngredient2 ?: "") + " " + (it.strMeasure2
                                        ?: "") + "\n" +
                                            (it.strIngredient3 ?: "") + " " + (it.strMeasure3
                                        ?: "") + "\n" +
                                            (it.strIngredient4 ?: "") + " " + (it.strMeasure4
                                        ?: "") + "\n" +
                                            (it.strIngredient5 ?: "") + " " + (it.strMeasure5
                                        ?: "") + "\n" +
                                            (it.strIngredient6 ?: "") + " " + (it.strMeasure6
                                        ?: "") + "\n" +
                                            (it.strIngredient7 ?: "") + " " + (it.strMeasure7
                                        ?: "") + "\n" +
                                            (it.strIngredient8 ?: "") + " " + (it.strMeasure8
                                        ?: "") + "\n" +
                                            (it.strIngredient9 ?: "") + " " + (it.strMeasure9
                                        ?: "") + "\n" +
                                            (it.strIngredient10 ?: "") + " " + (it.strMeasure10
                                        ?: "") + "\n" +
                                            (it.strIngredient11 ?: "") + " " + (it.strMeasure11
                                        ?: "") + "\n" +
                                            (it.strIngredient12 ?: "") + " " + (it.strMeasure12
                                        ?: "") + "\n" +
                                            (it.strIngredient13 ?: "") + " " + (it.strMeasure13
                                        ?: "") + "\n" +
                                            (it.strIngredient14 ?: "") + " " + (it.strMeasure14
                                        ?: "") + "\n" +
                                            (it.strIngredient15 ?: "") + " " + (it.strMeasure15
                                        ?: "") + "\n"
                                    ).trim(),
                            instructions = strInstructions!!
                        )
                    )
                }
            }

            listLocal.forEach {
                with(it) {
                    fusionList.add(
                        CocktailAll(
                            id = null,
                            uuid = uuid,
                            title = name,
                            imageUrl = url,
                            ingredients = ingredients,
                            instructions = instructions
                        )
                    )
                }
            }
            _listMerged.value = fusionList.sortedBy { it.title }
        }
    }

    fun whereDataFrom(cocktail: CocktailAll): Boolean {
        return when {
            cocktail.id == null -> true
            cocktail.uuid == null -> false
            else -> false
        }
    }

    fun navigateToAddCocktail() {
        _navigationDestination.value =
            MainFragmentDirections.actionMainFragmentToCreateCocktailsFragment()
        _navigationDestination.value = null
    }

}