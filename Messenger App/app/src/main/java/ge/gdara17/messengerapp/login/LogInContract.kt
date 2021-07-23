package ge.gdara17.messengerapp.login

import ge.gdara17.messengerapp.dataclasses.User

interface LogInContract {
    interface View {
        fun onLoginSuccess()
        fun onLoginFail()
    }

    interface Model {
        fun validateUser(username: String, password: String)
        fun createUser(user: User, password: String)
        fun isUserLoggedIn(): Boolean
    }

    interface Presenter {
        fun isUserLoggedIn(): Boolean
        fun validateUser(username: String, password: String)
        fun createUser(user: User, password: String)
        fun onUserValidated(isValid: Boolean)
        fun onUserCreated(isCreated: Boolean)
    }
}