package com.example.onebyteassign.models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.onebyteassign.networkLayer.OBFirebaseService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

object OBCurrentUser {

    lateinit var firebaseUser: FirebaseUser
    @JvmStatic
    lateinit var user: OBUser
    var firebaseService: OBFirebaseService
    @JvmStatic
    var des: String= "sasas"

    init {

        FirebaseAuth.getInstance().currentUser?.let {
            firebaseUser = it
        }?: run {
            throw Exception("No Authenticated User Found.")
        }

        val firestore = FirebaseFirestore.getInstance()
        val collection = firestore.collection("users")
        val query = collection.whereEqualTo("uid", firebaseUser.uid)

        firebaseService = OBFirebaseService(collection, query)

        firebaseService.fetch(
            firebaseUser.uid, completion = {result, exception ->

                exception?.let {

                }

                result?.let {

                    user = it.toObject(OBUser::class.java)!!
                    Log.e("user", user.toString())
                }
            })

    }

    fun reloadUser() {

        FirebaseAuth.getInstance().currentUser?.reload()

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
    }

}