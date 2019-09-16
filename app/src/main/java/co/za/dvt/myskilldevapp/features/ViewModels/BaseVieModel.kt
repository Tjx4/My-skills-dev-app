package co.za.dvt.myskilldevapp.features.ViewModels

import androidx.lifecycle.ViewModel
import co.za.dvt.myskilldevapp.constants.Constants.HOST
import co.za.dvt.myskilldevapp.helpers.RetrofitHelper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseVieModel : ViewModel(){

    protected var retrofitHelper:RetrofitHelper

    init {
        val builder = Retrofit.Builder()
            .baseUrl(HOST)
            .addConverterFactory(GsonConverterFactory.create())
        val retrofit = builder.build()

        retrofitHelper = retrofit.create(RetrofitHelper::class.java!!)
    }

}