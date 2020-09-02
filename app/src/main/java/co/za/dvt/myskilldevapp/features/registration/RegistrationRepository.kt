package co.za.dvt.myskilldevapp.features.registration

import android.app.Application
import co.za.dvt.myskilldevapp.features.database.PADatabase
import co.za.dvt.myskilldevapp.features.repositories.BaseRepositories
import co.za.dvt.myskilldevapp.models.RegistrationModel

class RegistrationRepository(var application: Application) : BaseRepositories() {
    var database = PADatabase.getInstance(application).USERSDAO

    suspend fun registerUser(params: Map<String, String>) : RegistrationModel {
        return try {
             retrofitHelper.registerMember(params) ?: RegistrationModel(false, "")
        }
        catch (ex: Exception){
             RegistrationModel(false, "$ex")
        }
    }
}