package co.za.dvt.myskilldevapp.features.registration

import android.app.Application
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel

class RegistrationViewModel(application: Application) : BaseVieModel(application) {
    private val registrationRepository: RegistrationRepository = RegistrationRepository(application)
}