package com.example.cocktails.details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.cocktails.main.MainFragmentDirections
import com.example.cocktails.utils.CocktailAll
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


@HiltViewModel
class DetailsCocktailsViewModel @Inject constructor(
    @ApplicationContext val context: Context,
) : ViewModel() {

    private val _cocktail = MutableLiveData<CocktailAll>()
    val cocktail: LiveData<CocktailAll>
        get() = _cocktail

    private val _navigationDestination = MutableLiveData<NavDirections>()
    val navigationDestination: LiveData<NavDirections>
        get() = _navigationDestination

    fun setCocktail(cocktail: CocktailAll) {
        _cocktail.value = cocktail
    }
}