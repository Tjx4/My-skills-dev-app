package co.za.dvt.myskilldevapp.features.houses

import co.za.dvt.myskilldevapp.features.repositories.BaseRepositories

class HousesRepository : BaseRepositories() {
    suspend fun getHouses(apiKey: String) = try { retrofitHelper?.getHouses(apiKey) } catch (e: Exception){ null }
}