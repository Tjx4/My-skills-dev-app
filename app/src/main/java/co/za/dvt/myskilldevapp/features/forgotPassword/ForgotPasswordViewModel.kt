package co.za.dvt.myskilldevapp.features.forgotPassword

import android.app.Application
import co.za.dvt.myskilldevapp.features.registration.RegistrationRepository
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel

class ForgotPasswordViewModel (application: Application, var registrationRepository: RegistrationRepository) : BaseVieModel(application) {

}