package co.za.dvt.myskilldevapp.features.dashboard

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.constants.USER
import co.za.dvt.myskilldevapp.features.database.tables.GameStats
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel
import co.za.dvt.myskilldevapp.helpers.getYearMonthDayAndTime
import co.za.dvt.myskilldevapp.models.CarModel
import kotlinx.coroutines.*
import java.util.HashMap
import java.util.concurrent.TimeUnit

class DashboardViewModel(val dashboardRepository: DashboardRepository, application: Application) : BaseVieModel(application) {



}