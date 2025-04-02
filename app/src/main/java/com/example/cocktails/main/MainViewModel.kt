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
): ViewModel() {

    private var _listMerged = MutableLiveData<List<CocktailAll>>()
    var listMerged: LiveData<List<CocktailAll>> = _listMerged

    private val _navigationDestination = MutableLiveData<NavDirections>()
    val navigationDestination: LiveData<NavDirections>
        get() = _navigationDestination

    init {
        loadAllCocktails()
    }

    fun loadAllCocktails(){
        viewModelScope.launch {
            var fusionList = mutableListOf<CocktailAll>()
            val listAPI = cocktailRepository.getAllCocktailRemote().first
            cocktailRepository
                .createLocalCocktail(
                    Cocktail(
                        UUID.randomUUID(),
                        "Vodka 2",
                        "x",
                        "nothing",
                        "nothing ing")
                )

            val listLocal = cocktailRepository.getAllCocktailLocal()


            listAPI.forEach {
                with(it){
                    fusionList.add(CocktailAll(
                        id = idDrink.toInt(),
                        uuid = null,
                        title = strDrink!!,
                        imageUrl = strDrinkThumb!!,
                        ingredients = strIngredient1!!,
                        instructions = strInstructions!!
                    ))
                }
            }

            listLocal.forEach {
                with(it){
                    fusionList.add(
                        CocktailAll(
                            id = null,
                            uuid = uuid,
                            title = name,
                            imageUrl = url,
                            ingredients = ingredients,
                            instructions = instructions
                        ) )
                }
            }
            _listMerged.value = fusionList
            Log.i(TAG, "getAll: ${fusionList}")
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