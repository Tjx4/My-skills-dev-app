package co.za.dvt.myskilldevapp.features.dashboard

import androidx.lifecycle.MutableLiveData

class DashboardRepository {

    //Todo: fix logic
    val dashboardModel: MutableLiveData<DashboardModel>

    init {
        dashboardModel = MutableLiveData()
    }

    fun getLuckyNumber(): DashboardModel {
        dashboardModel.value = DashboardModel()
      //  dashboardModel.value?.luckyNumber = (1..6).random()
        return dashboardModel.value!!
    }

}

