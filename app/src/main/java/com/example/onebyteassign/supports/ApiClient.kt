package com.example.onebyteassign.supports

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    const val BASE_URL = "https://identitytoolkit.googleapis.com/v1/"
    const val BASE_URL2 = "https://firestore.googleapis.com/v1/"

   fun<T> buildService(service: Class<T>, url: String): T{
       val retrofit = Retrofit.Builder()
           .addCallAdapterFactory(
               RxJava2CallAdapterFactory.create())
           .addConverterFactory(
               GsonConverterFactory.create())
           .baseUrl(url)
           .build()

        return retrofit.create(service)
    }
}