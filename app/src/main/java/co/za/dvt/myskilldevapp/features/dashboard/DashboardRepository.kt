package co.za.dvt.myskilldevapp.features.dashboard

import co.za.dvt.myskilldevapp.features.database.GameStatsDAO
import co.za.dvt.myskilldevapp.features.database.tables.GameStats
import co.za.dvt.myskilldevapp.features.repositories.BaseRepositories
import kotlinx.coroutines.*
import java.util.HashMap

open class DashboardRepository(private val database: GameStatsDAO) : BaseRepositories() {

    private var job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val ioScope = CoroutineScope(Dispatchers.IO + job)

    suspend fun fetchLuckyNumber(payload: HashMap<String, String>) = try { retrofitHelper?.getLuckyNumner(payload) } catch (e: Exception){ null }

    suspend fun fetchAvailableCars() = try { retrofitHelper?.getAvailableCars() } catch (e: Exception){ null }

    suspend fun clear() {
        withContext(Dispatchers.IO){
            database.clear()
        }
    }

    suspend fun getCurrentStatsFromDB(): GameStats {
        return withContext(Dispatchers.IO){
            var stats = database.getCurrentStats()

            if(stats?.endTime != stats?.startTime){
                null
            }

            stats
        }
    }

    suspend fun getAllStatsFromDB(): List<GameStats>?{
        return withContext(Dispatchers.IO){
            var stats = database.getAllGameStats()
            stats
        }
    }

    suspend fun addStatsToDB(currentStats: GameStats) {
        withContext(Dispatchers.IO){
            database.insert(currentStats)
        }
    }

    suspend fun updateStats(oldStats: GameStats) {
        withContext(Dispatchers.IO){
            database.update(oldStats)
        }
    }
}