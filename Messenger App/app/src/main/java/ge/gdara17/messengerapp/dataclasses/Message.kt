package ge.gdara17.messengerapp.dataclasses

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@IgnoreExtraProperties
@Parcelize
class Message(
    @get:Exclude
    var chatUid: String? = null,
    @get:Exclude
    var received: Boolean? = true,
    var sender: String? = null,
    val timestamp: Long? = Calendar.getInstance().timeInMillis,
    val text: String? = null,
) : Parcelable {


    @IgnoredOnParcel
    private val formatter = SimpleDateFormat("hh:mm")

    @Exclude
    fun getTimeAsCalendar(): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp!!

        return calendar
    }

    @Exclude
    fun getFormattedTime(): String {
        return formatter.format(timestamp)
    }
}