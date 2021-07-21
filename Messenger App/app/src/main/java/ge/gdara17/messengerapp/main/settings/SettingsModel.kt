package ge.gdara17.messengerapp.main.settings

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SettingsModel(private val presenter: SettingsContract.Presenter) : SettingsContract.Model {
    val user = Firebase.auth.currentUser

    override fun updateUser(username: String, occupation: String) {
        user!!.updateEmail(username)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(DEBUG_TAG, "User email address updated.")
                    presenter.onUserUpdated(true)
                } else {
                    Log.d(DEBUG_TAG, "User email address updated.", task.exception)
                    presenter.onUserUpdated(false)
                }
            }
    }

    override fun signOut() {
        Firebase.auth.signOut()
    }

    companion object {
        const val DEBUG_TAG = "MyDebug"
    }
}