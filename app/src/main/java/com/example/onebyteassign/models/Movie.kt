package com.example.onebyteassign.models

import java.io.Serializable
import java.util.*
import kotlin.collections.HashMap

data class Movie(
    var id: String = "",
    var name: String = "",
    var image: String = "",
    var rating: Int = 0,
    var date:String = "",
    var category: String = ""
):Serializable {

//    fun getCreatedAt(): Date? {
//
//        return Date((date * 1000).toLong())
//    }

    fun hashMap(): HashMap<String, Any> {

        return hashMapOf(

            "id" to id,
            "name" to name,
            "image" to image,
            "rating" to rating,
            "date" to date,
            "category" to category
        )
    }

    override fun equals(other: Any?): Boolean {

        if(!(other as Movie).id.equals("")) {
            other?.let {

                return id.equals((it as Movie).id)
            }
        } else if((other as Movie).id.equals("") && !(other as Movie).name.equals("")) {
            return name.equals((other as Movie).name)
        }

        return false
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}