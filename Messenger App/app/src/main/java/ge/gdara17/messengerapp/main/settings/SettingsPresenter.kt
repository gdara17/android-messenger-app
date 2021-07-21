package ge.gdara17.messengerapp.main.settings

class SettingsPresenter(private val view: SettingsContract.View) : SettingsContract.Presenter {
    private val model: SettingsContract.Model = SettingsModel(this)

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