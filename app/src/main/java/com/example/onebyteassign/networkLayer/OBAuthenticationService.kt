package com.example.onebyteassign.networkLayer

import android.util.Log
import com.example.onebyteassign.models.OBUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import durdinapps.rxfirebase2.RxFirebaseAuth

object OBAuthenticationService {

    var currentUser: FirebaseUser? = null
        get() = FirebaseAuth.getInstance().currentUser
        private set

    var auth: FirebaseAuth? = null
        get() = FirebaseAuth.getInstance()
        private set

    var firebaseService: OBFirebaseService

    //init
    init {

        val firestore = FirebaseFirestore.getInstance()
        val path = "users"
        val collection = firestore.collection(path)
        val query = collection

        firebaseService = OBFirebaseService(collection, query)
    }

    fun needsAuthentication(): Boolean {
        return currentUser == null
    }

    fun loginRX(email: String, password: String, callback:(error: Boolean?)-> Unit) {
        RxFirebaseAuth.signInWithEmailAndPassword(auth!!, email, password)
            .map{authResult -> authResult.getUser() != null}
            .subscribe(
                {
                    Log.e("result", it.toString())
                    callback(false)
                },
                {
                    callback(true)
                }
            )
    }

    fun signupRX(email: String, password: String, callback:(error: Boolean?)-> Unit) {
        RxFirebaseAuth.createUserWithEmailAndPassword(auth!!, email, password)
            .map{authResult -> authResult.getUser() != null}
            .subscribe(
                {
                    Log.e("result", it.toString())
                    callback(false)
                },
                {
                    callback(true)
                }
            )
    }

    fun setUserRecord(
        OBUser: OBUser,
        completion:(
            error: Boolean?
        )-> Unit
    ) {

        firebaseService.update(
            OBUser.uid,
            OBUser.hashMap(),
            completion = {exception: Exception? ->

                if(exception == null)
                    completion(false)
                else completion(true)
            }
        )
    }
}