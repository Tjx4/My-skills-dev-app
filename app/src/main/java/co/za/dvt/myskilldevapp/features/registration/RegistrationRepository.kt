package co.za.dvt.myskilldevapp.features.registration

import android.app.Application
import co.za.dvt.myskilldevapp.features.database.PADatabase
import co.za.dvt.myskilldevapp.features.repositories.BaseRepositories
import co.za.dvt.myskilldevapp.models.LoginModel
import co.za.dvt.myskilldevapp.models.RegistrationModel

class RegistrationRepository(var application: Application) : BaseRepositories() {
    var database = PADatabase.getInstance(application).PADAO

    suspend fun registerUer(params: Map<String, String>) : RegistrationModel? {
        try {
            return retrofitHelper.registerMember(params)
        }
        catch (ex: Exception){
            return RegistrationModel(false, "$ex")
        }
    }
}