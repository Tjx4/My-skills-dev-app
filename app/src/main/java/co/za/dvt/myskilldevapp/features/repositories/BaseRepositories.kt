package co.za.dvt.myskilldevapp.features.repositories

import co.za.dvt.myskilldevapp.constants.HOST
import co.za.dvt.myskilldevapp.helpers.RetrofitHelper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseRepositories {

    protected var retrofitHelper: RetrofitHelper

    init {
        val builder = Retrofit.Builder()
            .baseUrl(HOST)
            .addConverterFactory(GsonConverterFactory.create())
        val retrofit = builder.build()

        retrofitHelper = retrofit.create(RetrofitHelper::class.java!!)
    }
}