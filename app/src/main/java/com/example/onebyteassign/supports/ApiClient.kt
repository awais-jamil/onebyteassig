package com.example.onebyteassign.supports

import com.squareup.okhttp.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    val retrofit = Retrofit.Builder()
        .addCallAdapterFactory(
            RxJava2CallAdapterFactory.create())
        .addConverterFactory(
            GsonConverterFactory.create())
        .baseUrl("https://identitytoolkit.googleapis.com/v1/")
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }



}