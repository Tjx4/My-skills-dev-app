package co.za.dvt.myskilldevapp.features.dashboard

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.za.dvt.myskilldevapp.features.database.PADatabase
import co.za.dvt.myskilldevapp.helpers.API
import java.lang.IllegalArgumentException

class DashboardViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DashboardViewModel::class.java)){
            var database = PADatabase.getInstance(application)
            val retrofitHelper = API.retrofitHelper
            val dashboardRepository = DashboardRepository(database, retrofitHelper)
            return DashboardViewModel(application, dashboardRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
     }
}