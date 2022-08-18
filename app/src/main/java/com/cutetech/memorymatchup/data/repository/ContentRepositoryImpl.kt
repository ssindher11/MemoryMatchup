package com.cutetech.memorymatchup.data.repository

import com.cutetech.memorymatchup.R.drawable
import com.cutetech.memorymatchup.domain.model.Tile
import com.cutetech.memorymatchup.domain.repository.ContentRepository
import com.cutetech.memorymatchup.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentRepositoryImpl @Inject constructor() : ContentRepository {

    private val tilesList = listOf(
        Tile("camel", drawable.camel),
        Tile("cat", drawable.cat),
        Tile("cow", drawable.cow),
        Tile("deer", drawable.deer),
        Tile("dog", drawable.dog),
        Tile("dragon", drawable.dragon),
        Tile("elephant", drawable.elephant),
        Tile("fox", drawable.fox),
        Tile("frog", drawable.frog),
        Tile("giraffe", drawable.giraffe),
        Tile("hedgehog", drawable.hedgehog),
        Tile("lion", drawable.lion),
        Tile("monkey", drawable.monkey),
        Tile("pterodactyl", drawable.pterodactyl),
        Tile("racoon", drawable.racoon),
        Tile("sparrow", drawable.sparrow),
        Tile("squirrel", drawable.squirrel),
        Tile("turkey", drawable.turkey),
        Tile("turtle", drawable.turtle),
        Tile("weasel", drawable.weasel),
    )

    override suspend fun getTiles(numberOfTiles: Int): Flow<Resource<List<Tile>>> {
        require(numberOfTiles % 2 == 0) { "Number of tiles must be even" }
        require(numberOfTiles <= tilesList.size) { "Number of tiles cannot be more than ${tilesList.size}" }
        return flow {
            emit(Resource.Loading(true))
            val halfList = tilesList.shuffled().take(numberOfTiles)
            val otherHalf = tilesList.shuffled()
            val combinedList = (halfList + otherHalf).shuffled()
            emit(Resource.Success(combinedList))
        }.flowOn(Dispatchers.IO)
    }
}