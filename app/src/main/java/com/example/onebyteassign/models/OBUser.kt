package com.example.onebyteassign.models

import com.example.onebyteassign.networkLayer.OBAuthenticationService
import com.google.firebase.auth.FirebaseUser
import java.io.Serializable

data class OBUser(
    var uid: String = "",
    var name: String = "",
    var phone: String = "",
    var email: String = ""
):Serializable {

    constructor(firebaseUser: FirebaseUser): this() {

        uid = firebaseUser.uid

        firebaseUser.displayName?.let {

            name = it
        }
    }

    fun hashMap(): HashMap<String, Any> {

        return hashMapOf(

            "uid" to uid,
            "name" to name,
            "phone" to phone,
            "email" to email
        )
    }

    fun OBUser.isCurrent(): Boolean {

        OBAuthenticationService.currentUser?.let {

            return it.uid.equals(uid)
        }

        return false
    }

    override fun equals(other: Any?): Boolean {

        if(!(other as OBUser).uid.equals("")) {
            other?.let {

                return uid.equals((it as OBUser).uid)
            }
        } else if((other as OBUser).uid.equals("") && !(other as OBUser).name.equals("")) {
            return name.equals((other as OBUser).name)
        }

        return false
    }

    override fun hashCode(): Int {
        return uid.hashCode()
    }
}