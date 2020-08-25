package co.za.dvt.myskilldevapp.features.dashboard

import android.app.Application
import co.za.dvt.myskilldevapp.features.database.PADatabase
import co.za.dvt.myskilldevapp.features.database.tables.UsersTable
import co.za.dvt.myskilldevapp.features.repositories.BaseRepositories
import kotlinx.coroutines.*
import java.util.HashMap

open class DashboardRepository(var application: Application) : BaseRepositories() {
    var database = PADatabase.getInstance(application).PADAO

    suspend fun fetchLuckyNumber(token: String, payload: HashMap<String, String>) = try { retrofitHelper?.loginMember(token, payload) } catch (e: Exception){ null }
    suspend fun fetchAvailableCars() = try { retrofitHelper?.getAvailableCars() } catch (e: Exception){ null }
    suspend fun getAllStatsFromDB() = withContext(Dispatchers.IO){ database.getAllUsers()}
    suspend fun addStatsToDB(currentStats: UsersTable) = withContext(Dispatchers.IO){database.insert(currentStats) }
}