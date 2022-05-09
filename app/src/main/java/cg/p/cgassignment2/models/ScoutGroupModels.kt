package cg.p.cgassignment2.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScoutGroupModels(
                              var uid: String = "",
                              var _id: String = "N/A",
                              var name : String = "",
                              var location : String = "",
                              val email: String = "example@gmail.com") : Parcelable {
    fun toMap(): Map<String,Any?>
    {
        return mapOf(
                 "uid" to uid,
                 "_id" to _id,
                 "name" to name,
                 "location" to location,
                 "email" to email
        )
    }
}
