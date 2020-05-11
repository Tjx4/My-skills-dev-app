package co.za.dvt.myskilldevapp.features.characters

import co.za.dvt.myskilldevapp.features.repositories.BaseRepository

class CharatersRepository : BaseRepository(){
    suspend fun getStudents(apiKey: String) = try { retrofitHelper?.getStudents(apiKey) } catch (e: Exception){ null }

}