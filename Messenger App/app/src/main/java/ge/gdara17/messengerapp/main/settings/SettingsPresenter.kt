package ge.gdara17.messengerapp.main.settings

import ge.gdara17.messengerapp.dataclasses.User

class SettingsPresenter(private val view: SettingsContract.View) : SettingsContract.Presenter {
    private val model: SettingsContract.Model = SettingsModel(this)

    override fun getUser() {
        model.fetchUser()
    }

    override fun onUserFetched(user: User?) {
        if (user != null) {
            view.showUser(user)
        }
    }

    override fun updateUser(username: String, occupation: String) {
        model.updateUser(username, occupation)
    }

    override fun onUserUpdated(isUpdated: Boolean) {
        if (isUpdated) {
            view.onSettingsUpdateSuccess()
        } else {
            view.onSettingsUpdateFail()
        }
    }

    override fun signOutUser() {
        model.signOut()
    }

}