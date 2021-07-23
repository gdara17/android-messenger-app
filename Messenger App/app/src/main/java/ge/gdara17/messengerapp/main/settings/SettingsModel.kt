package ge.gdara17.messengerapp.main.settings

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import ge.gdara17.messengerapp.Constants
import ge.gdara17.messengerapp.dataclasses.User

class SettingsModel(private val presenter: SettingsContract.Presenter) : SettingsContract.Model {
    val user = Firebase.auth.currentUser
    private val database = Firebase.database
    private val usersRef = database.getReference("users")

    override fun fetchUser() {
        usersRef.child(user?.uid!!).get().addOnSuccessListener { userSnapshot ->
            val currentUser = userSnapshot.getValue<User>()
            Log.d(Constants.DEBUG_TAG, "user fetch:success, $currentUser")
            presenter.onUserFetched(currentUser)
        }
    }

    override fun updateUser(username: String, occupation: String) {
        user!!.updateEmail(username)
            .addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {
                    Log.d(Constants.DEBUG_TAG, "email update:success")
                    usersRef.child(user.uid).updateChildren(
                        mapOf(
                            "username" to username,
                            "occupation" to occupation
                        )
                    ).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(Constants.DEBUG_TAG, "User update:success")
                            presenter.onUserUpdated(true)
                        } else {
                            Log.d(Constants.DEBUG_TAG, "User update:failure", task.exception)
                            presenter.onUserUpdated(false)
                        }
                    }
                } else {
                    Log.d(Constants.DEBUG_TAG, "email update:failure")
                    presenter.onUserUpdated(false)
                }
            }
    }

    override fun signOut() {
        Firebase.auth.signOut()
    }
}