package co.za.dvt.myskilldevapp.features.dashboard

import co.za.dvt.myskilldevapp.features.database.GameStatsDAO
import co.za.dvt.myskilldevapp.features.database.tables.GameStats
import co.za.dvt.myskilldevapp.features.repositories.BaseRepositories
import kotlinx.coroutines.*
import java.util.HashMap

open class DashboardRepository(private val database: GameStatsDAO) : BaseRepositories() {
    suspend fun fetchLuckyNumber(payload: HashMap<String, String>) = try { retrofitHelper?.getLuckyNumner(payload) } catch (e: Exception){ null }
    suspend fun fetchAvailableCars() = try { retrofitHelper?.getAvailableCars() } catch (e: Exception){ null }
    suspend fun getAllStatsFromDB() = withContext(Dispatchers.IO){ database.getAllGameStats()}
    suspend fun addStatsToDB(currentStats: GameStats) = withContext(Dispatchers.IO){database.insert(currentStats) }
}