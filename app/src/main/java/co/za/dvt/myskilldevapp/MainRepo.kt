package co.za.dvt.myskilldevapp

import androidx.lifecycle.MutableLiveData

class MainRepo {

    //Todo: fix logic
    val mainModel: MutableLiveData<MainModel>

    init {
        mainModel = MutableLiveData()
    }

    fun getLuckyNumber(): MainModel{
        mainModel.value = MainModel()
        mainModel.value?.luckyNumber = (1..6).random()
        return mainModel.value!!
    }

}

