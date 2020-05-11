package co.za.dvt.myskilldevapp.features.characters

import co.za.dvt.myskilldevapp.features.repositories.BaseRepository

class CharatersRepository : BaseRepository(){
    suspend fun getCharacters(apiKey: String) = retrofitHelper?.getCharacters(apiKey)
    suspend fun getCharactersInhouse(apiKey: String, house: String) = retrofitHelper?.getCharactersInHouse(apiKey, house)
}