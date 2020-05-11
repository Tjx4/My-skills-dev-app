package co.za.dvt.myskilldevapp.features.dashboard

import co.za.dvt.myskilldevapp.features.database.GameStatsDAO
import co.za.dvt.myskilldevapp.features.repositories.BaseRepository

open class DashboardRepository(var database: GameStatsDAO) : BaseRepository() {

}