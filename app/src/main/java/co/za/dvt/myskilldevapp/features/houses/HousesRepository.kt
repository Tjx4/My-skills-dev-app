package co.za.dvt.myskilldevapp.features.houses

import co.za.dvt.myskilldevapp.features.repositories.BaseRepository

class HousesRepository : BaseRepository() {
    suspend fun getHouses(apiKey: String) = retrofitHelper?.getHouses(apiKey)
}