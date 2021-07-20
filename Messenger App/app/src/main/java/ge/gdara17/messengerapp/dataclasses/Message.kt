package ge.gdara17.messengerapp.dataclasses

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@IgnoreExtraProperties
@Parcelize
class Message(
//    val from: User? = null,
//    val to: User? = null,
    val time: Long? = Calendar.getInstance().timeInMillis,
    val text: String? = null,
    val received: Boolean? = true
) : Parcelable {
    @IgnoredOnParcel
    private val formatter = SimpleDateFormat("hh:mm")

    fun getTimeAsCalendar(): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time!!

        return calendar
    }

    fun getFormattedTime(): String {
        return formatter.format(time)
    }
}