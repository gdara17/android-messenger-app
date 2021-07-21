package ge.gdara17.messengerapp.contacts

import ge.gdara17.messengerapp.dataclasses.User

class ContactsPresenter(private val view: ContactsContract.View) :
    ContactsContract.Presenter {
    private val model : ContactsContract.Model = ContactsModel(this)

    override fun getContacts() {
        model.fetchContacts()
    }

    override fun onContactsFetched(contacts: MutableList<User>) {
        view.showContacts(contacts)
    }
}