package co.za.dvt.myskilldevapp.features.houses

import co.za.dvt.myskilldevapp.features.repositories.BaseRepository

class HousesRepository : BaseRepository() {
    suspend fun getHouses(apiKey: String) = try { retrofitHelper?.getHouses(apiKey) } catch (e: Exception){ null }
}