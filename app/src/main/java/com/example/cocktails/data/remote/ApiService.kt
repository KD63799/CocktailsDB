package com.example.cocktails.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(ApiRoutes.GET_COCKTAILS_BY_PARAMS)
    suspend fun getAllDrinks(
        @Query("f", encoded = true) char : Char
    ) : Response<CocktailDto>?

    @GET(ApiRoutes.GET_COCKTAIL_BY_ID)
    suspend fun getDrinkById(
        @Query("i", encoded = true) id : String
    ) : Response<CocktailDto>?
}
