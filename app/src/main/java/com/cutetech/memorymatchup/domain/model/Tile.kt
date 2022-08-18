package com.cutetech.memorymatchup.domain.model

import androidx.annotation.DrawableRes

data class Tile(
    val name: String,
    @DrawableRes val imageRes: Int,
)
