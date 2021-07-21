package ge.gdara17.messengerapp.login

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.Executor

class LogInModel(private val presenter: LogInContract.Presenter) : LogInContract.Model, Executor {
    private var auth: FirebaseAuth = Firebase.auth

    override fun validateUser(username: String, password: String) {
        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(DEBUG_TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    presenter.onUserValidated(true)
                } else {
                    Log.w(DEBUG_TAG, "signInWithEmail:failure", task.exception)
                    presenter.onUserValidated(false)
                }
            }
    }

    override fun createUser(username: String, password: String) {
        auth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(DEBUG_TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    presenter.onUserCreated(true)
                } else {
                    Log.w(DEBUG_TAG, "createUserWithEmail:failure", task.exception)
                    presenter.onUserCreated(false)
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

    companion object {
        private const val DEBUG_TAG = "MyDebug"
    }

}
