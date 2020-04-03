package co.za.dvt.myskilldevapp.features.dashboard

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.za.dvt.myskilldevapp.features.database.GameStatsDAO
import java.lang.IllegalArgumentException

class DashboardViewModelFactory(private val dashboardRepository: DashboardRepository, private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DashboardViewModel::class.java)){
            return DashboardViewModel(dashboardRepository, application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
     }
}