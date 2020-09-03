package co.za.dvt.myskilldevapp.helpers

import co.za.dvt.myskilldevapp.enums.Hosts
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object API {
    var retrofitHelper: RetrofitHelper

    init {
        val builder = Retrofit.Builder()
            .baseUrl(Hosts.LocalHost.ip)
            .addConverterFactory(GsonConverterFactory.create())
        val retrofit = builder.build()

        retrofitHelper = retrofit.create(RetrofitHelper::class.java)
    }
}