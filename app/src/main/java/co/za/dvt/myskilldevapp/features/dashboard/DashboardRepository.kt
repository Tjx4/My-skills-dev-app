package co.za.dvt.myskilldevapp.features.dashboard

import co.za.dvt.myskilldevapp.features.repositories.BaseRepositories
import co.za.dvt.myskilldevapp.models.RoundModel
import kotlinx.coroutines.delay
import java.util.HashMap

open class DashboardRepository : BaseRepositories() {
    suspend fun fetchLuckyNumber(payload: HashMap<String, String>) : RoundModel {
delay(3000)
        return retrofitHelper?.getLuckyNumner(payload)
    }
    suspend fun fetchAvailableCars() = retrofitHelper.getAvailableCars()
}