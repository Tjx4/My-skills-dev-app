package co.za.dvt.myskilldevapp.features.dashboard

import android.app.Application
import co.za.dvt.myskilldevapp.features.database.PADatabase
import co.za.dvt.myskilldevapp.features.database.tables.UsersTable
import co.za.dvt.myskilldevapp.helpers.RetrofitHelper
import kotlinx.coroutines.*

open class DashboardRepository(var database: PADatabase, var retrofitHelper: RetrofitHelper) {

    suspend fun addStatsToDB(currentStats: UsersTable) = withContext(Dispatchers.IO){database.USERSDAO.insert(currentStats) }
}