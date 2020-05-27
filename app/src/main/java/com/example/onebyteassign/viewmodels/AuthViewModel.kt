package com.example.onebyteassign.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onebyteassign.models.OBUser
import com.example.onebyteassign.networkLayer.OBAuthenticationService
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.disposables.CompositeDisposable


class AuthViewModel : ViewModel() {

    enum class authStatus{
        Progress,
        Success,
        LoginSuccess,
        SignUpSuccess,
        Error
    }

    val authState = MutableLiveData<authStatus>(authStatus.Progress)

//    val apiServe by lazy {
//        ApiClient.buildService(ApiInterface::class.java, ApiClient.BASE_URL)
//    }
//    val apiServeFireStore by lazy {
//        ApiClient.buildService(ApiInterface::class.java, ApiClient.BASE_URL2)
//    }

    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun login(email: String, password: String) {
       OBAuthenticationService.loginRX(email, password){ error ->

           if(error!!) {
               authState.value = authStatus.Error
           }
           else {
               authState.value = authStatus.Success
           }
       }
    }

    fun signup(email: String, password: String) {
        OBAuthenticationService.signupRX(email, password){ error ->

            if(error!!) {
                authState.value = authStatus.Error
            }
            else {
                authState.value = authStatus.SignUpSuccess
            }
        }
    }

    fun saveUserToFirestore(name: String, phone: String, email: String) {

        var user = OBUser(OBAuthenticationService.currentUser!!.uid, name, phone, email)

        OBAuthenticationService.setUserRecord(user){ error ->

            if(error!!) {
                authState.value = authStatus.Error
            }
            else {
                authState.value = authStatus.Success
            }
        }
    }

    //retrofit

//    fun login(email: String, password: String) {
//
//        var hashmap = HashMap<String, Any>()
//        hashmap.set("email", email)
//        hashmap.set("password", password)
//        hashmap.set("returnSecureToken", true)
//
//        compositeDisposable.add(apiServe.login(hashmap)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                { result ->
//                    Log.e("result", result.toString())
//                    authState.value = authStatus.Success
//                },
//                { error ->
//                    authState.value = authStatus.Error
//                }
//            ))
//    }
//
//    fun signup(email: String, password: String, name: String, phone: String) {
//
//        var hashmap = HashMap<String, Any>()
//        hashmap.set("email", email)
//        hashmap.set("password", password)
//        hashmap.set("returnSecureToken", true)
//
//        val requests = ArrayList<Observable<*>>()
//        requests.add(apiServe.signUp(hashmap))
//
//        compositeDisposable.add(apiServe.signUp(hashmap)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                { result ->
//                    OBSharedPrefrences.setAuthVerificationId(result.refreshToken.toString())
//                    authState.value = authStatus.SignUpSuccess
//                },
//                { error ->
//                    Log.e("error", error.message)
//                    authState.value = authStatus.Error
//                }
//            ))
//    }
//
//    fun wrapUp(){
//        compositeDisposable?.dispose()
//    }
}
