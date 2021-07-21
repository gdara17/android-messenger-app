package ge.gdara17.messengerapp.main.settings

interface SettingsContract {
    interface View {
        fun onSettingsUpdateSuccess()
        fun onSettingsUpdateFail()
    }

    interface Model {
        fun updateUser(username: String, occupation: String)
        fun signOut()
    }

    interface Presenter {
        fun updateUser(username: String, occupation: String)
        fun onUserUpdated(isUpdated: Boolean)
        fun signOutUser()
    }
}