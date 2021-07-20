package ge.gdara17.messengerapp.dataclasses

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class User(
    val name: String? = null,
    val occupation: String? = null,
//    val avatar: Image,
) : Parcelable