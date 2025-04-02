package com.example.cocktails.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cocktails.data.local.dao.CocktailDao


@Database(entities = [Cocktail::class], version = 1)
abstract class AppDatabase : RoomDatabase() {


    abstract fun coktailDao(): CocktailDao
}