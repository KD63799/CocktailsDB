package com.example.cocktails.data.remote

import com.squareup.moshi.Json

data class CocktailDto(
    @Json(name = "drinks")
    val drinks: List<DrinkDto>?,
    val drinksError: String?
)