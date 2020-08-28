package co.za.dvt.myskilldevapp.features.forgotPassword

import android.app.Application
import co.za.dvt.myskilldevapp.features.database.PADatabase
import co.za.dvt.myskilldevapp.features.repositories.BaseRepositories

class ForgotPasswordRepository(var application: Application) : BaseRepositories() {
    var database = PADatabase.getInstance(application).USERSDAO

}