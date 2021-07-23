package ge.gdara17.messengerapp.main.settings

import ge.gdara17.messengerapp.dataclasses.User

interface SettingsContract {
    interface View {
        fun showUser(user: User)
        fun onSettingsUpdateSuccess()
        fun onSettingsUpdateFail()
    }

    interface Model {
        fun fetchUser()
        fun updateUser(username: String, occupation: String)
        fun signOut()
    }

    interface Presenter {
        fun getUser()
        fun onUserFetched(user: User?)
        fun updateUser(username: String, occupation: String)
        fun onUserUpdated(isUpdated: Boolean)
        fun signOutUser()
    }
}