package ge.gdara17.messengerapp.dataclasses

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class Chat(
    @get:Exclude
    var uid: String? = null,
    var with: User? = null,
    val messages: MutableList<Message>? = null,
    val lastMessage: Message? = null,
) : Parcelable