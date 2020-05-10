package co.za.dvt.myskilldevapp.features.students

import co.za.dvt.myskilldevapp.features.repositories.BaseRepositories

class StudentsRepository : BaseRepositories(){
    suspend fun getStudents(apiKey: String) = retrofitHelper?.getStudents(apiKey)
}