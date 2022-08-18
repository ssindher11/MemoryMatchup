package com.cutetech.memorymatchup.domain.repository

import com.cutetech.memorymatchup.domain.model.Tile
import com.cutetech.memorymatchup.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ContentRepository {
    suspend fun getTiles(numberOfTiles: Int): Flow<Resource<List<Tile>>>
}