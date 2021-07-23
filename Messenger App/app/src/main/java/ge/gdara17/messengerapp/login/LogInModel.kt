package ge.gdara17.messengerapp.login

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import ge.gdara17.messengerapp.Constants
import ge.gdara17.messengerapp.dataclasses.User
import java.lang.Exception
import java.util.concurrent.Executor

class LogInModel(private val presenter: LogInContract.Presenter) : LogInContract.Model, Executor {
    private var auth: FirebaseAuth = Firebase.auth
    private val usersRef = FirebaseDatabase.getInstance().getReference("users");

    override fun validateUser(username: String, password: String) {
        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(Constants.DEBUG_TAG, "signInWithEmail:success")
                    presenter.onUserValidated(true)
                } else {
                    Log.w(Constants.DEBUG_TAG, "signInWithEmail:failure", task.exception)
                    presenter.onUserValidated(false)
                }
            }
    }

    override fun createUser(user: User, password: String) {
        auth.createUserWithEmailAndPassword(user.username!!, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserEntry(user)
                } else {
                    onFailure(task.exception)
                }
            }
    }

    override fun isUserLoggedIn(): Boolean {
        val currentUser = auth.currentUser
        return currentUser != null
    }

    override fun execute(command: Runnable?) {
        command?.run()
    }

    private fun addUserEntry(user: User) {
        auth.currentUser?.uid?.let { uid ->
            usersRef.child(uid).setValue(user).addOnSuccessListener {
                Log.d(Constants.DEBUG_TAG, "createUserWithEmail:success")
                presenter.onUserCreated(true)
            }.addOnFailureListener {
                onFailure(it)
            }
        }
    }

    private fun onFailure(exception: Exception?) {
        Log.w(Constants.DEBUG_TAG, "createUserWithEmail:failure", exception)
        presenter.onUserCreated(false)
    }
}
