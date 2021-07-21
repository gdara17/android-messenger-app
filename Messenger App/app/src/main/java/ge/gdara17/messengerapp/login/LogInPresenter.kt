package ge.gdara17.messengerapp.login


class LogInPresenter(private val view: LogInContract.View) :
    LogInContract.Presenter {
    private val model: LogInContract.Model = LogInModel(this)

    override fun isUserLoggedIn(): Boolean {
        return model.isUserLoggedIn()
    }

    override fun validateUser(username: String, password: String) {
        model.validateUser(username, password)
    }

    override fun createUser(username: String, password: String) {
        model.createUser(username, password)
    }

    override fun onUserValidated(isValid: Boolean) {
        if (isValid) {
            view.onLoginSuccess()
        } else {
            view.onLoginFail()
        }
    }

    override fun onUserCreated(isCreated: Boolean) {
        onUserValidated(isCreated)
    }

}