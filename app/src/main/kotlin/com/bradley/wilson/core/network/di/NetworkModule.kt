package com.bradley.wilson.core.network.di

import com.bradley.wilson.core.network.NetworkClient
import com.bradley.wilson.core.network.RetrofitClient
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule: Module = module {
    single<NetworkClient> { RetrofitClient(get()) }
    single {
        Retrofit.Builder()
            .baseUrl("https://hiring.revolut.codes/api/android/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
