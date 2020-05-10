package co.za.dvt.myskilldevapp.features.viewModels

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import co.za.dvt.myskilldevapp.helpers.CacheHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseVieModel(application: Application) : AndroidViewModel(application){
    protected var app = application
    protected var cacheHelper = CacheHelper(application.applicationContext)
    protected var viewModelJob = Job()
    protected val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    protected val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        cacheHelper.apiKey = "$2a$10$1JEnmtEF417yBaFZcr51qukRjaKv8d5toEG5DKP/IUZWIVwfsaF7y"
    }
}