package com.example.onebyteassign.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onebyteassign.supports.ApiClient
import com.example.onebyteassign.supports.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class AuthViewModel : ViewModel() {

    enum class authStatus{
        Progress,
        Success,
        Error
    }

    val authState = MutableLiveData<authStatus>(authStatus.Progress)

    val apiServe by lazy {
        ApiClient.buildService(ApiInterface::class.java)
    }
    var disposable: Disposable? = null

    var lastException:Exception? = null

    fun login(email: String, password: String) {

        var hashmap = HashMap<String, Any>()
        hashmap.set("email", email)
        hashmap.set("password", password)
        hashmap.set("returnSecureToken", true)

        disposable =
            apiServe.login(hashmap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        authState.value = authStatus.Success
                    },
                    { error ->
                        authState.value = authStatus.Error
                    }
                )
    }

    fun signup(email: String, password: String, name: String, phone: String) {

        var hashmap = HashMap<String, Any>()
        hashmap.set("email", email)
        hashmap.set("password", password)

        disposable =
            apiServe.signUp(hashmap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        authState.value = authStatus.Success
                    },
                    { error ->
                        Log.e("error", error.message)
                        Log.e("error1", error.localizedMessage)
                        Log.e("error1", error.toString())
                        authState.value = authStatus.Error
                    }
                )
    }
}
