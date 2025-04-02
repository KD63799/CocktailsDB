package com.example.cocktails.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.cocktails.data.local.Cocktail
import java.util.UUID

@Dao
interface CocktailDao {

    @Query("SELECT * FROM cocktail")
    suspend fun getAllCocktail(): List<Cocktail>

    @Query("SELECT * FROM cocktail WHERE uuid = :cocktailUuid")
    suspend fun getCocktailById(cocktailUuid: UUID): Cocktail?

    @Insert
    suspend fun addCocktail(cocktail: Cocktail)

    @Update
    suspend fun updateCocktail(cocktail: Cocktail)

    @Delete
    suspend fun deleteCocktail(cocktail: Cocktail)
}