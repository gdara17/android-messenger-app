package ge.gdara17.messengerapp.dataclasses

import android.app.Person
import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class Chat(
    val with: User? = null,
    val messages: MutableList<Message>? = null,
) : Parcelable