package com.example.onebyteassign.supports

import android.app.Application

class OBApplication: Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        OBSharedPrefrences.init(this)
    }

    companion object {
        private var instance: OBApplication? = null

        @JvmStatic
        fun applicationContext() : OBApplication {
            return instance as OBApplication
        }
    }
}