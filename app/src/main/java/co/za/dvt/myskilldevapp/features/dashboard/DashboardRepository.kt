package co.za.dvt.myskilldevapp.features.dashboard

import co.za.dvt.myskilldevapp.features.repositories.BaseRepositories
import java.util.HashMap

open class DashboardRepository : BaseRepositories() {
    suspend fun fetchLuckyNumber(payload: HashMap<String, String>) = retrofitHelper?.getLuckyNumner(payload)
    suspend fun fetchAvailableCars() = retrofitHelper.getAvailableCars()
}