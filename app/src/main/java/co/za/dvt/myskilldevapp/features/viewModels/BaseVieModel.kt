package co.za.dvt.myskilldevapp.features.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel

abstract class BaseVieModel(application: Application) : AndroidViewModel(application){
    protected var app = application
}