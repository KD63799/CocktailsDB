package com.example.cocktails.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class CocktailAll(
    val id: Int?,
    val uuid: UUID?,
    val title: String,
    val imageUrl: String,
    val ingredients: String,
    val instructions: String
) : Parcelable