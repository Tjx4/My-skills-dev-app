package co.za.dvt.myskilldevapp.features.registration

import co.za.dvt.myskilldevapp.helpers.RetrofitHelper
import co.za.dvt.myskilldevapp.models.RegistrationModel

class RegistrationRepository(var retrofitHelper: RetrofitHelper) {

    suspend fun registerUser(params: Map<String, String>) : RegistrationModel {
        return try {
             retrofitHelper.registerMember(params) ?: RegistrationModel(false, "")
        }
        catch (ex: Exception){
             RegistrationModel(false, "$ex")
        }
    }
}