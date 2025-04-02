package com.example.cocktails.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "cocktail")
data class Cocktail(
    @PrimaryKey val uuid: UUID,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "url_image") val url : String,
    @ColumnInfo(name = "instructions") val instructions : String,
    @ColumnInfo(name = "ingredients") val ingredients: String,
)