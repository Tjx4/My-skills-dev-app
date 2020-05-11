package co.za.dvt.myskilldevapp.features.spells

import co.za.dvt.myskilldevapp.features.repositories.BaseRepository

class SpellsRepository : BaseRepository() {
    suspend fun getSpells(apiKey: String) = retrofitHelper?.getSpells(apiKey)
}