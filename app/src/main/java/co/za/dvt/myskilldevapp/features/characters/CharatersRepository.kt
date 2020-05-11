package co.za.dvt.myskilldevapp.features.characters

import co.za.dvt.myskilldevapp.features.repositories.BaseRepositories

class CharatersRepository : BaseRepositories(){
    suspend fun getStudents(apiKey: String) = try { retrofitHelper?.getStudents(apiKey) } catch (e: Exception){ null }

}