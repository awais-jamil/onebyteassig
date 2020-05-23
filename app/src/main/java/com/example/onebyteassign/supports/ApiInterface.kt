package com.example.onebyteassign.supports

import com.example.onebyteassign.models.Model
import io.reactivex.Observable
import retrofit2.http.*

interface ApiInterface {

    @Headers("Content-Type: application/json")
    @POST("./accounts:signInWithPassword?key=AIzaSyCZ1RHhSXtnWgbkh709bfn5jNBWuViT5vY")
    fun login(@Body body: HashMap<String, Any>):
            Observable<Model.Result>

    @Headers("Content-Type: application/json")
    @POST("./accounts:signUp?key=AIzaSyCZ1RHhSXtnWgbkh709bfn5jNBWuViT5vY")
    fun signUp(@Body body: HashMap<String, Any>):
            Observable<Model.Result>
}