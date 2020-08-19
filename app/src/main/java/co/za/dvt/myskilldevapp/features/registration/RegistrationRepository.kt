package co.za.dvt.myskilldevapp.features.registration

import android.app.Application
import co.za.dvt.myskilldevapp.features.database.PADatabase
import co.za.dvt.myskilldevapp.features.repositories.BaseRepositories

class RegistrationRepository(var application: Application) : BaseRepositories() {
    var database = PADatabase.getInstance(application).PADAO

}