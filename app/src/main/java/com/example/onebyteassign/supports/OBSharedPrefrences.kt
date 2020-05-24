package com.example.onebyteassign.supports

import android.content.Context
import android.content.SharedPreferences

object OBSharedPrefrences {

    private const val NAME = "OBSharedPrefrences"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private const val KEY_AUTH_VERIFICATION_ID = "KEY_AUTH_VERIFICATION_ID"
    private const val KEY_AUTH_RESEND_TOKEN = "KEY_AUTH_RESEND_TOKEN"

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    /**
     * SharedPreferences extension function, so we won't need to call edit()
    and apply()
     * ourselves on every SharedPreferences operation.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    fun setAuthVerificationId(authId: String) {

        preferences.edit {
            it.putString(KEY_AUTH_VERIFICATION_ID, authId)
        }
    }

    fun getAuthVerificationId(): String? {

        return  preferences.getString(KEY_AUTH_VERIFICATION_ID, null)
    }

}