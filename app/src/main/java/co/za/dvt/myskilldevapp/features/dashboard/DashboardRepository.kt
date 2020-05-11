package co.za.dvt.myskilldevapp.features.dashboard

import co.za.dvt.myskilldevapp.features.database.GameStatsDAO
import co.za.dvt.myskilldevapp.features.database.tables.GameStats
import co.za.dvt.myskilldevapp.features.repositories.BaseRepositories
import kotlinx.coroutines.*
import java.util.HashMap

open class DashboardRepository(var database: GameStatsDAO) : BaseRepositories() {

}