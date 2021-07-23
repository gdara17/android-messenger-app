package ge.gdara17.messengerapp.dataclasses

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class User(
    @get:Exclude
    var uid: String? = null,
    var username: String? = null,
    var occupation: String? = null,
    var chats: Map<String, String>? = null,
//    val avatar: Image,
) : Parcelable {

}