package co.za.dvt.myskilldevapp.features.dashboard

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.features.database.tables.UsersTable
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel
import co.za.dvt.myskilldevapp.models.HostModel
import kotlinx.coroutines.*

class DashboardViewModel(application: Application) : BaseVieModel(application) {
    private val dashboardRepository: DashboardRepository = DashboardRepository(application)

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean>
        get() = _showLoading

    private val _showContent: MutableLiveData<Boolean> = MutableLiveData()
    val showContent: MutableLiveData<Boolean>
        get() = _showContent

    var busyMessage: String = ""

    init {
    }



}